package ru.ki.model.entity;

import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.sql.Date;

/**
 * @author ikozar
 * @version 1.0
 */
@Entity
public class Realizations {
    private static String[] months = new String[] {"Январь", "Февраль", "Март", "Апр.", "Май", "Июнь",
            "Июль", "Июль", "Август", "Сентябрь", "Октябрь", "Декабрь"};

    private Integer idRealiz;

    @javax.persistence.Column(name = "id_realiz")
    @Id
    public Integer getIdRealiz() {
        return idRealiz;
    }

    public void setIdRealiz(Integer idRealiz) {
        this.idRealiz = idRealiz;
    }

    private Date dateRealiz;

    @javax.persistence.Column(name = "date_realiz")
    @Basic
    public Date getDateRealiz() {
        return dateRealiz;
    }

    public void setDateRealiz(Date dateRealiz) {
        this.dateRealiz = dateRealiz;
    }

    @Transient
    public Integer getQuarter() {
        return (month + 2) / 3;
    }

    Integer month;

    @Formula(value = "extract(month from date_realiz)")
    public Integer getMonth() {
        return month;   //dateRealiz.getMonth() + 1;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    @Transient
    public String getNameMonth() {
        return months[dateRealiz.getMonth()];
    }

    private Float numRealiz;

    @javax.persistence.Column(name = "num_realiz")
    @Basic
    public Float getNumRealiz() {
        return numRealiz;
    }

    public void setNumRealiz(Float numRealiz) {
        this.numRealiz = numRealiz;
    }

    private Double sumRealiz;

    @javax.persistence.Column(name = "sum_realiz")
    @Basic
    public Double getSumRealiz() {
        return sumRealiz;
    }

    public void setSumRealiz(Double sumRealiz) {
        this.sumRealiz = sumRealiz;
    }

    private Short pcDiscount;

    @javax.persistence.Column(name = "pc_discount")
    @Basic
    public Short getPcDiscount() {
        return pcDiscount;
    }

    public void setPcDiscount(Short pcDiscount) {
        this.pcDiscount = pcDiscount;
    }

    private Float price;

    @javax.persistence.Column(name = "price")
    @Basic
    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Realizations that = (Realizations) o;

        if (dateRealiz != null ? !dateRealiz.equals(that.dateRealiz) : that.dateRealiz != null) return false;
        if (personal != null ? !personal.equals(that.personal) : that.personal != null) return false;
        if (wares != null ? !wares.equals(that.wares) : that.wares != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = dateRealiz != null ? dateRealiz.hashCode() : 0;
        result = 31 * result + (personal != null ? personal.hashCode() : 0);
        result = 31 * result + (wares != null ? wares.hashCode() : 0);
        return result;
    }

    private Wares wares;

    @ManyToOne(fetch = FetchType.LAZY)
    public
    @javax.persistence.JoinColumn(name = "id_wares", referencedColumnName = "id_wares")
    Wares getWares() {
        return wares;
    }

    public void setWares(Wares wares) {
        this.wares = wares;
    }

    private Personal personal;

    @ManyToOne(fetch = FetchType.LAZY)
    public
    @javax.persistence.JoinColumn(name = "id_pers", referencedColumnName = "id_pers", nullable = false)
    Personal getPersonal() {
        return personal;
    }

    public void setPersonal(Personal personal) {
        this.personal = personal;
    }
}
