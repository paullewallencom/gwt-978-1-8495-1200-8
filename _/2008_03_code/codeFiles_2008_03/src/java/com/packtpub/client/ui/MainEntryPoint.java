/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.packtpub.client.ui;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.packtpub.client.dto.UsersDTO;
import com.packtpub.client.rpc.GWTService;
import com.packtpub.client.rpc.GWTServiceAsync;

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

    UsersDTO usersDTO;

    public void onModuleLoad()
    {
       //login();
         HomePage homePage=new HomePage();
         RootPanel.get().add(homePage);
    }

    public void login()
    {
        final FormPanel formPanel = new FormPanel();
        final TextField<String> txtUsername = new TextField<String>();
        final TextField<String> txtPassword = new TextField<String>();
        final Button btnLogin = new Button("Login");

        formPanel.setBodyBorder(false);
        formPanel.setWidth(380);
        formPanel.setLabelWidth(100);
        formPanel.setButtonAlign(HorizontalAlignment.CENTER);
        
        txtUsername.setFieldLabel("Username");
        txtPassword.setFieldLabel("Password");
        txtPassword.setPassword(true);
        
        txtUsername.setAllowBlank(false);
        formPanel.add(txtUsername);

        txtPassword.setPassword(true);
        txtPassword.setAllowBlank(false);
        formPanel.add(txtPassword);

        formPanel.addButton(btnLogin);

        ContentPanel topPanel = new ContentPanel();
        Image image = new Image("resources/images/login.gif", 0, 0, 400, 101);
        topPanel.setLayout(new FitLayout());
        topPanel.add(image);
        
        final Window window = new Window();
        
        window.setModal(true);
        window.setResizable(false);
        window.setClosable(false);
        window.setDraggable(false);
        window.setWidth(400);
        window.setHeight(260);
        window.setPlain(true);
        window.setLayout(new BorderLayout());
        window.add(topPanel, new BorderLayoutData(LayoutRegion.NORTH,100));
        
        window.add(formPanel, new BorderLayoutData(LayoutRegion.CENTER,300));
        window.show();

        final AsyncCallback<UsersDTO> usersAsyncCallback = new AsyncCallback<UsersDTO>()
        {

            @Override
            public void onFailure(Throwable caught)
            {
                MessageBox.info("Error","Cannot login!",null);
                btnLogin.enable();
            }

            @Override
            public void onSuccess(UsersDTO result)
            {
                
                if (result != null)
                {
                    usersDTO = result;
                    window.hide();
                    HomePage homePage=new HomePage();
                    RootPanel.get().add(homePage);
                    
                    
                }
                else
                {
                    MessageBox.info("Error","Wrong username or password",null);
                    btnLogin.enable();
                }
            }
        };

        btnLogin.addListener(Events.OnClick,new Listener<ButtonEvent>(){

            @Override
            public void handleEvent(ButtonEvent e)
            {
                
                btnLogin.disable();
                String username = txtUsername.getValue();
                String password = txtPassword.getValue();
                ((GWTServiceAsync)GWT.create(GWTService.class)).login(username, password, usersAsyncCallback);
            }

        });



    }
}
