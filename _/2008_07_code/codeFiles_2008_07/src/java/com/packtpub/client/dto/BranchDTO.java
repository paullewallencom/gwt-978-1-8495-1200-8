/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.packtpub.client.dto;

import java.io.Serializable;

/**
 *
 * @author Shamsuddin
 */
public class BranchDTO implements Serializable{

  private Integer branchId;
  private String name;
  private String location;

    public BranchDTO(Integer branchId, String name, String location)
    {
        this.branchId = branchId;
        this.name = name;
        this.location = location;
    }

    public BranchDTO(Integer branchId, String name)
    {
        this.branchId = branchId;
        this.name = name;
    }

    public BranchDTO(Integer branchId)
    {
        this.branchId = branchId;
    }

    public BranchDTO()
    {
    }

    public Integer getBranchId()
    {
        return branchId;
    }

    public void setBranchId(Integer branchId)
    {
        this.branchId = branchId;
    }

    public String getLocation()
    {
        return location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }


}
