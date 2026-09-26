package com.infosys.dto;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

public class EstimateRequest {
    @NotBlank
    private String lob;
    @NotNull @Min(2000)
    private Integer year;
    @NotBlank
    private String quarter;
    @NotEmpty @Size(min = 3, max = 3)
    @Valid
    private List<MonthEstimateRequest> months;

    public String getLob() { return lob; }
    public void setLob(String lob) { this.lob = lob; }
    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }
    public String getQuarter() { return quarter; }
    public void setQuarter(String quarter) { this.quarter = quarter; }
    public List<MonthEstimateRequest> getMonths() { return months; }
    public void setMonths(List<MonthEstimateRequest> months) { this.months = months; }
}
