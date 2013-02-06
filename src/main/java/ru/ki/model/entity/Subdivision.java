package ru.ki.model.entity;

import javax.persistence.*;

/**
 * @author ikozar
 * @version 1.0
 */
@Entity
public class Subdivision {

    private Integer idSubdiv;

    @Column(name = "id_subdiv")
    @Id
    public Integer getIdSubdiv() {
        return idSubdiv;
    }

    public void setIdSubdiv(Integer idSubdiv) {
        this.idSubdiv = idSubdiv;
    }

    private String naimSubdiv;

    @javax.persistence.Column(name = "naim_subdiv")
    @Basic
    public String getNaimSubdiv() {
        return naimSubdiv;
    }

    public void setNaimSubdiv(String naimSubdiv) {
        this.naimSubdiv = naimSubdiv;
    }

    private String rubSubdiv;

    @javax.persistence.Column(name = "rub_subdiv")
    @Basic
    public String getRubSubdiv() {
        return rubSubdiv;
    }

    public void setRubSubdiv(String rubSubdiv) {
        this.rubSubdiv = rubSubdiv;
    }

    private String naimSubdivE;

    @javax.persistence.Column(name = "naim_subdiv_e")
    @Basic
    public String getNaimSubdivE() {
        return naimSubdivE;
    }

    public void setNaimSubdivE(String naimSubdivE) {
        this.naimSubdivE = naimSubdivE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Subdivision that = (Subdivision) o;

        if (idSubdiv != null ? !idSubdiv.equals(that.idSubdiv) : that.idSubdiv != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idSubdiv != null ? idSubdiv.hashCode() : 0;
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

    private Subdivision subdivision;

    @ManyToOne(fetch = FetchType.LAZY)
    public
    @javax.persistence.JoinColumn(name = "id_subdiv_parent", referencedColumnName = "id_subdiv", nullable = false)
    Subdivision getSubdivision() {
        return subdivision;
    }

    public void setSubdivision(Subdivision subdivision) {
        this.subdivision = subdivision;
    }

    private TypeSubdiv typeSubdiv;

    @ManyToOne(fetch = FetchType.LAZY)
    public
    @javax.persistence.JoinColumn(name = "id_type_subdiv", referencedColumnName = "id_type_subdiv", nullable = false)
    TypeSubdiv getTypeSubdiv() {
        return typeSubdiv;
    }

    public void setTypeSubdiv(TypeSubdiv typeSubdiv) {
        this.typeSubdiv = typeSubdiv;
    }

    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    public
    @javax.persistence.JoinColumn(name = "id_store", referencedColumnName = "id_store", nullable = false)
    Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }
}
