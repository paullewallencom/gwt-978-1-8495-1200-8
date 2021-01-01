/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.packtpub.client.dto;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Shamsuddin
 */

public class CustomerDTO implements Serializable {
    
    private Integer customerNo;
    private String name;
    private String address;
    private String contactNo;
    private List<SalesDTO> salesDTOList;

    public CustomerDTO()
    {
    }

    public CustomerDTO(Integer customerNo)
    {
        this.customerNo = customerNo;
    }

    public CustomerDTO(Integer customerNo, String name)
    {
        this.customerNo = customerNo;
        this.name = name;
    }

    public Integer getCustomerNo()
    {
        return customerNo;
    }

    public void setCustomerNo(Integer customerNo)
    {
        this.customerNo = customerNo;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getContactNo()
    {
        return contactNo;
    }

    public void setContactNo(String contactNo)
    {
        this.contactNo = contactNo;
    }

    public List<SalesDTO> getSalesList()
    {
        return salesDTOList;
    }

    public void setSalesList(List<SalesDTO> salesList)
    {
        this.salesDTOList = salesList;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (customerNo != null ? customerNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CustomerDTO))
        {
            return false;
        }
        CustomerDTO other = (CustomerDTO) object;
        if ((this.customerNo == null && other.customerNo != null) || (this.customerNo != null && !this.customerNo.equals(other.customerNo)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "com.packtpub.beans.Customer[customerNo=" + customerNo + "]";
    }

}
