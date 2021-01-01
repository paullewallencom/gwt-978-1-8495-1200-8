/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.packtpub.client.ui;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.packtpub.client.dto.BranchDTO;
import com.packtpub.client.rpc.GWTService;
import com.packtpub.client.rpc.GWTServiceAsync;

/**
 *
 * @author Shamsuddin
 */
public class BranchForm extends FormPanel
{

    TextField<String> branchIdField = new TextField<String>();
    TextField<String> nameField = new TextField<String>();
    TextField<String> locationField = new TextField<String>();
    Button findButton = new Button("Find");
    Button saveButton = new Button("Save");
    Button updateButton = new Button("Update");
    Button deleteButton = new Button("Delete");
    Button clearButton = new Button("Clear");

    BranchDTO branchDTO;

    public BranchForm()
    {
        setHeading("Branch");
        setFrame(true);
        setSize(380, 200);

        createForm();

        final AsyncCallback<Boolean> callback = new AsyncCallback<Boolean>()
        {

            MessageBox messageBox = new MessageBox();

            @Override
            public void onFailure(Throwable caught)
            {

                messageBox.setMessage("An error occured! Cannot complete the operation");
                messageBox.show();
            }

            @Override
            public void onSuccess(Boolean result)
            {
                if (result)
                {
                    messageBox.setMessage("Operation completed successfully");

                } else
                {
                    messageBox.setMessage("An error occured! Cannot complete compltet the operation");
                }

                messageBox.show();
            }
        };

        saveButton.addSelectionListener(new SelectionListener<ButtonEvent>() {

            @Override
            public void componentSelected(ButtonEvent ce)
            {
                branchDTO=new BranchDTO();
                branchDTO.setBranchId(Integer.parseInt(branchIdField.getValue()));
                branchDTO.setName(nameField.getValue());
                branchDTO.setLocation(locationField.getValue());

                ((GWTServiceAsync)GWT.create(GWTService.class)).addBranch(branchDTO, callback);
            }
        });

        final AsyncCallback<BranchDTO> callbackFind = new AsyncCallback<BranchDTO>()
        {

            @Override
            public void onFailure(Throwable caught)
            {
                MessageBox messageBox = new MessageBox();
                messageBox.setMessage("An error occured! Cannot complete the operation");
                messageBox.show();
                clear();
            }

            @Override
            public void onSuccess(BranchDTO result)
            {

                branchDTO=result;

                if(result!=null)
                {
                    branchIdField.setValue(""+branchDTO.getBranchId());
                    nameField.setValue(branchDTO.getName());
                    locationField.setValue(branchDTO.getLocation());
                }
                 else
                 {
                    MessageBox messageBox = new MessageBox();
                    messageBox.setMessage("No such Branch found");
                    messageBox.show();
                    clear();

                 }
            }

        };

        findButton.addSelectionListener(new SelectionListener<ButtonEvent>()
        {

            @Override
            public void componentSelected(ButtonEvent ce)
            {
                MessageBox inputBox = MessageBox.prompt("Input", "Enter the Branch ID");
                inputBox.addCallback(new Listener<MessageBoxEvent>()
                {

                    public void handleEvent(MessageBoxEvent be)
                    {
                        int branchId = Integer.parseInt(be.getValue());
                        ((GWTServiceAsync)GWT.create(GWTService.class)).findBranch(branchId,callbackFind);
                    }
                });
               
            }
        });

        updateButton.addSelectionListener(new SelectionListener<ButtonEvent>()
        {

            @Override
            public void componentSelected(ButtonEvent ce)
            {
                branchDTO.setName(nameField.getValue());
                branchDTO.setLocation(locationField.getValue());
                
                ((GWTServiceAsync)GWT.create(GWTService.class)).updateBranch(branchDTO,callback);
                clear();
            }
        });

        deleteButton.addSelectionListener(new SelectionListener<ButtonEvent>()
        {

            @Override
            public void componentSelected(ButtonEvent ce)
            {

                ((GWTServiceAsync)GWT.create(GWTService.class)).deleteBranch(branchDTO.getBranchId(),callback);
                clear();
            }
        });


    }

    private void createForm()
    {
        branchIdField.setFieldLabel("Branch ID");
        branchIdField.setEmptyText("Enter the branch ID");
        branchIdField.setAllowBlank(false);

        nameField.setFieldLabel("Name");
        nameField.setEmptyText("Enter the branch name");
        nameField.setAllowBlank(false);

        locationField.setFieldLabel("Location");
        locationField.setEmptyText("Enter the branch location");
        locationField.setAllowBlank(true);

        add(branchIdField);
        add(nameField);
        add(locationField);

        addButton(findButton);
        addButton(saveButton);
        addButton(updateButton);
        addButton(deleteButton);
        addButton(clearButton);
    }
}
