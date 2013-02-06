package ru.ki.model.entity;

import javax.persistence.*;

/**
 * @author ikozar
 * @version 1.0
 */
@Entity
public class Store {
    private Integer idStore;

    @javax.persistence.Column(name = "id_store")
    @Id
    public Integer getIdStore() {
        return idStore;
    }

    public void setIdStore(Integer idStore) {
        this.idStore = idStore;
    }

    private String naimStore;

    @javax.persistence.Column(name = "naim_store")
    @Basic
    public String getNaimStore() {
        return naimStore;
    }

    public void setNaimStore(String naimStore) {
        this.naimStore = naimStore;
    }

    private String naimStoreE;

    @javax.persistence.Column(name = "naim_store_e")
    @Basic
    public String getNaimStoreE() {
        return naimStoreE;
    }

    public void setNaimStoreE(String naimStoreE) {
        this.naimStoreE = naimStoreE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Store store = (Store) o;

        if (idStore != null ? !idStore.equals(store.idStore) : store.idStore != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idStore != null ? idStore.hashCode() : 0;
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

    @Override
    public String toString() {
        return "Store{" + idStore +
                ": " + naimStore + 
                '}';
    }

}
