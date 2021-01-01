/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.packtpub.beans;

import com.packtpub.client.dto.SalesDTO;
import com.packtpub.client.dto.SalesDetailsDTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Shamsuddin
 */
@Entity
@Table(name = "sales")
@NamedQueries(
{
    @NamedQuery(name = "Sales.findAll", query = "SELECT s FROM Sales s"),
    @NamedQuery(name = "Sales.findBySalesNo", query = "SELECT s FROM Sales s WHERE s.salesNo = :salesNo"),
    @NamedQuery(name = "Sales.findBySalesDate", query = "SELECT s FROM Sales s WHERE s.salesDate = :salesDate")
})
public class Sales implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "SalesNo")
    private Integer salesNo;
    @Basic(optional = false)
    @Column(name = "SalesDate")
    @Temporal(TemporalType.DATE)
    private Date salesDate;
    @JoinColumn(name = "SalesDeskId", referencedColumnName = "SalesDeskId")
    @ManyToOne(optional = false)
    private SalesDesk salesDesk;
    @JoinColumn(name = "EmployeeId", referencedColumnName = "EmployeeId")
    @ManyToOne(optional = false)
    private Employee employee;
    @JoinColumn(name = "CustomerNo", referencedColumnName = "CustomerNo")
    @ManyToOne
    private Customer customer;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sales")
    private List<SalesDetails> salesDetailsList;

    public Sales()
    {
    }

    public Sales(Integer salesNo)
    {
        this.salesNo = salesNo;
    }

    public Sales(Integer salesNo, Date salesDate)
    {
        this.salesNo = salesNo;
        this.salesDate = salesDate;
    }

    public Sales(SalesDTO salesDTO)
    {
        setSalesNo(salesDTO.getSalesNo());
        setSalesDate(salesDTO.getSalesDate());
        setCustomer(new Customer(salesDTO.getCustomer()));
        setSalesDesk(new SalesDesk(salesDTO.getSalesDesk()));
        setEmployee(new Employee(salesDTO.getEmployee()));

        List<SalesDetailsDTO> salesDetailsDTOList = salesDTO.getSalesDetailsDTOList();
        salesDetailsList = new ArrayList<SalesDetails>();
        for(int i=0; i<salesDetailsDTOList.size(); i++)
        {
            SalesDetailsDTO salesDetailsDTO=salesDetailsDTOList.get(i);
            SalesDetails salesDetails=new SalesDetails(salesDetailsDTO);
            salesDetailsList.add(salesDetails);
        }

    }

    public Integer getSalesNo()
    {
        return salesNo;
    }

    public void setSalesNo(Integer salesNo)
    {
        this.salesNo = salesNo;
    }

    public Date getSalesDate()
    {
        return salesDate;
    }

    public void setSalesDate(Date salesDate)
    {
        this.salesDate = salesDate;
    }

    public SalesDesk getSalesDesk()
    {
        return salesDesk;
    }

    public void setSalesDesk(SalesDesk salesDesk)
    {
        this.salesDesk = salesDesk;
    }

    public Employee getEmployee()
    {
        return employee;
    }

    public void setEmployee(Employee employee)
    {
        this.employee = employee;
    }

    public Customer getCustomer()
    {
        return customer;
    }

    public void setCustomer(Customer customer)
    {
        this.customer = customer;
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
        hash += (salesNo != null ? salesNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sales))
        {
            return false;
        }
        Sales other = (Sales) object;
        if ((this.salesNo == null && other.salesNo != null) || (this.salesNo != null && !this.salesNo.equals(other.salesNo)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "com.packtpub.beans.Sales[salesNo=" + salesNo + "]";
    }

}
