
package com.packtpub.client.ui;

/**
 *
 * @author Shamsuddin Ahammad
 */

import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MenuEvent;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.layout.AccordionLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
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
    
    
    private TabPanel tabPanel = new TabPanel();

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
        add(getLeftSidebar(), leftSidebarLayoutData);

                
        tabPanel.setMinTabWidth(115);
        //tabPanel.setResizeTabs(true);
        //tabPanel.setAnimScroll(true);
        tabPanel.setTabScroll(true);
        tabPanel.setCloseContextMenu(true);

        add(tabPanel, mainContentsLayoutData);
        
        add(getRightSidebar(), rightSidebarLayoutData);
        add(getFooter(), footerLayoutData);

        
        
    }

    private void addTab(String text, ContentPanel contentPanel)
    {
        TabItem item = new TabItem();
        item.setText(text);
        item.setClosable(true);
        item.add(contentPanel);
        tabPanel.add(item);
        tabPanel.setSelection(item);
    }

    public ContentPanel getBanner()
    {
        ContentPanel bannerPanel = new ContentPanel();
        bannerPanel.setHeaderVisible(false);
        bannerPanel.add(new Image("resources/images/banner.png"));
        
        return bannerPanel;
    }

    public ContentPanel getLeftSidebar()
    {
        ContentPanel leftSidebarPanel = new ContentPanel();

        leftSidebarPanel.setHeading("Navigation");
        leftSidebarPanel.setBodyBorder(true);

        leftSidebarPanel.setLayout(new AccordionLayout());
        

        ContentPanel setupContentPanel=new ContentPanel();
        setupContentPanel.setHeading("Setup");
        setupContentPanel.setLayout(new RowLayout());

        Button branchButton=new Button("Branch", new SelectionListener<ButtonEvent>() {

            @Override
            public void componentSelected(ButtonEvent ce)
            {
                BranchForm branchForm = new BranchForm();
                addTab("Branch",branchForm);
            }
        });
        Button customerButton=new Button("Customer");
        Button employeeButton=new Button("Employee", new SelectionListener<ButtonEvent>() {

            @Override
            public void componentSelected(ButtonEvent ce)
            {
                EmployeeForm employeeForm = new EmployeeForm();
                addTab("Employee",employeeForm);
            }
        });
        Button productButton=new Button("Product");
        Button salesDeskButton=new Button("Sales Desk");
        Button supplierButton=new Button("Supplier");
        Button userButton=new Button("User");


        setupContentPanel.add(branchButton,new RowData(1,-1,new Margins(5,5,5,5)));
        setupContentPanel.add(customerButton,new RowData(1,-1,new Margins(5,5,5,5)));
        setupContentPanel.add(employeeButton,new RowData(1,-1,new Margins(5,5,5,5)));
        setupContentPanel.add(productButton,new RowData(1,-1,new Margins(5,5,5,5)));
        setupContentPanel.add(salesDeskButton,new RowData(1,-1,new Margins(5,5,5,5)));
        setupContentPanel.add(supplierButton,new RowData(1,-1,new Margins(5,5,5,5)));
        setupContentPanel.add(userButton,new RowData(1,-1,new Margins(5,5,5,5)));
        
        
        leftSidebarPanel.add(setupContentPanel);


        ContentPanel salesContentPanel=new ContentPanel();
        salesContentPanel.setHeading("Sales");
        salesContentPanel.setLayout(new RowLayout());

        Button newSalesButton=new Button("New Sales");
        Button listSalesButton=new Button("List Sales");

        salesContentPanel.add(newSalesButton,new RowData(1,-1,new Margins(5,5,5,5)));
        salesContentPanel.add(listSalesButton,new RowData(1,-1,new Margins(5,5,5,5)));

        leftSidebarPanel.add(salesContentPanel);

        ContentPanel purchaseContentPanel=new ContentPanel();
        purchaseContentPanel.setHeading("Purchase");
        purchaseContentPanel.setLayout(new RowLayout());

        Button newPurchaseButton=new Button("New Purchase");
        Button listPurchaseButton=new Button("List Purchase");

        purchaseContentPanel.add(newPurchaseButton,new RowData(1,-1,new Margins(5,5,5,5)));
        purchaseContentPanel.add(listPurchaseButton,new RowData(1,-1,new Margins(5,5,5,5)));

        leftSidebarPanel.add(purchaseContentPanel);


        ContentPanel stockContentPanel=new ContentPanel();
        stockContentPanel.setHeading("Stock");
        stockContentPanel.setLayout(new RowLayout());

        Button viewStockButton=new Button("View Stock");


        stockContentPanel.add(viewStockButton,new RowData(1,-1,new Margins(5,5,5,5)));


        leftSidebarPanel.add(stockContentPanel);


        ContentPanel reportsContentPanel=new ContentPanel();
        reportsContentPanel.setHeading("Reports");
        reportsContentPanel.setLayout(new RowLayout());

        Button salesDetailHtmlButton=new Button("Sales Detail(HTML)");
        Button salesDetailPdfButton=new Button("Sales Detail(PDF)",  new SelectionListener<ButtonEvent>() {

            @Override
            public void componentSelected(ButtonEvent ce)
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
                        reportViewer.setHeight(480);
                        addTab("Sales Details Report",reportViewer);

                    }
                });

                
                
            }
        });


        Button purchaseDetailButton=new Button("Purchase Detail");
        Button salesSummaryButton=new Button("Sales Summary");

        reportsContentPanel.add(salesDetailHtmlButton,new RowData(1,-1,new Margins(5,5,5,5)));
        reportsContentPanel.add(salesDetailPdfButton,new RowData(1,-1,new Margins(5,5,5,5)));
        reportsContentPanel.add(purchaseDetailButton,new RowData(1,-1,new Margins(5,5,5,5)));
        reportsContentPanel.add(salesSummaryButton,new RowData(1,-1,new Margins(5,5,5,5)));

        leftSidebarPanel.add(reportsContentPanel);

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

                EmployeeForm employeeForm = new EmployeeForm();
                addTab("Employee",employeeForm);


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
//                mainContentsPanel.setHeading("Form");
  //              mainContentsPanel.add(branchForm);
    //            mainContentsPanel.layout();


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
//                        mainContentsPanel.setHeading("Reports");
  //                      mainContentsPanel.add(reportViewer);
    //                    mainContentsPanel.layout();


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
