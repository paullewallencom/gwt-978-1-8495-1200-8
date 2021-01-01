/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.packtpub.beans;

import com.packtpub.client.dto.CustomerDTO;
import com.packtpub.client.dto.SalesDTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Shamsuddin
 */
@Entity
@Table(name = "customer")
@NamedQueries(
{
    @NamedQuery(name = "Customer.findAll", query = "SELECT c FROM Customer c"),
    @NamedQuery(name = "Customer.findByCustomerNo", query = "SELECT c FROM Customer c WHERE c.customerNo = :customerNo"),
    @NamedQuery(name = "Customer.findByName", query = "SELECT c FROM Customer c WHERE c.name = :name"),
    @NamedQuery(name = "Customer.findByAddress", query = "SELECT c FROM Customer c WHERE c.address = :address"),
    @NamedQuery(name = "Customer.findByContactNo", query = "SELECT c FROM Customer c WHERE c.contactNo = :contactNo")
})
public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CustomerNo")
    private Integer customerNo;
    @Basic(optional = false)
    @Column(name = "Name")
    private String name;
    @Column(name = "Address")
    private String address;
    @Column(name = "ContactNo")
    private String contactNo;
    @OneToMany(mappedBy = "customer")
    private List<Sales> salesList;

    public Customer()
    {
    }

    public Customer(CustomerDTO customerDTO)
    {
        setCustomerNo(customerDTO.getCustomerNo());
        setName(customerDTO.getName());
        setAddress(customerDTO.getAddress());
        setContactNo(customerDTO.getContactNo());

        List<SalesDTO> salesDTOList=customerDTO.getSalesList();
        salesList = new ArrayList<Sales>();
        for(int i=0;i<salesDTOList.size();i++)
        {
            SalesDTO salesDTO=salesDTOList.get(i);
            Sales sales=new Sales(salesDTO);
            salesList.add(sales);

        }
    }

    public Customer(Integer customerNo)
    {
        this.customerNo = customerNo;
    }

    public Customer(Integer customerNo, String name)
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

    public List<Sales> getSalesList()
    {
        return salesList;
    }

    public void setSalesList(List<Sales> salesList)
    {
        this.salesList = salesList;
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
        if (!(object instanceof Customer))
        {
            return false;
        }
        Customer other = (Customer) object;
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
