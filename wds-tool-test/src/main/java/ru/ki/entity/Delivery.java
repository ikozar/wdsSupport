package ru.ki.entity;

import javax.persistence.*;

/**
 * @author ikozar
 * @version 1.0
 */
@Entity
public class Delivery {
    private Integer idDeliv;

    @Column(name = "id_deliv")
    @Id
    public Integer getIdDeliv() {
        return idDeliv;
    }

    public void setIdDeliv(Integer idDeliv) {
        this.idDeliv = idDeliv;
    }

    private Teritory teritory;

    @ManyToOne(fetch = FetchType.LAZY)
    public
    @JoinColumn(name = "id_ter", referencedColumnName = "id_ter", nullable = false)
    Teritory getTeritory() {
        return teritory;
    }

    public void setTeritory(Teritory teritory) {
        this.teritory = teritory;
    }

    private Wares wares;

    @ManyToOne(fetch = FetchType.LAZY)
    public
    @JoinColumn(name = "id_wares", referencedColumnName = "id_wares")
    Wares getWares() {
        return wares;
    }

    public void setWares(Wares wares) {
        this.wares = wares;
    }

    private Integer quarter;

    @Column(name = "id_quarter")
    @Basic
    public Integer getQuarter() {
        return quarter;
    }

    public void setQuarter(Integer quarter) {
        this.quarter = quarter;
    }

    private Integer numDeliv;

    @Column(name = "num_deliv")
    @Basic
    public Integer getNumDeliv() {
        return numDeliv;
    }

    public void setNumDeliv(Integer numDeliv) {
        this.numDeliv = numDeliv;
    }

    private Float sumDeliv;

    @Column(name = "sum_deliv")
    @Basic
    public Float getSumDeliv() {
        return sumDeliv;
    }

    public void setSumDeliv(Float sumDeliv) {
        this.sumDeliv = sumDeliv;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Delivery delivery = (Delivery) o;

        if (teritory != null ? !teritory.equals(delivery.teritory) : delivery.teritory != null) return false;
        if (wares != null ? !wares.equals(delivery.wares) : delivery.wares != null) return false;
        if (quarter != null ? !quarter.equals(delivery.quarter) : delivery.quarter != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = teritory != null ? teritory.hashCode() : 0;
        result = 31 * result + (wares != null ? wares.hashCode() : 0);
        result = 31 * result + (quarter != null ? quarter.hashCode() : 0);
        return result;
    }
}
