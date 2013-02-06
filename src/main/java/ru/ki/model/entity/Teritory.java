package ru.ki.model.entity;

import javax.persistence.*;

/**
 * @author ikozar
 * @version 1.0
 */
@Entity
public class Teritory {
    private Integer idTer;

    @javax.persistence.Column(name = "id_ter")
    @Id
    public Integer getIdTer() {
        return idTer;
    }

    public void setIdTer(Integer idTer) {
        this.idTer = idTer;
    }

    private String naimTer;

    @javax.persistence.Column(name = "naim_ter")
    @Basic
    public String getNaimTer() {
        return naimTer;
    }

    public void setNaimTer(String naimTer) {
        this.naimTer = naimTer;
    }

    private Short prTer;

    @javax.persistence.Column(name = "pr_ter")
    @Basic
    public Short getPrTer() {
        return prTer;
    }

    public void setPrTer(Short prTer) {
        this.prTer = prTer;
    }

    private String rubTerit;

    @javax.persistence.Column(name = "rub_terit")
    @Basic
    public String getRubTerit() {
        return rubTerit;
    }

    public void setRubTerit(String rubTerit) {
        this.rubTerit = rubTerit;
    }

    private String naimTerE;

    @javax.persistence.Column(name = "naim_ter_e")
    @Basic
    public String getNaimTerE() {
        return naimTerE;
    }

    public void setNaimTerE(String naimTerE) {
        this.naimTerE = naimTerE;
    }

    public Teritory() {
    }

    public Teritory(Integer idTer) {
        this.idTer = idTer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Teritory)) return false;

        Teritory teritory = (Teritory) o;

        if (getIdTer() != null ?
                !getIdTer().equals(teritory.getIdTer()) :
                teritory.getIdTer() != null) 
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idTer != null ? idTer.hashCode() : 0;
        return result;
    }

    private Teritory teritoryParent;

    @ManyToOne(fetch = FetchType.LAZY)
    public
    @javax.persistence.JoinColumn(name = "id_ter_parent", referencedColumnName = "id_ter", nullable = false)
    Teritory getTeritoryParent() {
        return teritoryParent;
    }

    public void setTeritoryParent(Teritory teritoryParent) {
        this.teritoryParent = teritoryParent;
    }

    @Override
    public String toString() {
        return "Teritory{" + idTer +
                ": " + naimTer +
                '}';
    }
}
