/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.packtpub.beans;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Shamsuddin
 */
@Embeddable
public class SalesDetailsPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "SalesNo")
    private int salesNo;
    @Basic(optional = false)
    @Column(name = "ProductCode")
    private int productCode;

    public SalesDetailsPK()
    {
    }

    public SalesDetailsPK(int salesNo, int productCode)
    {
        this.salesNo = salesNo;
        this.productCode = productCode;
    }

    public int getSalesNo()
    {
        return salesNo;
    }

    public void setSalesNo(int salesNo)
    {
        this.salesNo = salesNo;
    }

    public int getProductCode()
    {
        return productCode;
    }

    public void setProductCode(int productCode)
    {
        this.productCode = productCode;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (int) salesNo;
        hash += (int) productCode;
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SalesDetailsPK))
        {
            return false;
        }
        SalesDetailsPK other = (SalesDetailsPK) object;
        if (this.salesNo != other.salesNo)
        {
            return false;
        }
        if (this.productCode != other.productCode)
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "com.packtpub.beans.SalesDetailsPK[salesNo=" + salesNo + ", productCode=" + productCode + "]";
    }

}
