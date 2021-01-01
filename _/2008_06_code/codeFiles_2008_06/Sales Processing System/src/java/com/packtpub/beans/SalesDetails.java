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
@Table(name = "sales_details")
@NamedQueries(
{
    @NamedQuery(name = "SalesDetails.findAll", query = "SELECT s FROM SalesDetails s"),
    @NamedQuery(name = "SalesDetails.findBySalesNo", query = "SELECT s FROM SalesDetails s WHERE s.salesDetailsPK.salesNo = :salesNo"),
    @NamedQuery(name = "SalesDetails.findByProductCode", query = "SELECT s FROM SalesDetails s WHERE s.salesDetailsPK.productCode = :productCode"),
    @NamedQuery(name = "SalesDetails.findBySalesQuantity", query = "SELECT s FROM SalesDetails s WHERE s.salesQuantity = :salesQuantity"),
    @NamedQuery(name = "SalesDetails.findByUnitSalesPrice", query = "SELECT s FROM SalesDetails s WHERE s.unitSalesPrice = :unitSalesPrice")
})
public class SalesDetails implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SalesDetailsPK salesDetailsPK;
    @Basic(optional = false)
    @Column(name = "SalesQuantity")
    private int salesQuantity;
    @Basic(optional = false)
    @Column(name = "UnitSalesPrice")
    private double unitSalesPrice;
    @JoinColumn(name = "ProductCode", referencedColumnName = "ProductCode", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Product product;
    @JoinColumn(name = "SalesNo", referencedColumnName = "SalesNo", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Sales sales;

    public SalesDetails()
    {
    }

    public SalesDetails(SalesDetailsPK salesDetailsPK)
    {
        this.salesDetailsPK = salesDetailsPK;
    }

    public SalesDetails(SalesDetailsPK salesDetailsPK, int salesQuantity, double unitSalesPrice)
    {
        this.salesDetailsPK = salesDetailsPK;
        this.salesQuantity = salesQuantity;
        this.unitSalesPrice = unitSalesPrice;
    }

    public SalesDetails(int salesNo, int productCode)
    {
        this.salesDetailsPK = new SalesDetailsPK(salesNo, productCode);
    }

    public SalesDetailsPK getSalesDetailsPK()
    {
        return salesDetailsPK;
    }

    public void setSalesDetailsPK(SalesDetailsPK salesDetailsPK)
    {
        this.salesDetailsPK = salesDetailsPK;
    }

    public int getSalesQuantity()
    {
        return salesQuantity;
    }

    public void setSalesQuantity(int salesQuantity)
    {
        this.salesQuantity = salesQuantity;
    }

    public double getUnitSalesPrice()
    {
        return unitSalesPrice;
    }

    public void setUnitSalesPrice(double unitSalesPrice)
    {
        this.unitSalesPrice = unitSalesPrice;
    }

    public Product getProduct()
    {
        return product;
    }

    public void setProduct(Product product)
    {
        this.product = product;
    }

    public Sales getSales()
    {
        return sales;
    }

    public void setSales(Sales sales)
    {
        this.sales = sales;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (salesDetailsPK != null ? salesDetailsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SalesDetails))
        {
            return false;
        }
        SalesDetails other = (SalesDetails) object;
        if ((this.salesDetailsPK == null && other.salesDetailsPK != null) || (this.salesDetailsPK != null && !this.salesDetailsPK.equals(other.salesDetailsPK)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "com.packtpub.beans.SalesDetails[salesDetailsPK=" + salesDetailsPK + "]";
    }

}
