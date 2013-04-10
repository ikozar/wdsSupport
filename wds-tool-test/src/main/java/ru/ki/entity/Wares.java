package ru.ki.entity;

import javax.persistence.*;

/**
 * @author ikozar
 * @version 1.0
 */
@Entity
public class Wares {
    private Integer idWares;

    @javax.persistence.Column(name = "id_wares")
    @Id
    public Integer getIdWares() {
        return idWares;
    }

    public void setIdWares(Integer idWares) {
        this.idWares = idWares;
    }

    private String naimWares;

    @javax.persistence.Column(name = "naim_wares")
    @Basic
    public String getNaimWares() {
        return naimWares;
    }

    public void setNaimWares(String naimWares) {
        this.naimWares = naimWares;
    }

    private Float priceWares;

    @javax.persistence.Column(name = "price_wares")
    @Basic
    public Float getPriceWares() {
        return priceWares;
    }

    public void setPriceWares(Float priceWares) {
        this.priceWares = priceWares;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Wares wares = (Wares) o;

        if (idWares != null ? !idWares.equals(wares.idWares) : wares.idWares != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idWares != null ? idWares.hashCode() : 0;
        return result;
    }

    private TypeWares typeWares;

    @ManyToOne(fetch = FetchType.LAZY)
    public
    @javax.persistence.JoinColumn(name = "id_type_wares", referencedColumnName = "id_type_wares")
    TypeWares getTypeWares() {
        return typeWares;
    }

    public void setTypeWares(TypeWares typeWares) {
        this.typeWares = typeWares;
    }

    private Vendor vendor;

    @ManyToOne(fetch = FetchType.LAZY)
    public
    @javax.persistence.JoinColumn(name = "id_vendor", referencedColumnName = "id_vendor", nullable = false)
    Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }
}
