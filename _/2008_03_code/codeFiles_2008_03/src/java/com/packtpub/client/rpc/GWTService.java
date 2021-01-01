/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.packtpub.client.rpc;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.packtpub.client.dto.BranchDTO;
import com.packtpub.client.dto.UsersDTO;
import java.util.HashMap;

/**
 *
 * @author Shamsuddin
 */
@RemoteServiceRelativePath("rpc/gwtservice")
public interface GWTService extends RemoteService {
    public String myMethod(String s);

    public boolean addBranch(BranchDTO branchDTO);
    public BranchDTO findBranch(int branchId);
    public boolean updateBranch(BranchDTO branchDTO);
    public boolean deleteBranch(int branchId);
    public String getHtmlReport(String fileName,HashMap<String,Integer> param);
    public String getPdfReport(String fileName,HashMap<String,Integer> param);
    public UsersDTO login(String username,String password);

}
