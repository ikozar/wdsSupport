package ru.ki.entity.test;

import javax.persistence.*;

/**
 * @author ikozar
 * @version 1.0
 */
@javax.persistence.Table(name = "type_wares")
@Entity
public class TypeWares {
    private Long idTypeWares;

    @javax.persistence.Column(name = "id_type_wares")
    @Id
    public Long getIdTypeWares() {
        return idTypeWares;
    }

    public void setIdTypeWares(Long idTypeWares) {
        this.idTypeWares = idTypeWares;
    }

    private String naimTypeWares;

    @javax.persistence.Column(name = "naim_type_wares")
    @Basic
    public String getNaimTypeWares() {
        return naimTypeWares;
    }

    public void setNaimTypeWares(String naimTypeWares) {
        this.naimTypeWares = naimTypeWares;
    }

    private String rubTypeWares;

    @javax.persistence.Column(name = "rub_type_wares")
    @Basic
    public String getRubTypeWares() {
        return rubTypeWares;
    }

    public void setRubTypeWares(String rubTypeWares) {
        this.rubTypeWares = rubTypeWares;
    }

    private String naimTypeWaresE;

    @javax.persistence.Column(name = "naim_type_wares_e")
    @Basic
    public String getNaimTypeWaresE() {
        return naimTypeWaresE;
    }

    public void setNaimTypeWaresE(String naimTypeWaresE) {
        this.naimTypeWaresE = naimTypeWaresE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TypeWares typeWares = (TypeWares) o;

        if (idTypeWares != null ? !idTypeWares.equals(typeWares.idTypeWares) : typeWares.idTypeWares != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idTypeWares != null ? idTypeWares.hashCode() : 0;
        return result;
    }

    private TypeWares typeWaresParent;

    @ManyToOne(fetch = FetchType.LAZY)
    public
    @javax.persistence.JoinColumn(name = "id_type_wares_parent", referencedColumnName = "id_type_wares", nullable = false)
    TypeWares getTypeWaresParent() {
        return typeWaresParent;
    }

    public void setTypeWaresParent(TypeWares typeWaresParent) {
        this.typeWaresParent = typeWaresParent;
    }
}
