/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.packtpub.controller;

import com.packtpub.beans.Sales;
import com.packtpub.controller.exceptions.IllegalOrphanException;
import com.packtpub.controller.exceptions.NonexistentEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.packtpub.beans.SalesDesk;
import com.packtpub.beans.Employee;
import com.packtpub.beans.Customer;
import com.packtpub.beans.SalesDetails;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Shamsuddin
 */
public class SalesJpaController {

    public SalesJpaController()
    {
        emf = Persistence.createEntityManagerFactory("Sales Processing SystemPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    public void create(Sales sales)
    {
        if (sales.getSalesDetailsList() == null)
        {
            sales.setSalesDetailsList(new ArrayList<SalesDetails>());
        }
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            SalesDesk salesDesk = sales.getSalesDesk();
            if (salesDesk != null)
            {
                salesDesk = em.getReference(salesDesk.getClass(), salesDesk.getSalesDeskId());
                sales.setSalesDesk(salesDesk);
            }
            Employee employee = sales.getEmployee();
            if (employee != null)
            {
                employee = em.getReference(employee.getClass(), employee.getEmployeeId());
                sales.setEmployee(employee);
            }
            Customer customer = sales.getCustomer();
            if (customer != null)
            {
                customer = em.getReference(customer.getClass(), customer.getCustomerNo());
                sales.setCustomer(customer);
            }
            List<SalesDetails> attachedSalesDetailsList = new ArrayList<SalesDetails>();
            for (SalesDetails salesDetailsListSalesDetailsToAttach : sales.getSalesDetailsList())
            {
                salesDetailsListSalesDetailsToAttach = em.getReference(salesDetailsListSalesDetailsToAttach.getClass(), salesDetailsListSalesDetailsToAttach.getSalesDetailsPK());
                attachedSalesDetailsList.add(salesDetailsListSalesDetailsToAttach);
            }
            sales.setSalesDetailsList(attachedSalesDetailsList);
            em.persist(sales);
            if (salesDesk != null)
            {
                salesDesk.getSalesList().add(sales);
                salesDesk = em.merge(salesDesk);
            }
            if (employee != null)
            {
                employee.getSalesList().add(sales);
                employee = em.merge(employee);
            }
            if (customer != null)
            {
                customer.getSalesList().add(sales);
                customer = em.merge(customer);
            }
            for (SalesDetails salesDetailsListSalesDetails : sales.getSalesDetailsList())
            {
                Sales oldSalesOfSalesDetailsListSalesDetails = salesDetailsListSalesDetails.getSales();
                salesDetailsListSalesDetails.setSales(sales);
                salesDetailsListSalesDetails = em.merge(salesDetailsListSalesDetails);
                if (oldSalesOfSalesDetailsListSalesDetails != null)
                {
                    oldSalesOfSalesDetailsListSalesDetails.getSalesDetailsList().remove(salesDetailsListSalesDetails);
                    oldSalesOfSalesDetailsListSalesDetails = em.merge(oldSalesOfSalesDetailsListSalesDetails);
                }
            }
            em.getTransaction().commit();
        } finally
        {
            if (em != null)
            {
                em.close();
            }
        }
    }

    public void edit(Sales sales) throws IllegalOrphanException, NonexistentEntityException, Exception
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Sales persistentSales = em.find(Sales.class, sales.getSalesNo());
            SalesDesk salesDeskOld = persistentSales.getSalesDesk();
            SalesDesk salesDeskNew = sales.getSalesDesk();
            Employee employeeOld = persistentSales.getEmployee();
            Employee employeeNew = sales.getEmployee();
            Customer customerOld = persistentSales.getCustomer();
            Customer customerNew = sales.getCustomer();
            List<SalesDetails> salesDetailsListOld = persistentSales.getSalesDetailsList();
            List<SalesDetails> salesDetailsListNew = sales.getSalesDetailsList();
            List<String> illegalOrphanMessages = null;
            for (SalesDetails salesDetailsListOldSalesDetails : salesDetailsListOld)
            {
                if (!salesDetailsListNew.contains(salesDetailsListOldSalesDetails))
                {
                    if (illegalOrphanMessages == null)
                    {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain SalesDetails " + salesDetailsListOldSalesDetails + " since its sales field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null)
            {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (salesDeskNew != null)
            {
                salesDeskNew = em.getReference(salesDeskNew.getClass(), salesDeskNew.getSalesDeskId());
                sales.setSalesDesk(salesDeskNew);
            }
            if (employeeNew != null)
            {
                employeeNew = em.getReference(employeeNew.getClass(), employeeNew.getEmployeeId());
                sales.setEmployee(employeeNew);
            }
            if (customerNew != null)
            {
                customerNew = em.getReference(customerNew.getClass(), customerNew.getCustomerNo());
                sales.setCustomer(customerNew);
            }
            List<SalesDetails> attachedSalesDetailsListNew = new ArrayList<SalesDetails>();
            for (SalesDetails salesDetailsListNewSalesDetailsToAttach : salesDetailsListNew)
            {
                salesDetailsListNewSalesDetailsToAttach = em.getReference(salesDetailsListNewSalesDetailsToAttach.getClass(), salesDetailsListNewSalesDetailsToAttach.getSalesDetailsPK());
                attachedSalesDetailsListNew.add(salesDetailsListNewSalesDetailsToAttach);
            }
            salesDetailsListNew = attachedSalesDetailsListNew;
            sales.setSalesDetailsList(salesDetailsListNew);
            sales = em.merge(sales);
            if (salesDeskOld != null && !salesDeskOld.equals(salesDeskNew))
            {
                salesDeskOld.getSalesList().remove(sales);
                salesDeskOld = em.merge(salesDeskOld);
            }
            if (salesDeskNew != null && !salesDeskNew.equals(salesDeskOld))
            {
                salesDeskNew.getSalesList().add(sales);
                salesDeskNew = em.merge(salesDeskNew);
            }
            if (employeeOld != null && !employeeOld.equals(employeeNew))
            {
                employeeOld.getSalesList().remove(sales);
                employeeOld = em.merge(employeeOld);
            }
            if (employeeNew != null && !employeeNew.equals(employeeOld))
            {
                employeeNew.getSalesList().add(sales);
                employeeNew = em.merge(employeeNew);
            }
            if (customerOld != null && !customerOld.equals(customerNew))
            {
                customerOld.getSalesList().remove(sales);
                customerOld = em.merge(customerOld);
            }
            if (customerNew != null && !customerNew.equals(customerOld))
            {
                customerNew.getSalesList().add(sales);
                customerNew = em.merge(customerNew);
            }
            for (SalesDetails salesDetailsListNewSalesDetails : salesDetailsListNew)
            {
                if (!salesDetailsListOld.contains(salesDetailsListNewSalesDetails))
                {
                    Sales oldSalesOfSalesDetailsListNewSalesDetails = salesDetailsListNewSalesDetails.getSales();
                    salesDetailsListNewSalesDetails.setSales(sales);
                    salesDetailsListNewSalesDetails = em.merge(salesDetailsListNewSalesDetails);
                    if (oldSalesOfSalesDetailsListNewSalesDetails != null && !oldSalesOfSalesDetailsListNewSalesDetails.equals(sales))
                    {
                        oldSalesOfSalesDetailsListNewSalesDetails.getSalesDetailsList().remove(salesDetailsListNewSalesDetails);
                        oldSalesOfSalesDetailsListNewSalesDetails = em.merge(oldSalesOfSalesDetailsListNewSalesDetails);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex)
        {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0)
            {
                Integer id = sales.getSalesNo();
                if (findSales(id) == null)
                {
                    throw new NonexistentEntityException("The sales with id " + id + " no longer exists.");
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
            Sales sales;
            try
            {
                sales = em.getReference(Sales.class, id);
                sales.getSalesNo();
            } catch (EntityNotFoundException enfe)
            {
                throw new NonexistentEntityException("The sales with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<SalesDetails> salesDetailsListOrphanCheck = sales.getSalesDetailsList();
            for (SalesDetails salesDetailsListOrphanCheckSalesDetails : salesDetailsListOrphanCheck)
            {
                if (illegalOrphanMessages == null)
                {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Sales (" + sales + ") cannot be destroyed since the SalesDetails " + salesDetailsListOrphanCheckSalesDetails + " in its salesDetailsList field has a non-nullable sales field.");
            }
            if (illegalOrphanMessages != null)
            {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            SalesDesk salesDesk = sales.getSalesDesk();
            if (salesDesk != null)
            {
                salesDesk.getSalesList().remove(sales);
                salesDesk = em.merge(salesDesk);
            }
            Employee employee = sales.getEmployee();
            if (employee != null)
            {
                employee.getSalesList().remove(sales);
                employee = em.merge(employee);
            }
            Customer customer = sales.getCustomer();
            if (customer != null)
            {
                customer.getSalesList().remove(sales);
                customer = em.merge(customer);
            }
            em.remove(sales);
            em.getTransaction().commit();
        } finally
        {
            if (em != null)
            {
                em.close();
            }
        }
    }

    public List<Sales> findSalesEntities()
    {
        return findSalesEntities(true, -1, -1);
    }

    public List<Sales> findSalesEntities(int maxResults, int firstResult)
    {
        return findSalesEntities(false, maxResults, firstResult);
    }

    private List<Sales> findSalesEntities(boolean all, int maxResults, int firstResult)
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sales.class));
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

    public Sales findSales(Integer id)
    {
        EntityManager em = getEntityManager();
        try
        {
            return em.find(Sales.class, id);
        } finally
        {
            em.close();
        }
    }

    public int getSalesCount()
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sales> rt = cq.from(Sales.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally
        {
            em.close();
        }
    }

}
