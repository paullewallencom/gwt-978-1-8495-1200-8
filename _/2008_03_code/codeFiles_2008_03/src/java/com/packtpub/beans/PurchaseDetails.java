/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.packtpub.beans;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Shamsuddin
 */
@Entity
@Table(name = "purchase_details")
@NamedQueries(
{
    @NamedQuery(name = "PurchaseDetails.findAll", query = "SELECT p FROM PurchaseDetails p"),
    @NamedQuery(name = "PurchaseDetails.findByPurchaseNo", query = "SELECT p FROM PurchaseDetails p WHERE p.purchaseDetailsPK.purchaseNo = :purchaseNo"),
    @NamedQuery(name = "PurchaseDetails.findByProductCode", query = "SELECT p FROM PurchaseDetails p WHERE p.purchaseDetailsPK.productCode = :productCode"),
    @NamedQuery(name = "PurchaseDetails.findByPurchaseQuantity", query = "SELECT p FROM PurchaseDetails p WHERE p.purchaseQuantity = :purchaseQuantity"),
    @NamedQuery(name = "PurchaseDetails.findByUnitPurchasePrice", query = "SELECT p FROM PurchaseDetails p WHERE p.unitPurchasePrice = :unitPurchasePrice")
})
public class PurchaseDetails implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PurchaseDetailsPK purchaseDetailsPK;
    @Basic(optional = false)
    @Column(name = "PurchaseQuantity")
    private int purchaseQuantity;
    @Basic(optional = false)
    @Column(name = "UnitPurchasePrice")
    private double unitPurchasePrice;
    @JoinColumn(name = "PurchaseNo", referencedColumnName = "PurchaseNo", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Purchase purchase;
    @JoinColumn(name = "ProductCode", referencedColumnName = "ProductCode", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Product product;

    public PurchaseDetails()
    {
    }

    public PurchaseDetails(PurchaseDetailsPK purchaseDetailsPK)
    {
        this.purchaseDetailsPK = purchaseDetailsPK;
    }

    public PurchaseDetails(PurchaseDetailsPK purchaseDetailsPK, int purchaseQuantity, double unitPurchasePrice)
    {
        this.purchaseDetailsPK = purchaseDetailsPK;
        this.purchaseQuantity = purchaseQuantity;
        this.unitPurchasePrice = unitPurchasePrice;
    }

    public PurchaseDetails(int purchaseNo, int productCode)
    {
        this.purchaseDetailsPK = new PurchaseDetailsPK(purchaseNo, productCode);
    }

    public PurchaseDetailsPK getPurchaseDetailsPK()
    {
        return purchaseDetailsPK;
    }

    public void setPurchaseDetailsPK(PurchaseDetailsPK purchaseDetailsPK)
    {
        this.purchaseDetailsPK = purchaseDetailsPK;
    }

    public int getPurchaseQuantity()
    {
        return purchaseQuantity;
    }

    public void setPurchaseQuantity(int purchaseQuantity)
    {
        this.purchaseQuantity = purchaseQuantity;
    }

    public double getUnitPurchasePrice()
    {
        return unitPurchasePrice;
    }

    public void setUnitPurchasePrice(double unitPurchasePrice)
    {
        this.unitPurchasePrice = unitPurchasePrice;
    }

    public Purchase getPurchase()
    {
        return purchase;
    }

    public void setPurchase(Purchase purchase)
    {
        this.purchase = purchase;
    }

    public Product getProduct()
    {
        return product;
    }

    public void setProduct(Product product)
    {
        this.product = product;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (purchaseDetailsPK != null ? purchaseDetailsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PurchaseDetails))
        {
            return false;
        }
        PurchaseDetails other = (PurchaseDetails) object;
        if ((this.purchaseDetailsPK == null && other.purchaseDetailsPK != null) || (this.purchaseDetailsPK != null && !this.purchaseDetailsPK.equals(other.purchaseDetailsPK)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "com.packtpub.beans.PurchaseDetails[purchaseDetailsPK=" + purchaseDetailsPK + "]";
    }

}
