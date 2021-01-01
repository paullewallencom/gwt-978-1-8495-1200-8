/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.packtpub.beans;

import com.packtpub.client.dto.EmployeeDTO;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Shamsuddin
 */
@Entity
@Table(name = "employee")
@NamedQueries(
{
    @NamedQuery(name = "Employee.findAll", query = "SELECT e FROM Employee e"),
    @NamedQuery(name = "Employee.findByEmployeeId", query = "SELECT e FROM Employee e WHERE e.employeeId = :employeeId"),
    @NamedQuery(name = "Employee.findByName", query = "SELECT e FROM Employee e WHERE e.name = :name"),
    @NamedQuery(name = "Employee.findByDesignation", query = "SELECT e FROM Employee e WHERE e.designation = :designation"),
    @NamedQuery(name = "Employee.findByGender", query = "SELECT e FROM Employee e WHERE e.gender = :gender"),
    @NamedQuery(name = "Employee.findByMobile", query = "SELECT e FROM Employee e WHERE e.mobile = :mobile")
})
public class Employee implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "EmployeeId")
    private Integer employeeId;
    @Basic(optional = false)
    @Column(name = "Name")
    private String name;
    @Basic(optional = false)
    @Column(name = "Designation")
    private String designation;
    @Basic(optional = false)
    @Column(name = "Gender")
    private String gender;
    @Basic(optional = false)
    @Column(name = "Mobile")
    private String mobile;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employee")
    private List<Users> usersList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employee")
    private List<Sales> salesList;
    @JoinColumn(name = "BranchId", referencedColumnName = "BranchId")
    @ManyToOne(optional = false)
    private Branch branch;

    public Employee()
    {
    }

    public Employee(Integer employeeId)
    {
        this.employeeId = employeeId;
    }

    public Employee(Integer employeeId, String name, String designation, String gender, String mobile)
    {
        this.employeeId = employeeId;
        this.name = name;
        this.designation = designation;
        this.gender = gender;
        this.mobile = mobile;
    }

    Employee(EmployeeDTO employee)
    {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public Integer getEmployeeId()
    {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId)
    {
        this.employeeId = employeeId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDesignation()
    {
        return designation;
    }

    public void setDesignation(String designation)
    {
        this.designation = designation;
    }

    public String getGender()
    {
        return gender;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public String getMobile()
    {
        return mobile;
    }

    public void setMobile(String mobile)
    {
        this.mobile = mobile;
    }

    public List<Users> getUsersList()
    {
        return usersList;
    }

    public void setUsersList(List<Users> usersList)
    {
        this.usersList = usersList;
    }

    public List<Sales> getSalesList()
    {
        return salesList;
    }

    public void setSalesList(List<Sales> salesList)
    {
        this.salesList = salesList;
    }

    public Branch getBranch()
    {
        return branch;
    }

    public void setBranch(Branch branch)
    {
        this.branch = branch;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (employeeId != null ? employeeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Employee))
        {
            return false;
        }
        Employee other = (Employee) object;
        if ((this.employeeId == null && other.employeeId != null) || (this.employeeId != null && !this.employeeId.equals(other.employeeId)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "com.packtpub.beans.Employee[employeeId=" + employeeId + "]";
    }

}
