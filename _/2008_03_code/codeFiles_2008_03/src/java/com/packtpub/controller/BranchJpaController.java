/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.packtpub.controller;

import com.packtpub.beans.Branch;
import com.packtpub.controller.exceptions.IllegalOrphanException;
import com.packtpub.controller.exceptions.NonexistentEntityException;
import com.packtpub.controller.exceptions.PreexistingEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.packtpub.beans.Stock;
import java.util.ArrayList;
import java.util.List;
import com.packtpub.beans.Employee;
import com.packtpub.beans.SalesDesk;

/**
 *
 * @author Shamsuddin
 */
public class BranchJpaController {

    public BranchJpaController()
    {
        emf = Persistence.createEntityManagerFactory("Sales Processing SystemPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    public void create(Branch branch) throws PreexistingEntityException, Exception
    {
        if (branch.getStockList() == null)
        {
            branch.setStockList(new ArrayList<Stock>());
        }
        if (branch.getEmployeeList() == null)
        {
            branch.setEmployeeList(new ArrayList<Employee>());
        }
        if (branch.getSalesDeskList() == null)
        {
            branch.setSalesDeskList(new ArrayList<SalesDesk>());
        }
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Stock> attachedStockList = new ArrayList<Stock>();
            for (Stock stockListStockToAttach : branch.getStockList())
            {
                stockListStockToAttach = em.getReference(stockListStockToAttach.getClass(), stockListStockToAttach.getProductCode());
                attachedStockList.add(stockListStockToAttach);
            }
            branch.setStockList(attachedStockList);
            List<Employee> attachedEmployeeList = new ArrayList<Employee>();
            for (Employee employeeListEmployeeToAttach : branch.getEmployeeList())
            {
                employeeListEmployeeToAttach = em.getReference(employeeListEmployeeToAttach.getClass(), employeeListEmployeeToAttach.getEmployeeId());
                attachedEmployeeList.add(employeeListEmployeeToAttach);
            }
            branch.setEmployeeList(attachedEmployeeList);
            List<SalesDesk> attachedSalesDeskList = new ArrayList<SalesDesk>();
            for (SalesDesk salesDeskListSalesDeskToAttach : branch.getSalesDeskList())
            {
                salesDeskListSalesDeskToAttach = em.getReference(salesDeskListSalesDeskToAttach.getClass(), salesDeskListSalesDeskToAttach.getSalesDeskId());
                attachedSalesDeskList.add(salesDeskListSalesDeskToAttach);
            }
            branch.setSalesDeskList(attachedSalesDeskList);
            em.persist(branch);
            for (Stock stockListStock : branch.getStockList())
            {
                Branch oldBranchOfStockListStock = stockListStock.getBranch();
                stockListStock.setBranch(branch);
                stockListStock = em.merge(stockListStock);
                if (oldBranchOfStockListStock != null)
                {
                    oldBranchOfStockListStock.getStockList().remove(stockListStock);
                    oldBranchOfStockListStock = em.merge(oldBranchOfStockListStock);
                }
            }
            for (Employee employeeListEmployee : branch.getEmployeeList())
            {
                Branch oldBranchOfEmployeeListEmployee = employeeListEmployee.getBranch();
                employeeListEmployee.setBranch(branch);
                employeeListEmployee = em.merge(employeeListEmployee);
                if (oldBranchOfEmployeeListEmployee != null)
                {
                    oldBranchOfEmployeeListEmployee.getEmployeeList().remove(employeeListEmployee);
                    oldBranchOfEmployeeListEmployee = em.merge(oldBranchOfEmployeeListEmployee);
                }
            }
            for (SalesDesk salesDeskListSalesDesk : branch.getSalesDeskList())
            {
                Branch oldBranchOfSalesDeskListSalesDesk = salesDeskListSalesDesk.getBranch();
                salesDeskListSalesDesk.setBranch(branch);
                salesDeskListSalesDesk = em.merge(salesDeskListSalesDesk);
                if (oldBranchOfSalesDeskListSalesDesk != null)
                {
                    oldBranchOfSalesDeskListSalesDesk.getSalesDeskList().remove(salesDeskListSalesDesk);
                    oldBranchOfSalesDeskListSalesDesk = em.merge(oldBranchOfSalesDeskListSalesDesk);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex)
        {
            if (findBranch(branch.getBranchId()) != null)
            {
                throw new PreexistingEntityException("Branch " + branch + " already exists.", ex);
            }
            throw ex;
        } finally
        {
            if (em != null)
            {
                em.close();
            }
        }
    }

    public void edit(Branch branch) throws IllegalOrphanException, NonexistentEntityException, Exception
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Branch persistentBranch = em.find(Branch.class, branch.getBranchId());
            List<Stock> stockListOld = persistentBranch.getStockList();
            List<Stock> stockListNew = branch.getStockList();
            List<Employee> employeeListOld = persistentBranch.getEmployeeList();
            List<Employee> employeeListNew = branch.getEmployeeList();
            List<SalesDesk> salesDeskListOld = persistentBranch.getSalesDeskList();
            List<SalesDesk> salesDeskListNew = branch.getSalesDeskList();
            List<String> illegalOrphanMessages = null;
            for (Stock stockListOldStock : stockListOld)
            {
                if (!stockListNew.contains(stockListOldStock))
                {
                    if (illegalOrphanMessages == null)
                    {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Stock " + stockListOldStock + " since its branch field is not nullable.");
                }
            }
            for (Employee employeeListOldEmployee : employeeListOld)
            {
                if (!employeeListNew.contains(employeeListOldEmployee))
                {
                    if (illegalOrphanMessages == null)
                    {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Employee " + employeeListOldEmployee + " since its branch field is not nullable.");
                }
            }
            for (SalesDesk salesDeskListOldSalesDesk : salesDeskListOld)
            {
                if (!salesDeskListNew.contains(salesDeskListOldSalesDesk))
                {
                    if (illegalOrphanMessages == null)
                    {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain SalesDesk " + salesDeskListOldSalesDesk + " since its branch field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null)
            {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Stock> attachedStockListNew = new ArrayList<Stock>();
            for (Stock stockListNewStockToAttach : stockListNew)
            {
                stockListNewStockToAttach = em.getReference(stockListNewStockToAttach.getClass(), stockListNewStockToAttach.getProductCode());
                attachedStockListNew.add(stockListNewStockToAttach);
            }
            stockListNew = attachedStockListNew;
            branch.setStockList(stockListNew);
            List<Employee> attachedEmployeeListNew = new ArrayList<Employee>();
            for (Employee employeeListNewEmployeeToAttach : employeeListNew)
            {
                employeeListNewEmployeeToAttach = em.getReference(employeeListNewEmployeeToAttach.getClass(), employeeListNewEmployeeToAttach.getEmployeeId());
                attachedEmployeeListNew.add(employeeListNewEmployeeToAttach);
            }
            employeeListNew = attachedEmployeeListNew;
            branch.setEmployeeList(employeeListNew);
            List<SalesDesk> attachedSalesDeskListNew = new ArrayList<SalesDesk>();
            for (SalesDesk salesDeskListNewSalesDeskToAttach : salesDeskListNew)
            {
                salesDeskListNewSalesDeskToAttach = em.getReference(salesDeskListNewSalesDeskToAttach.getClass(), salesDeskListNewSalesDeskToAttach.getSalesDeskId());
                attachedSalesDeskListNew.add(salesDeskListNewSalesDeskToAttach);
            }
            salesDeskListNew = attachedSalesDeskListNew;
            branch.setSalesDeskList(salesDeskListNew);
            branch = em.merge(branch);
            for (Stock stockListNewStock : stockListNew)
            {
                if (!stockListOld.contains(stockListNewStock))
                {
                    Branch oldBranchOfStockListNewStock = stockListNewStock.getBranch();
                    stockListNewStock.setBranch(branch);
                    stockListNewStock = em.merge(stockListNewStock);
                    if (oldBranchOfStockListNewStock != null && !oldBranchOfStockListNewStock.equals(branch))
                    {
                        oldBranchOfStockListNewStock.getStockList().remove(stockListNewStock);
                        oldBranchOfStockListNewStock = em.merge(oldBranchOfStockListNewStock);
                    }
                }
            }
            for (Employee employeeListNewEmployee : employeeListNew)
            {
                if (!employeeListOld.contains(employeeListNewEmployee))
                {
                    Branch oldBranchOfEmployeeListNewEmployee = employeeListNewEmployee.getBranch();
                    employeeListNewEmployee.setBranch(branch);
                    employeeListNewEmployee = em.merge(employeeListNewEmployee);
                    if (oldBranchOfEmployeeListNewEmployee != null && !oldBranchOfEmployeeListNewEmployee.equals(branch))
                    {
                        oldBranchOfEmployeeListNewEmployee.getEmployeeList().remove(employeeListNewEmployee);
                        oldBranchOfEmployeeListNewEmployee = em.merge(oldBranchOfEmployeeListNewEmployee);
                    }
                }
            }
            for (SalesDesk salesDeskListNewSalesDesk : salesDeskListNew)
            {
                if (!salesDeskListOld.contains(salesDeskListNewSalesDesk))
                {
                    Branch oldBranchOfSalesDeskListNewSalesDesk = salesDeskListNewSalesDesk.getBranch();
                    salesDeskListNewSalesDesk.setBranch(branch);
                    salesDeskListNewSalesDesk = em.merge(salesDeskListNewSalesDesk);
                    if (oldBranchOfSalesDeskListNewSalesDesk != null && !oldBranchOfSalesDeskListNewSalesDesk.equals(branch))
                    {
                        oldBranchOfSalesDeskListNewSalesDesk.getSalesDeskList().remove(salesDeskListNewSalesDesk);
                        oldBranchOfSalesDeskListNewSalesDesk = em.merge(oldBranchOfSalesDeskListNewSalesDesk);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex)
        {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0)
            {
                Integer id = branch.getBranchId();
                if (findBranch(id) == null)
                {
                    throw new NonexistentEntityException("The branch with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally
        {
            if (em != null)
            {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Branch branch;
            try
            {
                branch = em.getReference(Branch.class, id);
                branch.getBranchId();
            } catch (EntityNotFoundException enfe)
            {
                throw new NonexistentEntityException("The branch with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Stock> stockListOrphanCheck = branch.getStockList();
            for (Stock stockListOrphanCheckStock : stockListOrphanCheck)
            {
                if (illegalOrphanMessages == null)
                {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Branch (" + branch + ") cannot be destroyed since the Stock " + stockListOrphanCheckStock + " in its stockList field has a non-nullable branch field.");
            }
            List<Employee> employeeListOrphanCheck = branch.getEmployeeList();
            for (Employee employeeListOrphanCheckEmployee : employeeListOrphanCheck)
            {
                if (illegalOrphanMessages == null)
                {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Branch (" + branch + ") cannot be destroyed since the Employee " + employeeListOrphanCheckEmployee + " in its employeeList field has a non-nullable branch field.");
            }
            List<SalesDesk> salesDeskListOrphanCheck = branch.getSalesDeskList();
            for (SalesDesk salesDeskListOrphanCheckSalesDesk : salesDeskListOrphanCheck)
            {
                if (illegalOrphanMessages == null)
                {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Branch (" + branch + ") cannot be destroyed since the SalesDesk " + salesDeskListOrphanCheckSalesDesk + " in its salesDeskList field has a non-nullable branch field.");
            }
            if (illegalOrphanMessages != null)
            {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(branch);
            em.getTransaction().commit();
        } finally
        {
            if (em != null)
            {
                em.close();
            }
        }
    }

    public List<Branch> findBranchEntities()
    {
        return findBranchEntities(true, -1, -1);
    }

    public List<Branch> findBranchEntities(int maxResults, int firstResult)
    {
        return findBranchEntities(false, maxResults, firstResult);
    }

    private List<Branch> findBranchEntities(boolean all, int maxResults, int firstResult)
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Branch.class));
            Query q = em.createQuery(cq);
            if (!all)
            {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally
        {
            em.close();
        }
    }

    public Branch findBranch(Integer id)
    {
        EntityManager em = getEntityManager();
        try
        {
            return em.find(Branch.class, id);
        } finally
        {
            em.close();
        }
    }

    public int getBranchCount()
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Branch> rt = cq.from(Branch.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally
        {
            em.close();
        }
    }

}
