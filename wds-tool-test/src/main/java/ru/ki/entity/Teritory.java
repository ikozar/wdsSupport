package ru.ki.entity;

import javax.persistence.*;

/**
 * @author ikozar
 * @version 1.0
 */
@Entity
public class Teritory {
    private Integer id;

    @javax.persistence.Column(name = "id_ter")
    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private String name;

    @javax.persistence.Column(name = "name_ter")
    @Basic
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private Short typeTeritory;

    @javax.persistence.Column(name = "pr_ter")
    @Basic
    public Short getTypeTeritory() {
        return typeTeritory;
    }

    public void setTypeTeritory(Short typeTeritory) {
        this.typeTeritory = typeTeritory;
    }

    private String rubric;

    @javax.persistence.Column(name = "rub_terit")
    @Basic
    public String getRubric() {
        return rubric;
    }

    public void setRubric(String rubric) {
        this.rubric = rubric;
    }

    private String nameFull;

    @javax.persistence.Column(name = "name_ter_e")
    @Basic
    public String getNameFull() {
        return nameFull;
    }

    public void setNameFull(String nameFull) {
        this.nameFull = nameFull;
    }

    public Teritory() {
    }

    public Teritory(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Teritory)) return false;

        Teritory teritory = (Teritory) o;

        if (getId() != null ?
                !getId().equals(teritory.getId()) :
                teritory.getId() != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
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
        return "Teritory{" + id +
                ": " + name +
                '}';
    }
}
