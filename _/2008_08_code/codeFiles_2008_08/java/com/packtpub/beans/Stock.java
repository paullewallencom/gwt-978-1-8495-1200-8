/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.packtpub.beans;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Shamsuddin
 */
@Entity
@Table(name = "stock")
@NamedQueries(
{
    @NamedQuery(name = "Stock.findAll", query = "SELECT s FROM Stock s"),
    @NamedQuery(name = "Stock.findByProductCode", query = "SELECT s FROM Stock s WHERE s.productCode = :productCode"),
    @NamedQuery(name = "Stock.findByQuantity", query = "SELECT s FROM Stock s WHERE s.quantity = :quantity"),
    @NamedQuery(name = "Stock.findByReorderLevel", query = "SELECT s FROM Stock s WHERE s.reorderLevel = :reorderLevel")
})
public class Stock implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ProductCode")
    private Integer productCode;
    @Basic(optional = false)
    @Column(name = "Quantity")
    private int quantity;
    @Basic(optional = false)
    @Column(name = "ReorderLevel")
    private int reorderLevel;
    @JoinColumn(name = "ProductCode", referencedColumnName = "ProductCode", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Product product;
    @JoinColumn(name = "BranchId", referencedColumnName = "BranchId")
    @ManyToOne(optional = false)
    private Branch branch;

    public Stock()
    {
    }

    public Stock(Integer productCode)
    {
        this.productCode = productCode;
    }

    public Stock(Integer productCode, int quantity, int reorderLevel)
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

    public Product getProduct()
    {
        return product;
    }

    public void setProduct(Product product)
    {
        this.product = product;
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
        hash += (productCode != null ? productCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Stock))
        {
            return false;
        }
        Stock other = (Stock) object;
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
