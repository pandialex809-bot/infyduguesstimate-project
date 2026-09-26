package com.infosys.dto;

public class EstimateResponse {
    public Long id;
    public String lob;
    public String duAnchor;
    public int year;
    public String quarter;
    public String month;
    public int confident;
    public int opportunities;
    public int supplySupportNeed;
    public double forecastedRevenue;
    public double actualRevenue;

    public EstimateResponse(Long id, String lob, String duAnchor, int year, String quarter,
                             String month, int confident, int opportunities,
                             int supplySupportNeed, double forecastedRevenue, double actualRevenue) {
        this.id = id;
        this.lob = lob;
        this.duAnchor = duAnchor;
        this.year = year;
        this.quarter = quarter;
        this.month = month;
        this.confident = confident;
        this.opportunities = opportunities;
        this.supplySupportNeed = supplySupportNeed;
        this.forecastedRevenue = forecastedRevenue;
        this.actualRevenue = actualRevenue;
    }
}
