/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.packtpub.client.dto;

import com.packtpub.beans.*;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Shamsuddin
 */

public class ProductDTO implements Serializable {
    private Integer productCode;
    private String name;
    private String description;
    private byte[] image;

    private StockDTO stock;
    private List<SalesDetailsDTO> salesDetailsDTOList;

    public ProductDTO()
    {
    }

    public ProductDTO(Integer productCode)
    {
        this.productCode = productCode;
    }

    public ProductDTO(Integer productCode, String name)
    {
        this.productCode = productCode;
        this.name = name;
    }

    public Integer getProductCode()
    {
        return productCode;
    }

    public void setProductCode(Integer productCode)
    {
        this.productCode = productCode;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public byte[] getImage()
    {
        return image;
    }

    public void setImage(byte[] image)
    {
        this.image = image;
    }

   
    public StockDTO getStock()
    {
        return stock;
    }

    public void setStock(StockDTO stock)
    {
        this.stock = stock;
    }

    public List<SalesDetailsDTO> getSalesDetailsList()
    {
        return salesDetailsDTOList;
    }

    public void setSalesDetailsList(List<SalesDetailsDTO> salesDetailsList)
    {
        this.salesDetailsDTOList = salesDetailsList;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (productCode != null ? productCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductDTO))
        {
            return false;
        }
        ProductDTO other = (ProductDTO) object;
        if ((this.productCode == null && other.productCode != null) || (this.productCode != null && !this.productCode.equals(other.productCode)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "com.packtpub.beans.Product[productCode=" + productCode + "]";
    }

}
