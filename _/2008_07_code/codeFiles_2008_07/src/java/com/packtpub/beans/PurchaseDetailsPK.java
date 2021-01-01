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
public class PurchaseDetailsPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "PurchaseNo")
    private int purchaseNo;
    @Basic(optional = false)
    @Column(name = "ProductCode")
    private int productCode;

    public PurchaseDetailsPK()
    {
    }

    public PurchaseDetailsPK(int purchaseNo, int productCode)
    {
        this.purchaseNo = purchaseNo;
        this.productCode = productCode;
    }

    public int getPurchaseNo()
    {
        return purchaseNo;
    }

    public void setPurchaseNo(int purchaseNo)
    {
        this.purchaseNo = purchaseNo;
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
        hash += (int) purchaseNo;
        hash += (int) productCode;
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PurchaseDetailsPK))
        {
            return false;
        }
        PurchaseDetailsPK other = (PurchaseDetailsPK) object;
        if (this.purchaseNo != other.purchaseNo)
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
        return "com.packtpub.beans.PurchaseDetailsPK[purchaseNo=" + purchaseNo + ", productCode=" + productCode + "]";
    }

}
