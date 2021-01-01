/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.packtpub.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Shamsuddin
 */
@Entity
@Table(name = "purchase")
@NamedQueries(
{
    @NamedQuery(name = "Purchase.findAll", query = "SELECT p FROM Purchase p"),
    @NamedQuery(name = "Purchase.findByPurchaseNo", query = "SELECT p FROM Purchase p WHERE p.purchaseNo = :purchaseNo"),
    @NamedQuery(name = "Purchase.findByPurchaseDate", query = "SELECT p FROM Purchase p WHERE p.purchaseDate = :purchaseDate")
})
public class Purchase implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "PurchaseNo")
    private Integer purchaseNo;
    @Basic(optional = false)
    @Column(name = "PurchaseDate")
    @Temporal(TemporalType.DATE)
    private Date purchaseDate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "purchase")
    private List<PurchaseDetails> purchaseDetailsList;
    @JoinColumn(name = "SupplierNo", referencedColumnName = "SupplierNo")
    @ManyToOne
    private Supplier supplier;

    public Purchase()
    {
    }

    public Purchase(Integer purchaseNo)
    {
        this.purchaseNo = purchaseNo;
    }

    public Purchase(Integer purchaseNo, Date purchaseDate)
    {
        this.purchaseNo = purchaseNo;
        this.purchaseDate = purchaseDate;
    }

    public Integer getPurchaseNo()
    {
        return purchaseNo;
    }

    public void setPurchaseNo(Integer purchaseNo)
    {
        this.purchaseNo = purchaseNo;
    }

    public Date getPurchaseDate()
    {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate)
    {
        this.purchaseDate = purchaseDate;
    }

    public List<PurchaseDetails> getPurchaseDetailsList()
    {
        return purchaseDetailsList;
    }

    public void setPurchaseDetailsList(List<PurchaseDetails> purchaseDetailsList)
    {
        this.purchaseDetailsList = purchaseDetailsList;
    }

    public Supplier getSupplier()
    {
        return supplier;
    }

    public void setSupplier(Supplier supplier)
    {
        this.supplier = supplier;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (purchaseNo != null ? purchaseNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Purchase))
        {
            return false;
        }
        Purchase other = (Purchase) object;
        if ((this.purchaseNo == null && other.purchaseNo != null) || (this.purchaseNo != null && !this.purchaseNo.equals(other.purchaseNo)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "com.packtpub.beans.Purchase[purchaseNo=" + purchaseNo + "]";
    }

}
