package ru.ki.dao.support.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.Date;

@XmlAccessorType(XmlAccessType.FIELD)
public class EmployeeVO {
    static {
        FindResult.addXmlSeeAlso(EmployeeVO.class);
    }
    private String fullName;
    private String prSex;
    private String employeeType;
    private String subdivisionName;
    private String storeName;
    private String teritory;
    private Date dateBorn;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPrSex() {
        return prSex;
    }

    public void setPrSex(String prSex) {
        this.prSex = prSex;
    }

    public String getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(String employeeType) {
        this.employeeType = employeeType;
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

    public Date getDateBorn() {
        return dateBorn;
    }

    public void setDateBorn(Date dateBorn) {
/*
        if (dateBorn instanceof java.sql.Date) {
            dateBorn = new Date(((java.sql.Date) dateBorn).getTime());
        }
*/
        this.dateBorn = dateBorn;
    }
}
