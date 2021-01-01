/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.packtpub.beans;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
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
@Table(name = "supplier")
@NamedQueries(
{
    @NamedQuery(name = "Supplier.findAll", query = "SELECT s FROM Supplier s"),
    @NamedQuery(name = "Supplier.findBySupplierNo", query = "SELECT s FROM Supplier s WHERE s.supplierNo = :supplierNo"),
    @NamedQuery(name = "Supplier.findBySupplierName", query = "SELECT s FROM Supplier s WHERE s.supplierName = :supplierName"),
    @NamedQuery(name = "Supplier.findByAddress", query = "SELECT s FROM Supplier s WHERE s.address = :address"),
    @NamedQuery(name = "Supplier.findByContactNo", query = "SELECT s FROM Supplier s WHERE s.contactNo = :contactNo")
})
public class Supplier implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "SupplierNo")
    private Integer supplierNo;
    @Basic(optional = false)
    @Column(name = "SupplierName")
    private String supplierName;
    @Basic(optional = false)
    @Column(name = "Address")
    private String address;
    @Column(name = "ContactNo")
    private String contactNo;
    @OneToMany(mappedBy = "supplier")
    private List<Purchase> purchaseList;

    public Supplier()
    {
    }

    public Supplier(Integer supplierNo)
    {
        this.supplierNo = supplierNo;
    }

    public Supplier(Integer supplierNo, String supplierName, String address)
    {
        this.supplierNo = supplierNo;
        this.supplierName = supplierName;
        this.address = address;
    }

    public Integer getSupplierNo()
    {
        return supplierNo;
    }

    public void setSupplierNo(Integer supplierNo)
    {
        this.supplierNo = supplierNo;
    }

    public String getSupplierName()
    {
        return supplierName;
    }

    public void setSupplierName(String supplierName)
    {
        this.supplierName = supplierName;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getContactNo()
    {
        return contactNo;
    }

    public void setContactNo(String contactNo)
    {
        this.contactNo = contactNo;
    }

    public List<Purchase> getPurchaseList()
    {
        return purchaseList;
    }

    public void setPurchaseList(List<Purchase> purchaseList)
    {
        this.purchaseList = purchaseList;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (supplierNo != null ? supplierNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Supplier))
        {
            return false;
        }
        Supplier other = (Supplier) object;
        if ((this.supplierNo == null && other.supplierNo != null) || (this.supplierNo != null && !this.supplierNo.equals(other.supplierNo)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "com.packtpub.beans.Supplier[supplierNo=" + supplierNo + "]";
    }

}
