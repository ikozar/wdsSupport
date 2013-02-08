package ru.ki.model.entity;

import javax.persistence.*;

/**
 * @author ikozar
 * @version 1.0
 */
@Entity
public class Subdivision {

    private Integer id;

    @Column(name = "id_subdiv")
    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private String name;

    @javax.persistence.Column(name = "name_subdiv")
    @Basic
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String rubric;

    @javax.persistence.Column(name = "rub_subdiv")
    @Basic
    public String getRubric() {
        return rubric;
    }

    public void setRubric(String rubric) {
        this.rubric = rubric;
    }

    private String nameFull;

    @javax.persistence.Column(name = "name_subdiv_e")
    @Basic
    public String getNameFull() {
        return nameFull;
    }

    public void setNameFull(String nameFull) {
        this.nameFull = nameFull;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Subdivision that = (Subdivision) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
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

    private Subdivision parentPubdivision;

    @ManyToOne(fetch = FetchType.LAZY)
    public
    @javax.persistence.JoinColumn(name = "id_subdiv_parent", referencedColumnName = "id_subdiv", nullable = false)
    Subdivision getParentPubdivision() {
        return parentPubdivision;
    }

    public void setParentPubdivision(Subdivision parentPubdivision) {
        this.parentPubdivision = parentPubdivision;
    }

    private TypeSubdiv typeSubdivision;

    @ManyToOne(fetch = FetchType.LAZY)
    public
    @javax.persistence.JoinColumn(name = "id_type_subdiv", referencedColumnName = "id_type_subdiv", nullable = false)
    TypeSubdiv getTypeSubdivision() {
        return typeSubdivision;
    }

    public void setTypeSubdivision(TypeSubdiv typeSubdivision) {
        this.typeSubdivision = typeSubdivision;
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
