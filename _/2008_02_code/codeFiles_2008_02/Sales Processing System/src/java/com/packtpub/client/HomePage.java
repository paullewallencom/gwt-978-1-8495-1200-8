
package com.packtpub.client;

/**
 *
 * @author Shamsuddin Ahammad
 */

import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.menu.MenuBar;
import com.extjs.gxt.ui.client.widget.menu.MenuBarItem;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;

public class HomePage extends ContentPanel
{

    public HomePage()
    {

        setSize(980,630);
        setHeaderVisible(false);
  
        BorderLayout layout = new BorderLayout();
        setLayout(layout);
        
        BorderLayoutData menubarLayoutData = new BorderLayoutData(LayoutRegion.NORTH, 25);
        menubarLayoutData.setMargins(new Margins(5));

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

        add(getMenuBar(), menubarLayoutData);
        add(getLeftSideBar(), leftSidebarLayoutData);
        add(getMainContents(), mainContentsLayoutData);
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

    public ContentPanel getMainContents()
    {


        ContentPanel mainContentsPanel = new ContentPanel();
        mainContentsPanel.setHeading("Main Contents");
        return mainContentsPanel;

    }

    public MenuBar getMenuBar()
    {
       MenuBar menuBar=new MenuBar();

        //Menus

        Menu fileMenu=new Menu();
        Menu reportsMenu=new Menu();
        Menu helpMenu=new Menu();

        //Items for File menu

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
}
