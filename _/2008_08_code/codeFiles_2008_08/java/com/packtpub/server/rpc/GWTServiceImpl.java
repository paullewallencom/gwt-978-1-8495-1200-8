/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.packtpub.server.rpc;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.packtpub.beans.Branch;
import com.packtpub.client.dto.BranchDTO;

import com.packtpub.client.rpc.GWTService;
import com.packtpub.controller.BranchJpaController;
import com.packtpub.controller.exceptions.IllegalOrphanException;
import com.packtpub.controller.exceptions.NonexistentEntityException;
import com.packtpub.controller.exceptions.PreexistingEntityException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author Shamsuddin
 */
public class GWTServiceImpl extends RemoteServiceServlet implements GWTService
{
    BranchJpaController branchJpaController = new BranchJpaController();

    public String myMethod(String s)
    {
        // Do something interesting with 's' here on the server.
        return "Server says: " + s;
    }

    @Override
    public boolean addBranch(BranchDTO branchDTO)
    {
        Branch branch = new Branch();
        branch.setBranchId(branchDTO.getBranchId());
        branch.setName(branchDTO.getName());
        branch.setLocation(branchDTO.getLocation());

        
        boolean added=false;
        try
        {
            branchJpaController.create(branch);
            added=true;
        }
        catch (PreexistingEntityException ex)
        {
            Logger.getLogger(GWTServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (Exception ex)
        {
            Logger.getLogger(GWTServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return added;
    }

    @Override
    public String getHtmlReport(String fileName, HashMap<String,Integer> param)
    {
        try
        {

            InitialContext ctx=new InitialContext();
            DataSource dataSource=(DataSource)ctx.lookup("sales");
            Connection con=dataSource.getConnection();


            String filePath = getServletConfig().getServletContext().getRealPath(fileName);
            JasperPrint print = JasperFillManager.fillReport(filePath+".jasper", param, con);

            String newFileName =  filePath+".html";
            JasperExportManager.exportReportToHtmlFile(print, newFileName);
            return fileName+".html";


        }
        catch (NamingException ex)
        {
            Logger.getLogger(GWTServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(GWTServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        catch (JRException ex)
        {
            Logger.getLogger(GWTServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

        @Override
    public String getPdfReport(String fileName, HashMap<String,Integer> param)
    {
        try
        {
            //Class.forName("com.mysql.jdbc.Driver");
            //Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sales", "root", "shams");

            InitialContext ctx=new InitialContext();
            DataSource dataSource=(DataSource)ctx.lookup("sales");
            Connection con=dataSource.getConnection();


            String filePath = getServletConfig().getServletContext().getRealPath(fileName);
            JasperPrint print = JasperFillManager.fillReport(filePath+".jasper", param, con);

            String newFileName =  filePath+".pdf";
            JasperExportManager.exportReportToPdfFile(print, newFileName);
            return fileName+".pdf";

        }
        catch (NamingException ex)
        {
            Logger.getLogger(GWTServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(GWTServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (JRException ex)
        {
            Logger.getLogger(GWTServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    public BranchDTO findBranch(int branchId)
    {
        
        Branch branch=branchJpaController.findBranch(branchId);

        BranchDTO branchDTO=null;

        if(branch!=null)
        {
            branchDTO=new BranchDTO();
            branchDTO.setBranchId(branch.getBranchId());
            branchDTO.setName(branch.getName());
            branchDTO.setLocation(branch.getLocation());
        }

        return branchDTO;
    }

    @Override
    public boolean updateBranch(BranchDTO branchDTO)
    {
        
        boolean updated=false;
        try
        {
            branchJpaController.edit(new Branch(branchDTO));
            updated=true;
        }
        catch (IllegalOrphanException ex)
        {
            Logger.getLogger(GWTServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (NonexistentEntityException ex)
        {
            Logger.getLogger(GWTServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (Exception ex)
        {
            Logger.getLogger(GWTServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return updated;

    }

    @Override
    public boolean deleteBranch(int branchId)
    {
        boolean deleted=false;
        try
        {
            branchJpaController.destroy(branchId);
            deleted=true;
        }
        catch (IllegalOrphanException ex)
        {
            Logger.getLogger(GWTServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (NonexistentEntityException ex)
        {
            Logger.getLogger(GWTServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return deleted;
    }

}
