package ru.ki.model.entity;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author ikozar
 * @version 1.0
 */
@javax.persistence.Table(name = "type_subdiv")
@Entity
public class TypeSubdiv {
    private Integer idTypeSubdiv;

    @javax.persistence.Column(name = "id_type_subdiv")
    @Id
    public Integer getIdTypeSubdiv() {
        return idTypeSubdiv;
    }

    public void setIdTypeSubdiv(Integer idTypeSubdiv) {
        this.idTypeSubdiv = idTypeSubdiv;
    }

    private String naimTypeSubdiv;

    @javax.persistence.Column(name = "naim_type_subdiv")
    @Basic
    public String getNaimTypeSubdiv() {
        return naimTypeSubdiv;
    }

    public void setNaimTypeSubdiv(String naimTypeSubdiv) {
        this.naimTypeSubdiv = naimTypeSubdiv;
    }

    private String naimTypeSubdivE;

    @javax.persistence.Column(name = "naim_type_subdiv_e")
    @Basic
    public String getNaimTypeSubdivE() {
        return naimTypeSubdivE;
    }

    public void setNaimTypeSubdivE(String naimTypeSubdivE) {
        this.naimTypeSubdivE = naimTypeSubdivE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TypeSubdiv that = (TypeSubdiv) o;

        if (idTypeSubdiv != null ? !idTypeSubdiv.equals(that.idTypeSubdiv) : that.idTypeSubdiv != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idTypeSubdiv != null ? idTypeSubdiv.hashCode() : 0;
        return result;
    }
}
