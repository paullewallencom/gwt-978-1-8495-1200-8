
package com.packtpub.client.ui;

/**
 *
 * @author Shamsuddin Ahammad
 */

import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MenuEvent;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.menu.MenuBar;
import com.extjs.gxt.ui.client.widget.menu.MenuBarItem;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import java.util.HashMap;

public class HomePage extends ContentPanel
{
    ContentPanel mainContentsPanel = new ContentPanel();

    public HomePage()
    {

        setSize(980,630);
        setHeaderVisible(false);
  
        BorderLayout layout = new BorderLayout();
        setLayout(layout);
        
        BorderLayoutData menuBarToolBarLayoutData = new BorderLayoutData(LayoutRegion.NORTH, 55);
        menuBarToolBarLayoutData.setMargins(new Margins(5));

        BorderLayoutData leftSidebarLayoutData = new BorderLayoutData(LayoutRegion.WEST, 150);
        leftSidebarLayoutData.setSplit(true);
        leftSidebarLayoutData.setCollapsible(true);
        leftSidebarLayoutData.setMargins(new Margins(0, 5, 0, 5));

        BorderLayoutData mainContentsLayoutData = new BorderLayoutData(LayoutRegion.CENTER);
        mainContentsLayoutData.setMargins(new Margins(0));

        BorderLayoutData rightSidebarLayoutData = new BorderLayoutData(LayoutRegion.EAST, 150);
        rightSidebarLayoutData.setSplit(true);
        rightSidebarLayoutData.setCollapsible(true);
        rightSidebarLayoutData.setMargins(new Margins(0, 5, 0, 5));

        BorderLayoutData footerLayoutData = new BorderLayoutData(LayoutRegion.SOUTH, 20);
        footerLayoutData.setMargins(new Margins(5));

        
        setTopComponent(getBanner());

        VerticalPanel menuAndToolBarPanel=new VerticalPanel();
        menuAndToolBarPanel.add(getMenuBar());
        menuAndToolBarPanel.add(getToolBar());

        add(menuAndToolBarPanel, menuBarToolBarLayoutData);
        add(getLeftSideBar(), leftSidebarLayoutData);

        mainContentsPanel.setLayout(new FitLayout());
        add(mainContentsPanel, mainContentsLayoutData);
        
        add(getRightSidebar(), rightSidebarLayoutData);
        add(getFooter(), footerLayoutData);

        
        
    }

    public ContentPanel getBanner()
    {
        ContentPanel bannerPanel = new ContentPanel();
        bannerPanel.setHeaderVisible(false);
        bannerPanel.add(new Image("resources/images/banner.png"));
        
        return bannerPanel;
    }

    public ContentPanel getLeftSideBar()
    {
        ContentPanel leftSidebarPanel = new ContentPanel();
        leftSidebarPanel.setHeading("Left Sidebar");
        return leftSidebarPanel;

    }

    public ContentPanel getRightSidebar()
    {
        ContentPanel rightSidebarPanel = new ContentPanel();
        rightSidebarPanel.setHeading("Right Sidebar");
        return rightSidebarPanel;
    }

    public VerticalPanel getFooter()
    {

        VerticalPanel footerPanel = new VerticalPanel();
        footerPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        Label label = new Label("Design by Shamsuddin Ahammad. Copyright © Packt Publishing.");
        footerPanel.add(label);

        return footerPanel;
    }

    
    public MenuBar getMenuBar()
    {
       MenuBar menuBar=new MenuBar();

        //Menus

        Menu fileMenu=new Menu();
        Menu reportsMenu=new Menu();
        Menu helpMenu=new Menu();

        //Items for File menu


        MenuItem employeeMenuItem=new MenuItem("Employee");
        fileMenu.add(employeeMenuItem);
        employeeMenuItem.addListener(Events.Select,new Listener<MenuEvent>()
        {

            @Override
            public void handleEvent(MenuEvent me)
            {

                EmployeeForm employeeForm=new EmployeeForm();
                mainContentsPanel.setHeading("Form");
                mainContentsPanel.add(employeeForm);
                mainContentsPanel.layout();


            }

        });

        MenuItem branchMenuItem=new MenuItem("Branch");
        fileMenu.add(branchMenuItem);
        branchMenuItem.addListener(Events.Select,new Listener<MenuEvent>()
        {

            @Override
            public void handleEvent(MenuEvent be)
            {
               
               BranchForm branchForm=new BranchForm();
                mainContentsPanel.setHeading("Form");
                mainContentsPanel.add(branchForm);
                mainContentsPanel.layout();


            }

        });

        MenuItem productMenuItem=new MenuItem("Product");
        fileMenu.add(productMenuItem);

        MenuItem stockMenuItem=new MenuItem("Stock");
        fileMenu.add(stockMenuItem);

        MenuItem purchaseMenuItem=new MenuItem("Purchase");
        fileMenu.add(purchaseMenuItem);

        MenuItem salesMenuItem=new MenuItem("Sales");
        fileMenu.add(salesMenuItem);

        //Items for Reports menu
        
        MenuItem productListMenuItem=new MenuItem("Product List");
        reportsMenu.add(productListMenuItem);

        MenuItem stockStatusMenuItem=new MenuItem("Stock Status");
        reportsMenu.add(stockStatusMenuItem);

        MenuItem purchaseDetailMenuItem=new MenuItem("Purchase Detail");
        reportsMenu.add(purchaseDetailMenuItem);

        MenuItem salesDetailMenuItem=new MenuItem("Sales Detail");
        reportsMenu.add(salesDetailMenuItem);
        salesDetailMenuItem.addListener(Events.Select,new Listener<MenuEvent>()
        {

            @Override
            public void handleEvent(MenuEvent be)
            {
                MessageBox inputBox = MessageBox.prompt("Input", "Enter the Sales No");
                inputBox.addCallback(new Listener<MessageBoxEvent>()
                {

                    public void handleEvent(MessageBoxEvent be)
                    {
                        int salesNo = Integer.parseInt(be.getValue());
                        HashMap<String, Integer> param = new HashMap<String, Integer>();
                        param.put("salesNo", salesNo);
                        PdfReportViewer reportViewer = new PdfReportViewer("reports/Sales", param, "Sales Invoice");
                        mainContentsPanel.setHeading("Reports");
                        mainContentsPanel.add(reportViewer);
                        mainContentsPanel.layout();


                    }
                });
                


            }

        });
        //Items for Help menu

        MenuItem aboutMenuItem=new MenuItem("About");
        helpMenu.add(aboutMenuItem);

        MenuBarItem menuBarItemFile=new MenuBarItem("File",fileMenu);
        MenuBarItem menuBarItemReports=new MenuBarItem("Reports",reportsMenu);
        MenuBarItem menuBarItemHelp=new MenuBarItem("Help",helpMenu);

        menuBar.add(menuBarItemFile);
        menuBar.add(menuBarItemReports);
        menuBar.add(menuBarItemHelp);

        return menuBar;

    }


    public ToolBar getToolBar()
    {
        ToolBar toolBar=new ToolBar();

        toolBar.add(new Button("Stock"));
        toolBar.add(new Button("Sales"));
        toolBar.add(new Button("Purchase"));

        return toolBar;
    }
}
