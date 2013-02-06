package ru.ki.model.ws;

import java.sql.Date;

public class PersonalVO {
    private String fioPers;
    private String prSex;
    private String prRukovod;
    private String subdivisionName;
    private String storeName;
    private String teritory;
    private Date dateRozd;

    public String getFioPers() {
        return fioPers;
    }

    public void setFioPers(String fioPers) {
        this.fioPers = fioPers;
    }

    public String getPrSex() {
        return prSex;
    }

    public void setPrSex(String prSex) {
        this.prSex = prSex;
    }

    public String getPrRukovod() {
        return prRukovod;
    }

    public void setPrRukovod(String prRukovod) {
        this.prRukovod = prRukovod;
    }

    public String getSubdivisionName() {
        return subdivisionName;
    }

    public void setSubdivisionName(String subdivisionName) {
        this.subdivisionName = subdivisionName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getTeritory() {
        return teritory;
    }

    public void setTeritory(String teritory) {
        this.teritory = teritory;
    }

    public Date getDateRozd() {
        return dateRozd;
    }

    public void setDateRozd(Date dateRozd) {
        this.dateRozd = dateRozd;
    }
}
