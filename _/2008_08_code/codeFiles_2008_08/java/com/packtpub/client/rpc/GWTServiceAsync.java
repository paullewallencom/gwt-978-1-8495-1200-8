/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.packtpub.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.packtpub.client.dto.BranchDTO;
import java.util.HashMap;

/**
 *
 * @author Shamsuddin
 */
public interface GWTServiceAsync {
    public void myMethod(String s, AsyncCallback<String> callback);

    public void addBranch(BranchDTO branchDTO, AsyncCallback<java.lang.Boolean> asyncCallback);

    public void findBranch(int branchId, AsyncCallback<BranchDTO> asyncCallback);

    public void updateBranch(BranchDTO branchDTO, AsyncCallback<java.lang.Boolean> asyncCallback);

    public void deleteBranch(int branchId, AsyncCallback<java.lang.Boolean> asyncCallback);

    public void getHtmlReport(String fileName, HashMap<String,Integer> param, AsyncCallback<String> asyncCallback);

    public void getPdfReport(String fileName, HashMap<String,Integer> param, AsyncCallback<String> asyncCallback);



}
