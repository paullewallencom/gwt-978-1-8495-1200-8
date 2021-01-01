/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.packtpub.client.dto;

import com.packtpub.beans.*;
import java.io.Serializable;

/**
 *
 * @author Shamsuddin
 */

public class SalesDetailsDTO implements Serializable {
    protected SalesDetailsPK salesDetailsPK;
    private int salesQuantity;
    private double unitSalesPrice;
    private Product product;
    private Sales sales;

    public SalesDetailsDTO()
    {
    }

    public SalesDetailsDTO(SalesDetailsPK salesDetailsPK)
    {
        this.salesDetailsPK = salesDetailsPK;
    }

    public SalesDetailsDTO(SalesDetailsPK salesDetailsPK, int salesQuantity, double unitSalesPrice)
    {
        this.salesDetailsPK = salesDetailsPK;
        this.salesQuantity = salesQuantity;
        this.unitSalesPrice = unitSalesPrice;
    }

    public SalesDetailsDTO(int salesNo, int productCode)
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
        if (!(object instanceof SalesDetailsDTO))
        {
            return false;
        }
        SalesDetailsDTO other = (SalesDetailsDTO) object;
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
