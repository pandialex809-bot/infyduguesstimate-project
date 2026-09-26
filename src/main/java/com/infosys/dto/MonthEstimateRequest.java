package com.infosys.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class MonthEstimateRequest {
    @NotBlank
    private String month;
    @NotNull @Min(0)
    private Integer confident;
    @NotNull @Min(0)
    private Integer opportunities;
    @NotNull @Min(0)
    private Integer supplySupportNeed;
    @NotNull @Min(0)
    private Double forecastedRevenue;
    @NotNull @Min(0)
    private Double actualRevenue;

    public String getMonth() { return month; }
    public void setMonth(String month) { this.month = month; }
    public Integer getConfident() { return confident; }
    public void setConfident(Integer v) { confident = v; }
    public Integer getOpportunities() { return opportunities; }
    public void setOpportunities(Integer v) { opportunities = v; }
    public Integer getSupplySupportNeed() { return supplySupportNeed; }
    public void setSupplySupportNeed(Integer v) { supplySupportNeed = v; }
    public Double getForecastedRevenue() { return forecastedRevenue; }
    public void setForecastedRevenue(Double v) { forecastedRevenue = v; }
    public Double getActualRevenue() { return actualRevenue; }
    public void setActualRevenue(Double v) { actualRevenue = v; }
}
