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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Shamsuddin
 */
@Entity
@Table(name = "sales_desk")
@NamedQueries(
{
    @NamedQuery(name = "SalesDesk.findAll", query = "SELECT s FROM SalesDesk s"),
    @NamedQuery(name = "SalesDesk.findBySalesDeskId", query = "SELECT s FROM SalesDesk s WHERE s.salesDeskId = :salesDeskId")
})
public class SalesDesk implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "SalesDeskId")
    private Integer salesDeskId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "salesDesk")
    private List<Sales> salesList;
    @JoinColumn(name = "BranchId", referencedColumnName = "BranchId")
    @ManyToOne(optional = false)
    private Branch branch;

    public SalesDesk()
    {
    }

    public SalesDesk(Integer salesDeskId)
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

    public List<Sales> getSalesList()
    {
        return salesList;
    }

    public void setSalesList(List<Sales> salesList)
    {
        this.salesList = salesList;
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
        hash += (salesDeskId != null ? salesDeskId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SalesDesk))
        {
            return false;
        }
        SalesDesk other = (SalesDesk) object;
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
