package com.infosys.entity;

import javax.persistence.*;

@Entity
@Table(name = "estimate",
       uniqueConstraints = @UniqueConstraint(
           name = "uk_estimate_lob_year_quarter_month",
           columnNames = {"lob_name", "estimate_year", "quarter", "month"}))
public class Estimate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lob_name", nullable = false)
    private Lob lob;

    @Column(name = "estimate_year", nullable = false)
    private int year;

    @Column(nullable = false, length = 2)
    private String quarter;

    @Column(nullable = false, length = 20)
    private String month;

    @Column(nullable = false)
    private Integer confident;

    @Column(nullable = false)
    private Integer opportunities;

    @Column(name = "supply_support_need", nullable = false)
    private Integer supplySupportNeed;

    @Column(nullable = false)
    private Double forecastedRevenue;

    @Column(nullable = false)
    private Double actualRevenue;

    public Estimate() {}

    public Long getId() { return id; }
    public Lob getLob() { return lob; }
    public void setLob(Lob lob) { this.lob = lob; }
    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }
    public String getQuarter() { return quarter; }
    public void setQuarter(String quarter) { this.quarter = quarter; }
    public String getMonth() { return month; }
    public void setMonth(String month) { this.month = month; }
    public Integer getConfident() { return confident; }
    public void setConfident(Integer confident) { this.confident = confident; }
    public Integer getOpportunities() { return opportunities; }
    public void setOpportunities(Integer opportunities) { this.opportunities = opportunities; }
    public Integer getSupplySupportNeed() { return supplySupportNeed; }
    public void setSupplySupportNeed(Integer supplySupportNeed) { this.supplySupportNeed = supplySupportNeed; }
    public Double getForecastedRevenue() { return forecastedRevenue; }
    public void setForecastedRevenue(Double forecastedRevenue) { this.forecastedRevenue = forecastedRevenue; }
    public Double getActualRevenue() { return actualRevenue; }
    public void setActualRevenue(Double actualRevenue) { this.actualRevenue = actualRevenue; }
}
