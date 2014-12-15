package ru.ki.entity.test;

import javax.persistence.*;

/**
 * @author ikozar
 * @version 1.0
 */
@Entity
public class Vendor {
    private Long idVendor;

    @javax.persistence.Column(name = "id_vendor")
    @Id
    public Long getIdVendor() {
        return idVendor;
    }

    public void setIdVendor(Long idVendor) {
        this.idVendor = idVendor;
    }

    private String naimVendor;

    @javax.persistence.Column(name = "naim_vendor")
    @Basic
    public String getNaimVendor() {
        return naimVendor;
    }

    public void setNaimVendor(String naimVendor) {
        this.naimVendor = naimVendor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vendor vendor = (Vendor) o;

        if (idVendor != null ? !idVendor.equals(vendor.idVendor) : vendor.idVendor != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idVendor != null ? idVendor.hashCode() : 0;
        return result;
    }

    private Teritory teritory;

    @ManyToOne(fetch = FetchType.LAZY)
    public
    @javax.persistence.JoinColumn(name = "id_ter", referencedColumnName = "id_ter", nullable = false)
    Teritory getTeritory() {
        return teritory;
    }

    public void setTeritory(Teritory teritory) {
        this.teritory = teritory;
    }
}
