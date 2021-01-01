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

public class StockDTO implements Serializable {
   
    private Integer productCode;
    private int quantity;
    private int reorderLevel;
    private ProductDTO productDTO;
    private BranchDTO branchDTO;

    public StockDTO()
    {
    }

    public StockDTO(Integer productCode)
    {
        this.productCode = productCode;
    }

    public StockDTO(Integer productCode, int quantity, int reorderLevel)
    {
        this.productCode = productCode;
        this.quantity = quantity;
        this.reorderLevel = reorderLevel;
    }

    public Integer getProductCode()
    {
        return productCode;
    }

    public void setProductCode(Integer productCode)
    {
        this.productCode = productCode;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    public int getReorderLevel()
    {
        return reorderLevel;
    }

    public void setReorderLevel(int reorderLevel)
    {
        this.reorderLevel = reorderLevel;
    }

    public ProductDTO getProduct()
    {
        return productDTO;
    }

    public void setProduct(ProductDTO product)
    {
        this.productDTO = product;
    }

    public BranchDTO getBranch()
    {
        return branchDTO;
    }

    public void setBranch(BranchDTO branch)
    {
        this.branchDTO = branch;
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
        if (!(object instanceof StockDTO))
        {
            return false;
        }
        StockDTO other = (StockDTO) object;
        if ((this.productCode == null && other.productCode != null) || (this.productCode != null && !this.productCode.equals(other.productCode)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "com.packtpub.beans.Stock[productCode=" + productCode + "]";
    }

}
