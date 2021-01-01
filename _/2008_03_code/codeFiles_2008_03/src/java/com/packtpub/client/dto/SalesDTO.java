/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.packtpub.client.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Shamsuddin
 */

public class SalesDTO implements Serializable {
    private Integer salesNo;
    private Date salesDate;
    private SalesDeskDTO salesDeskDTO;
    private EmployeeDTO employeeDTO;
    private CustomerDTO customerDTO;
    private List<SalesDetailsDTO> salesDetailsDTOList;

    public SalesDTO()
    {
    }

    public SalesDTO(Integer salesNo)
    {
        this.salesNo = salesNo;
    }

    public SalesDTO(Integer salesNo, Date salesDate)
    {
        this.salesNo = salesNo;
        this.salesDate = salesDate;
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

    public SalesDeskDTO getSalesDesk()
    {
        return salesDeskDTO;
    }

    public void setSalesDesk(SalesDeskDTO salesDesk)
    {
        this.salesDeskDTO = salesDesk;
    }

    public EmployeeDTO getEmployee()
    {
        return employeeDTO;
    }

    public void setEmployee(EmployeeDTO employee)
    {
        this.employeeDTO = employee;
    }

    public CustomerDTO getCustomer()
    {
        return customerDTO;
    }

    public void setCustomer(CustomerDTO customer)
    {
        this.customerDTO = customer;
    }

    public List<SalesDetailsDTO> getSalesDetailsDTOList()
    {
        return salesDetailsDTOList;
    }

    public void setSalesDetailsDTOList(List<SalesDetailsDTO> salesDetailsList)
    {
        this.salesDetailsDTOList = salesDetailsList;
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
        if (!(object instanceof SalesDTO))
        {
            return false;
        }
        SalesDTO other = (SalesDTO) object;
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
