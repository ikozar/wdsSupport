package ru.ki.model.entity;

import javax.persistence.*;
import java.sql.Date;

/**
 * @author ikozar
 * @version 1.0
 */
@Entity
//@Table(name = "personal")
public class Personal {
    private Integer idPers;

    @javax.persistence.Column(name = "id_pers")
    @Id
    public Integer getIdPers() {
        return idPers;
    }

    public void setIdPers(Integer idPers) {
        this.idPers = idPers;
    }

    private String fioPers;

    @javax.persistence.Column(name = "fio_pers")
    @Basic
    public String getFioPers() {
        return fioPers;
    }

    public void setFioPers(String fioPers) {
        this.fioPers = fioPers;
    }

    private PrSex prSex;

    @javax.persistence.Column(name = "pr_sex")
    @Enumerated(EnumType.STRING)
    @Basic
    public PrSex getPrSex() {
        return prSex;
    }

    public void setPrSex(PrSex prSex) {
        this.prSex = prSex;
    }

    private TypeEmployee prRukovod;

    @javax.persistence.Column(name = "pr_rukovod")
    @Enumerated(EnumType.ORDINAL)
    @Basic
    public TypeEmployee getPrRukovod() {
        return prRukovod;
    }

    public void setPrRukovod(TypeEmployee prRukovod) {
        this.prRukovod = prRukovod;
    }

    private Date dateRozd;

    @javax.persistence.Column(name = "date_rozd")
    @Basic
    public Date getDateRozd() {
        return dateRozd;
    }

    public void setDateRozd(Date dateRozd) {
        this.dateRozd = dateRozd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Personal personal = (Personal) o;

        if (idPers != null ? !idPers.equals(personal.idPers) : personal.idPers != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idPers != null ? idPers.hashCode() : 0;
        return result;
    }

    private Subdivision subdivision;

    @ManyToOne(fetch = FetchType.LAZY)
    public
    @javax.persistence.JoinColumn(name = "id_subdiv", referencedColumnName = "id_subdiv", nullable = false)
    Subdivision getSubdivision() {
        return subdivision;
    }

    public void setSubdivision(Subdivision subdivision) {
        this.subdivision = subdivision;
    }

    @Override
    public String toString() {
        return getFioPers() + " (" + getPrSex().name() + ')';
    }

}
