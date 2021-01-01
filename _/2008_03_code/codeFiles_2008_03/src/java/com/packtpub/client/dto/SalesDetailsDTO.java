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
    protected SalesDetailsPKDTO salesDetailsPKDTO;
    private int salesQuantity;
    private double unitSalesPrice;
    private ProductDTO productDTO;
    private SalesDTO salesDTO;

    public SalesDetailsDTO()
    {
    }

    public SalesDetailsDTO(SalesDetailsPKDTO salesDetailsPK)
    {
        this.salesDetailsPKDTO = salesDetailsPK;
    }

    public SalesDetailsDTO(SalesDetailsPKDTO salesDetailsPKDTO, int salesQuantity, double unitSalesPrice)
    {
        this.salesDetailsPKDTO = salesDetailsPKDTO;
        this.salesQuantity = salesQuantity;
        this.unitSalesPrice = unitSalesPrice;
    }

    public SalesDetailsDTO(int salesNo, int productCode)
    {
        this.salesDetailsPKDTO = new SalesDetailsPKDTO(salesNo, productCode);
    }

    public SalesDetailsPKDTO getSalesDetailsPK()
    {
        return salesDetailsPKDTO;
    }

    public void setSalesDetailsPK(SalesDetailsPKDTO salesDetailsPK)
    {
        this.salesDetailsPKDTO = salesDetailsPK;
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

    public ProductDTO getProduct()
    {
        return productDTO;
    }

    public void setProduct(ProductDTO product)
    {
        this.productDTO = product;
    }

    public SalesDTO getSales()
    {
        return salesDTO;
    }

    public void setSales(SalesDTO sales)
    {
        this.salesDTO = sales;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (salesDetailsPKDTO != null ? salesDetailsPKDTO.hashCode() : 0);
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
        if ((this.salesDetailsPKDTO == null && other.salesDetailsPKDTO != null) || (this.salesDetailsPKDTO != null && !this.salesDetailsPKDTO.equals(other.salesDetailsPKDTO)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "com.packtpub.beans.SalesDetails[salesDetailsPK=" + salesDetailsPKDTO + "]";
    }

}
