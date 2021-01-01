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

public class EmployeeDTO implements Serializable {
    private Integer employeeId;
    private String name;
    private String designation;
    private String gender;
    private String mobile;
    private List<Users> usersList;
    private List<Sales> salesList;
    private Branch branch;

    public EmployeeDTO()
    {
    }

    public EmployeeDTO(Integer employeeId)
    {
        this.employeeId = employeeId;
    }

    public EmployeeDTO(Integer employeeId, String name, String designation, String gender, String mobile)
    {
        this.employeeId = employeeId;
        this.name = name;
        this.designation = designation;
        this.gender = gender;
        this.mobile = mobile;
    }

    public Integer getEmployeeId()
    {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId)
    {
        this.employeeId = employeeId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDesignation()
    {
        return designation;
    }

    public void setDesignation(String designation)
    {
        this.designation = designation;
    }

    public String getGender()
    {
        return gender;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public String getMobile()
    {
        return mobile;
    }

    public void setMobile(String mobile)
    {
        this.mobile = mobile;
    }

    public List<Users> getUsersList()
    {
        return usersList;
    }

    public void setUsersList(List<Users> usersList)
    {
        this.usersList = usersList;
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
        hash += (employeeId != null ? employeeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmployeeDTO))
        {
            return false;
        }
        EmployeeDTO other = (EmployeeDTO) object;
        if ((this.employeeId == null && other.employeeId != null) || (this.employeeId != null && !this.employeeId.equals(other.employeeId)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "com.packtpub.beans.Employee[employeeId=" + employeeId + "]";
    }

}
