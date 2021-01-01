/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.packtpub.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Main entry point.
 *
 * @author user
 */
public class MainEntryPoint implements EntryPoint
{

   
  
    /** 
     * The entry point method, called automatically by loading a module
     * that declares an implementing class as an entry-point
     */
    public void onModuleLoad()
    {
        HomePage homePage=new HomePage();
        RootPanel.get().add(homePage);

    }
}
