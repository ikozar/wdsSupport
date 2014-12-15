package ru.ki.entity.test;

import javax.persistence.*;
import java.util.Date;

/**
 * @author ikozar
 * @version 1.0
 */
@Entity
//@Table(name = "Employee")
public class Employee {
    private Long id;

    @javax.persistence.Column(name = "id_employee")
    @Id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private String fullName;

    @javax.persistence.Column(name = "full_name")
    @Basic
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    private TypeEmployee employeeType;

    @javax.persistence.Column(name = "emp_type")
    @Enumerated(EnumType.ORDINAL)
    @Basic
    public TypeEmployee getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(TypeEmployee employeeType) {
        this.employeeType = employeeType;
    }

    private Date dateBorn;

    @javax.persistence.Column(name = "date_born")
    @Basic
    public Date getDateBorn() {
        return dateBorn;
    }

    public void setDateBorn(Date dateBorn) {
        this.dateBorn = dateBorn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        if (id != null ? !id.equals(employee.id) : employee.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
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
        return getFullName() + " (" + getPrSex().name() + ')';
    }

}
