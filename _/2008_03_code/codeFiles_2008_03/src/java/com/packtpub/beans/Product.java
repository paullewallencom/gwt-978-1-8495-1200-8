/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.packtpub.beans;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Shamsuddin
 */
@Entity
@Table(name = "product")
@NamedQueries(
{
    @NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p"),
    @NamedQuery(name = "Product.findByProductCode", query = "SELECT p FROM Product p WHERE p.productCode = :productCode"),
    @NamedQuery(name = "Product.findByName", query = "SELECT p FROM Product p WHERE p.name = :name"),
    @NamedQuery(name = "Product.findByDescription", query = "SELECT p FROM Product p WHERE p.description = :description")
})
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ProductCode")
    private Integer productCode;
    @Basic(optional = false)
    @Column(name = "Name")
    private String name;
    @Column(name = "Description")
    private String description;
    @Lob
    @Column(name = "Image")
    private byte[] image;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private List<PurchaseDetails> purchaseDetailsList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "product")
    private Stock stock;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private List<SalesDetails> salesDetailsList;

    public Product()
    {
    }

    public Product(Integer productCode)
    {
        this.productCode = productCode;
    }

    public Product(Integer productCode, String name)
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

    public List<PurchaseDetails> getPurchaseDetailsList()
    {
        return purchaseDetailsList;
    }

    public void setPurchaseDetailsList(List<PurchaseDetails> purchaseDetailsList)
    {
        this.purchaseDetailsList = purchaseDetailsList;
    }

    public Stock getStock()
    {
        return stock;
    }

    public void setStock(Stock stock)
    {
        this.stock = stock;
    }

    public List<SalesDetails> getSalesDetailsList()
    {
        return salesDetailsList;
    }

    public void setSalesDetailsList(List<SalesDetails> salesDetailsList)
    {
        this.salesDetailsList = salesDetailsList;
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
        if (!(object instanceof Product))
        {
            return false;
        }
        Product other = (Product) object;
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
