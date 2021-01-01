/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.packtpub.beans;

import com.packtpub.client.dto.BranchDTO;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Shamsuddin
 */
@Entity
@Table(name = "branch")
@NamedQueries(
{
    @NamedQuery(name = "Branch.findAll", query = "SELECT b FROM Branch b"),
    @NamedQuery(name = "Branch.findByBranchId", query = "SELECT b FROM Branch b WHERE b.branchId = :branchId"),
    @NamedQuery(name = "Branch.findByName", query = "SELECT b FROM Branch b WHERE b.name = :name"),
    @NamedQuery(name = "Branch.findByLocation", query = "SELECT b FROM Branch b WHERE b.location = :location")
})
public class Branch implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "BranchId")
    private Integer branchId;
    @Basic(optional = false)
    @Column(name = "Name")
    private String name;
    @Basic(optional = false)
    @Column(name = "Location")
    private String location;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "branch")
    private List<Stock> stockList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "branch")
    private List<Employee> employeeList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "branch")
    private List<SalesDesk> salesDeskList;

    public Branch()
    {
    }

    public Branch(Integer branchId)
    {
        this.branchId = branchId;
    }

    public Branch(Integer branchId, String name, String location)
    {
        this.branchId = branchId;
        this.name = name;
        this.location = location;
    }

    public Branch(BranchDTO branchDTO)
    {
        setBranchId(branchDTO.getBranchId());
        setName(branchDTO.getName());
        setLocation(branchDTO.getLocation());
        
    }

    public Integer getBranchId()
    {
        return branchId;
    }

    public void setBranchId(Integer branchId)
    {
        this.branchId = branchId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getLocation()
    {
        return location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    public List<Stock> getStockList()
    {
        return stockList;
    }

    public void setStockList(List<Stock> stockList)
    {
        this.stockList = stockList;
    }

    public List<Employee> getEmployeeList()
    {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList)
    {
        this.employeeList = employeeList;
    }

    public List<SalesDesk> getSalesDeskList()
    {
        return salesDeskList;
    }

    public void setSalesDeskList(List<SalesDesk> salesDeskList)
    {
        this.salesDeskList = salesDeskList;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (branchId != null ? branchId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Branch))
        {
            return false;
        }
        Branch other = (Branch) object;
        if ((this.branchId == null && other.branchId != null) || (this.branchId != null && !this.branchId.equals(other.branchId)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "com.packtpub.beans.Branch[branchId=" + branchId + "]";
    }

}
