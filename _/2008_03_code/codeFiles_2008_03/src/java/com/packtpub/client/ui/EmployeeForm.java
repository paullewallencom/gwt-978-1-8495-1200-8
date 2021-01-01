/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.packtpub.client.ui;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.DatePickerEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.Radio;
import com.extjs.gxt.ui.client.widget.form.RadioGroup;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.ColumnData;
import com.extjs.gxt.ui.client.widget.layout.ColumnLayout;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import java.util.Date;

/**
 *
 * @author Shamsuddin
 */
public class EmployeeForm extends FormPanel
{

    TextField<String> employeeIdField = new TextField<String>();
    TextField<String> nameField = new TextField<String>();
    TextField<String> mobileField = new TextField<String>();
    TextField<String> emailField = new TextField<String>();

    DateField dateOfBirthField = new DateField();
    DateField joinDateField = new DateField();

    Radio maleRadio = new Radio();
    Radio femaleRadio = new Radio();
    Radio fullTimeRadio = new Radio();
    Radio partTimeRadio = new Radio();

    SimpleComboBox departmentCombo = new SimpleComboBox();
    SimpleComboBox designationCombo = new SimpleComboBox();

    TextArea addressField = new TextArea();

    Button findButton=new Button("Find");
    Button saveButton=new Button("Save");
    Button updateButton=new Button("Update");
    Button deleteButton=new Button("Delete");
    Button clearButton=new Button("Clear");

    //CheckBox checkBox=new CheckBox();

    public EmployeeForm()
    {
        createForm();

        employeeIdField.addListener(Events.OnBlur,new Listener<FieldEvent>(){

            @Override
            public void handleEvent(FieldEvent be)
            {
                int value=Integer.parseInt(employeeIdField.getRawValue());

                addressField.setValue(""+(value*value));
            }

        });

      

        clearButton.addListener(Events.OnClick,new Listener<ButtonEvent>() {

            @Override
            public void handleEvent(ButtonEvent be)
            {
                employeeIdField.setValue(null);
                nameField.setValue(null);
                mobileField.setValue(null);
                emailField.setValue(null);
                dateOfBirthField.setValue(null);
                joinDateField.setValue(null);
                maleRadio.setValue(true);
                fullTimeRadio.setValue(true);
                departmentCombo.setSimpleValue("Sales");
                designationCombo.setSimpleValue("Manager");
                addressField.setValue(null);
            }
        });

        departmentCombo.addListener(Events.SelectionChange,new Listener<BaseEvent>(){

            @Override
            public void handleEvent(BaseEvent be)
            {
                String department=departmentCombo.getSimpleValue().toString();

                designationCombo.removeAll();

                designationCombo.add("Manager");
                designationCombo.add("Officer");

                if(department.equalsIgnoreCase("Accounts"))
                {
                    designationCombo.add("Clerk");
                }
                else if(department.equalsIgnoreCase("Sales"))
                {
                    designationCombo.add("Salesman");
                }

                
            }

        });



        dateOfBirthField.getDatePicker().addListener(Events.Select,new Listener<DatePickerEvent>()
        {

            @Override
            public void handleEvent(DatePickerEvent be)
            {
                

                Date today=new Date();
                Date dob=dateOfBirthField.getValue();

                long difference=today.getTime()-dob.getTime();

                emailField.setValue(""+difference);
                long second=difference/1000;
                long minute=second/60;
                long hour=minute/60;
                long day=hour/24;

                long month=day/30;
                day=day%30;

                long year=month/12;
                month=month%12;

                MessageBox messageBox=new MessageBox();
                messageBox.setMessage("Age is "+year+" year "+month+" month "+day+" day");
                messageBox.show();
            }


        }) ;
        
        nameField.addListener(Events.Focus,new Listener<FieldEvent>()
        {

            @Override
            public void handleEvent(FieldEvent be)
            {
                int employeeId=Integer.parseInt(employeeIdField.getValue());
                
                if(employeeId==1)
                    nameField.setValue("Saiful Islam");
                else if(employeeId==2)
                    nameField.setValue("Jashim Uddin");
                
            }

            
        });

           

        /*nameField.addKeyListener(new KeyListener(){

            @Override
            public void componentKeyUp(ComponentEvent event)
            {
                //super.componentKeyDown(event);
                try
                {
                    double number=Double.parseDouble(nameField.getValue());

                    addressField.setValue(String.valueOf(Math.ceil(number)));
                }
                catch(NumberFormatException nfe)
                {
                    addressField.setValue("Wrong Value");
                }
            }

        });*/

        nameField.addListener(Events.KeyUp,new Listener<FieldEvent>(){

            @Override
            public void handleEvent(FieldEvent be)
            {
                   try
                {
                    double number=Double.parseDouble(nameField.getValue());

                    addressField.setValue(String.valueOf(Math.ceil(number)));
                }
                catch(NumberFormatException nfe)
                {
                    addressField.setValue("Wrong Value");
                }

            }

        });

/*        checkBox.addListener(Events.Change,new Listener<BaseEvent>()
        {

            @Override
            public void handleEvent(BaseEvent be)
            {
                if(checkBox.getValue())
                    emailField.enable();
                else
                    emailField.disable();
            }



        });*/
        
    }

    private void createForm()
    {

        setFrame(true);
        setHeading("Employee");
        setSize(660, 480);
        setLabelAlign(LabelAlign.TOP);
        setButtonAlign(HorizontalAlignment.CENTER);

        LayoutContainer main = new LayoutContainer();
        main.setLayout(new ColumnLayout());

        LayoutContainer left = new LayoutContainer();
        left.setStyleAttribute("paddingRight", "10px");
        FormLayout layout = new FormLayout();
        layout.setLabelAlign(LabelAlign.TOP);
        left.setLayout(layout);

        FormData formData = new FormData("100%");

        employeeIdField.setFieldLabel("Employee ID");
        left.add(employeeIdField, formData);

        mobileField.setFieldLabel("Mobile");
        left.add(mobileField, formData);

        //checkBox.setBoxLabel("Other");
        //left.add(checkBox);

        dateOfBirthField.setFieldLabel("Date of Birth");
        dateOfBirthField.setMinValue(new Date(80,1,1));
        dateOfBirthField.setMaxValue(new Date());
        left.add(dateOfBirthField, formData);

        maleRadio.setBoxLabel("Male");
        femaleRadio.setBoxLabel("Female");

        RadioGroup genderGroup = new RadioGroup();
        genderGroup.setFieldLabel("Gender");
        genderGroup.add(maleRadio);
        genderGroup.add(femaleRadio);
        left.add(genderGroup, formData);

        departmentCombo.setFieldLabel("Department");
        departmentCombo.add("Sales");
        departmentCombo.add("Purchase");
        departmentCombo.add("Accounts");
        departmentCombo.add("Customer Service");

        left.add(departmentCombo, formData);

        LayoutContainer right = new LayoutContainer();
        right.setStyleAttribute("paddingLeft", "10px");
        layout = new FormLayout();
        layout.setLabelAlign(LabelAlign.TOP);
        right.setLayout(layout);

        nameField.setAllowBlank(false);
        nameField.setEmptyText("Enter Employee's Full Name");
        nameField.setFieldLabel("Full Name");
        nameField.setSelectOnFocus(true);
        right.add(nameField, formData);

        emailField.setFieldLabel("Email");
        right.add(emailField, formData);

        joinDateField.setFieldLabel("Join Date");
        right.add(joinDateField, formData);

        fullTimeRadio.setBoxLabel("Full Time");
        partTimeRadio.setBoxLabel("Part Time");

        RadioGroup jobTypeGroup = new RadioGroup();
        jobTypeGroup.setFieldLabel("Job Type");
        jobTypeGroup.add(fullTimeRadio);
        jobTypeGroup.add(partTimeRadio);
        right.add(jobTypeGroup, formData);

        designationCombo.setFieldLabel("Designation");
        designationCombo.add("Manager");
        designationCombo.add("Officer");
        designationCombo.add("Salesman");
        designationCombo.add("Clerk");

        right.add(designationCombo, formData);

        main.add(left, new ColumnData(.5));
        main.add(right, new ColumnData(.5));

        add(main, new FormData("100%"));

        addressField.setFieldLabel("Address");
        addressField.setHeight(150);
        add(addressField, new FormData("100%"));

        addButton(findButton);
        addButton(saveButton);
        addButton(updateButton);
        addButton(deleteButton);
        addButton(clearButton);

    }
}
