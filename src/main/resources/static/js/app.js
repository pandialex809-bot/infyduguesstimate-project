const $=id=>document.getElementById(id);
const api='/api';

function toast(msg){const t=$('toast');t.textContent=msg;t.style.display='block';clearTimeout(window.tt);window.tt=setTimeout(()=>t.style.display='none',2800)}
function showPage(name){document.querySelectorAll('.page').forEach(x=>x.classList.remove('active'));$(name).classList.add('active');document.querySelectorAll('.nav').forEach(x=>x.classList.toggle('active',x.dataset.page===name));$('title').textContent=name==='lobs'?'LOB Management':name==='entry'?'Data Entry':name==='records'?'Records':'Dashboard';if(name==='lobs')loadLobs();if(name==='dashboard')loadDashboard();if(name==='entry')buildMonths()}

document.querySelectorAll('.nav').forEach(b=>b.onclick=()=>showPage(b.dataset.page));
$('quarter').onchange=buildMonths;

async function json(url,opt){const r=await fetch(url,opt);const data=await r.json().catch(()=>({message:r.statusText}));if(!r.ok)throw new Error(data.message||'Request failed');return data}

async function loadLobs(){
 const data=await json(api+'/lobs');
 const selects=[$('lob'),$('filterLob')];
 selects.forEach(s=>{s.innerHTML=data.map(x=>`<option value="${x.lobName}">${x.lobName}</option>`).join('')});
 $('lobBody').innerHTML=data.map(x=>`<tr><td><b>${x.lobName}</b></td><td>${x.duAnchor}</td><td><button class="secondary" onclick="deleteLob('${x.lobName}')">Delete</button></td></tr>`).join('');
}
async function deleteLob(lob){if(!confirm('Delete '+lob+' and its estimates?'))return;try{await json(api+'/lobs/'+encodeURIComponent(lob),{method:'DELETE'});toast('LOB deleted');loadLobs()}catch(e){toast(e.message)}}

async function buildMonths(){
    const q = $('quarter').value;

    const quarterMonths = {
        Q1: ['Jan', 'Feb', 'Mar'],
        Q2: ['Apr', 'May', 'Jun'],
        Q3: ['Jul', 'Aug', 'Sep'],
        Q4: ['Oct', 'Nov', 'Dec']
    };

    const months = quarterMonths[q] || [];

    $('monthRows').innerHTML = months.map(m => `
        <div class="month-row">
            <div class="month-name">${m}</div>

            ${field(m, 'Confident', 'confident')}
            ${field(m, 'Opportunities', 'opportunities')}
            ${field(m, 'Support need', 'support')}
            ${field(m, 'Forecast', 'forecast')}
            ${field(m, 'Actual', 'actual')}
        </div>
    `).join('');
}
function field(m, label, key){
    return `
        <label>${label}
            <input
                data-month="${m}"
                data-key="${key}"
                type="number"
                min="0"
                placeholder="0"
                required
            >
        </label>
    `;
}
$('estimateForm').onsubmit=async e=>{
 e.preventDefault();
 const months=[...document.querySelectorAll('.month-row')].map(row=>{
  const month=row.querySelector('.month-name').textContent;
  const vals={};row.querySelectorAll('input').forEach(i=>vals[i.dataset.key]=Number(i.value));
  return {month,confident:vals.confident,opportunities:vals.opportunities,supplySupportNeed:vals.support,forecastedRevenue:vals.forecast,actualRevenue:vals.actual};
 });
 try{await json(api+'/estimates',{method:'POST',headers:{'Content-Type':'application/json'},body:JSON.stringify({lob:$('lob').value,year:Number($('year').value),quarter:$('quarter').value,months})});toast('Estimate saved successfully');e.target.reset();$('year').value=new Date().getFullYear();buildMonths();loadDashboard()}catch(err){toast(err.message)}
};

async function loadRecords(){
  try{
    const data=await json(
      `${api}/estimates/${encodeURIComponent($('filterLob').value)}/${$('filterYear').value}/${$('filterQuarter').value}`
    );

    $('recordsBody').innerHTML = data.length
      ? data.map(x=>`
        <tr>
          <td><b>${x.month}</b></td>
          <td>${x.confident}</td>
          <td>${x.opportunities}</td>
          <td>${x.supplySupportNeed}</td>
          <td>₹${x.forecastedRevenue.toLocaleString()}</td>
          <td>₹${x.actualRevenue.toLocaleString()}</td>
        </tr>
      `).join('')
      : `<tr><td colspan="6">No records found</td></tr>`;

  }catch(e){
    $('recordsBody').innerHTML =
      `<tr><td colspan="6">No records found</td></tr>`;

  }
}
async function loadDashboard(){
 try{
  const data=await json(api+'/dashboard');
  let v=0,f=0,a=0;data.forEach(x=>{v+=x.volume;f+=x.forecast;a+=x.actual});
  $('totalVolume').textContent=v.toLocaleString();$('forecast').textContent='₹'+Math.round(f).toLocaleString();$('actual').textContent='₹'+Math.round(a).toLocaleString();$('achievement').textContent=(f?Math.round(a/f*100):0)+'%';
  drawChart(data);
 }catch(e){toast(e.message)}
}
function drawChart(data){
 const c=$('chart'),ctx=c.getContext('2d'),d=devicePixelRatio||1,w=c.clientWidth*d,h=c.clientHeight*d;c.width=w;c.height=h;ctx.clearRect(0,0,w,h);
 if(!data.length){ctx.fillStyle='#8995a7';ctx.font=16*d+'px Arial';ctx.fillText('No data yet — add your first estimate.',25*d,55*d);return}
 const max=Math.max(...data.map(x=>Math.max(x.forecast,x.actual)),1),pad=45*d,step=(w-pad*1.5)/data.length;
 ctx.strokeStyle='#e8edf4';ctx.lineWidth=d;for(let i=0;i<5;i++){let y=pad+(h-pad*1.5)*i/4;ctx.beginPath();ctx.moveTo(pad,y);ctx.lineTo(w-pad/2,y);ctx.stroke()}
 data.forEach((x,i)=>{let x0=pad+i*step+step*.2,bw=step*.25;let fh=x.forecast/max*(h-pad*1.5),ah=x.actual/max*(h-pad*1.5);
 ctx.fillStyle='#ff6b35';ctx.fillRect(x0,h-pad-fh,bw,fh);ctx.fillStyle='#263b63';ctx.fillRect(x0+bw+6*d,h-pad-ah,bw,ah);
 ctx.fillStyle='#647187';ctx.font=11*d+'px Arial';ctx.fillText(x.period,x0,h-pad+18*d)});
}

$('lobForm').onsubmit=async e=>{
 e.preventDefault();
 try{await json(api+'/lobs',{method:'POST',headers:{'Content-Type':'application/json'},body:JSON.stringify({lob:$('newLob').value,duAnchor:$('newAnchor').value})});e.target.reset();toast('LOB added');loadLobs()}catch(err){toast(err.message)}
};

loadLobs().then(()=>{buildMonths();loadDashboard()});
