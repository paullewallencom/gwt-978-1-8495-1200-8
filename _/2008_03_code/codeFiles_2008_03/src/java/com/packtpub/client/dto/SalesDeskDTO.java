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

public class SalesDeskDTO implements Serializable {
    private Integer salesDeskId;
    private List<SalesDTO> salesDTOList;
    private BranchDTO branchDTO;

    public SalesDeskDTO()
    {
    }

    public SalesDeskDTO(Integer salesDeskId)
    {
        this.salesDeskId = salesDeskId;
    }

    public Integer getSalesDeskId()
    {
        return salesDeskId;
    }

    public void setSalesDeskId(Integer salesDeskId)
    {
        this.salesDeskId = salesDeskId;
    }

    public List<SalesDTO> getSalesList()
    {
        return salesDTOList;
    }

    public void setSalesList(List<SalesDTO> salesList)
    {
        this.salesDTOList = salesList;
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
        hash += (salesDeskId != null ? salesDeskId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SalesDeskDTO))
        {
            return false;
        }
        SalesDeskDTO other = (SalesDeskDTO) object;
        if ((this.salesDeskId == null && other.salesDeskId != null) || (this.salesDeskId != null && !this.salesDeskId.equals(other.salesDeskId)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "com.packtpub.beans.SalesDesk[salesDeskId=" + salesDeskId + "]";
    }

}
