package com.infosys.dto;

import javax.validation.constraints.NotBlank;

public class LobRequest {
    @NotBlank
    private String lob;
    @NotBlank
    private String duAnchor;

    public String getLob() { return lob; }
    public void setLob(String lob) { this.lob = lob; }

    public String getDuAnchor() { return duAnchor; }
    public void setDuAnchor(String duAnchor) { this.duAnchor = duAnchor; }
}
