package com.infosys.service;

import com.infosys.dto.*;
import com.infosys.entity.Estimate;
import com.infosys.entity.Lob;
import com.infosys.repository.EstimateRepository;
import com.infosys.repository.LobRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
public class EstimateService {
    private static final Map<String, List<String>> QUARTER_MONTHS = new LinkedHashMap<>();
    static {
        QUARTER_MONTHS.put("Q1", Arrays.asList("Apr", "May", "Jun"));
        QUARTER_MONTHS.put("Q2", Arrays.asList("Jul", "Aug", "Sep"));
        QUARTER_MONTHS.put("Q3", Arrays.asList("Oct", "Nov", "Dec"));
        QUARTER_MONTHS.put("Q4", Arrays.asList("Jan", "Feb", "Mar"));
    }

    private final EstimateRepository estimateRepository;
    private final LobRepository lobRepository;

    public EstimateService(EstimateRepository estimateRepository, LobRepository lobRepository) {
        this.estimateRepository = estimateRepository;
        this.lobRepository = lobRepository;
    }

    public List<String> months(String quarter) {
        List<String> result = QUARTER_MONTHS.get(quarter.toUpperCase());
        if (result == null) throw new IllegalArgumentException("Quarter must be Q1, Q2, Q3 or Q4");
        return result;
    }

    @Transactional
    public List<EstimateResponse> save(EstimateRequest request) {
        String lobName = request.getLob().trim().toUpperCase();
        String quarter = request.getQuarter().trim().toUpperCase();
        List<String> expected = months(quarter);

        if (request.getMonths().size() != 3) {
            throw new IllegalArgumentException("Exactly 3 months are required");
        }

        Set<String> received = new HashSet<>();
        for (MonthEstimateRequest m : request.getMonths()) {
            received.add(normalizeMonth(m.getMonth()));
        }
        if (!received.equals(new HashSet<>(expected))) {
            throw new IllegalArgumentException("For " + quarter + ", use exactly: " + String.join(", ", expected));
        }

        Lob lob = lobRepository.findAll()
        .stream()
        .filter(x -> x.getLobName() != null
                && x.getLobName().trim().equalsIgnoreCase(lobName.trim()))
        .findFirst()
        .orElseThrow(() ->
                new IllegalArgumentException("LOB not found: " + lobName));
        List<Estimate> saved = new ArrayList<>();
        for (MonthEstimateRequest m : request.getMonths()) {
            String month = normalizeMonth(m.getMonth());
            Estimate e = estimateRepository
                    .findByLobLobNameAndYearAndQuarterAndMonth(lobName, request.getYear(), quarter, month)
                    .orElseGet(Estimate::new);
            e.setLob(lob);
            e.setYear(request.getYear());
            e.setQuarter(quarter);
            e.setMonth(month);
            e.setConfident(m.getConfident());
            e.setOpportunities(m.getOpportunities());
            e.setSupplySupportNeed(m.getSupplySupportNeed());
            e.setForecastedRevenue(m.getForecastedRevenue());
            e.setActualRevenue(m.getActualRevenue());
            saved.add(estimateRepository.save(e));
        }
        return saved.stream().map(this::response).collect(java.util.stream.Collectors.toList());
    }

    public List<EstimateResponse> get(String lob, int year, String quarter) {
        months(quarter);
        return estimateRepository.findByLobLobNameAndYearAndQuarterOrderByIdAsc(lob.toUpperCase(), year, quarter.toUpperCase())
                .stream().map(this::response).collect(java.util.stream.Collectors.toList());
    }

    public List<Map<String, Object>> dashboard() {
        List<Estimate> all = estimateRepository.findAll();
        Map<String, Map<String, Object>> grouped = new LinkedHashMap<>();
        for (Estimate e : all) {
            String key = e.getYear() + "-" + e.getQuarter();
            Map<String, Object> row = grouped.computeIfAbsent(key, k -> {
                Map<String, Object> x = new LinkedHashMap<>();
                x.put("period", k);
                x.put("volume", 0);
                x.put("forecast", 0.0);
                x.put("actual", 0.0);
                return x;
            });
            row.put("volume", ((Integer) row.get("volume")) + e.getConfident());
            row.put("forecast", ((Double) row.get("forecast")) + e.getForecastedRevenue());
            row.put("actual", ((Double) row.get("actual")) + e.getActualRevenue());
        }
        return new ArrayList<>(grouped.values());
    }

    private EstimateResponse response(Estimate e) {
        return new EstimateResponse(e.getId(), e.getLob().getLobName(), e.getLob().getDuAnchor(),
                e.getYear(), e.getQuarter(), e.getMonth(), e.getConfident(),
                e.getOpportunities(), e.getSupplySupportNeed(),
                e.getForecastedRevenue(), e.getActualRevenue());
    }

    private String normalizeMonth(String value) {
        if (value == null) throw new IllegalArgumentException("Month is required");
        String v = value.trim();
        for (String m : Arrays.asList("Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec","Jan","Feb","Mar")) {
            if (m.equalsIgnoreCase(v)) return m;
        }
        throw new IllegalArgumentException("Invalid month: " + value);
    }
}
