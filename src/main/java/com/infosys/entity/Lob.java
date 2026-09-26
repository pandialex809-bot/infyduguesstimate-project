package com.infosys.entity;

import javax.persistence.*;

@Entity
@Table(name = "lob")
public class Lob {
    @Id
    @Column(name = "lob_name", nullable = false, length = 100)
    private String lobName;

    @Column(name = "du_anchor", nullable = false, length = 150)
    private String duAnchor;

    public Lob() {}

    public Lob(String lobName, String duAnchor) {
        this.lobName = lobName;
        this.duAnchor = duAnchor;
    }

    public String getLobName() { return lobName; }
    public void setLobName(String lobName) { this.lobName = lobName; }

    public String getDuAnchor() { return duAnchor; }
    public void setDuAnchor(String duAnchor) { this.duAnchor = duAnchor; }
}
