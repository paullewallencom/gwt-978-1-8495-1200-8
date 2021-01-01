/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.packtpub.client.dto;

import com.packtpub.beans.*;
import java.io.Serializable;

/**
 *
 * @author Shamsuddin
 */

public class UsersDTO implements Serializable {
    private String userName;
    private String password;
    private EmployeeDTO employeeDTO;

    public UsersDTO()
    {
    }

    public UsersDTO(String userName)
    {
        this.userName = userName;
    }

    public UsersDTO(String userName, String password)
    {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public EmployeeDTO getEmployeeDTO()
    {
        return employeeDTO;
    }

    public void setEmployeeDTO(EmployeeDTO employee)
    {
        this.employeeDTO = employee;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (userName != null ? userName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsersDTO))
        {
            return false;
        }
        UsersDTO other = (UsersDTO) object;
        if ((this.userName == null && other.userName != null) || (this.userName != null && !this.userName.equals(other.userName)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "com.packtpub.beans.Users[userName=" + userName + "]";
    }

}
