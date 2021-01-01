/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.packtpub.controller;

import com.packtpub.beans.Customer;
import com.packtpub.controller.exceptions.NonexistentEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.packtpub.beans.Sales;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Shamsuddin
 */
public class CustomerJpaController {

    public CustomerJpaController()
    {
        emf = Persistence.createEntityManagerFactory("Sales Processing SystemPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    public void create(Customer customer)
    {
        if (customer.getSalesList() == null)
        {
            customer.setSalesList(new ArrayList<Sales>());
        }
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Sales> attachedSalesList = new ArrayList<Sales>();
            for (Sales salesListSalesToAttach : customer.getSalesList())
            {
                salesListSalesToAttach = em.getReference(salesListSalesToAttach.getClass(), salesListSalesToAttach.getSalesNo());
                attachedSalesList.add(salesListSalesToAttach);
            }
            customer.setSalesList(attachedSalesList);
            em.persist(customer);
            for (Sales salesListSales : customer.getSalesList())
            {
                Customer oldCustomerOfSalesListSales = salesListSales.getCustomer();
                salesListSales.setCustomer(customer);
                salesListSales = em.merge(salesListSales);
                if (oldCustomerOfSalesListSales != null)
                {
                    oldCustomerOfSalesListSales.getSalesList().remove(salesListSales);
                    oldCustomerOfSalesListSales = em.merge(oldCustomerOfSalesListSales);
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

    public void edit(Customer customer) throws NonexistentEntityException, Exception
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Customer persistentCustomer = em.find(Customer.class, customer.getCustomerNo());
            List<Sales> salesListOld = persistentCustomer.getSalesList();
            List<Sales> salesListNew = customer.getSalesList();
            List<Sales> attachedSalesListNew = new ArrayList<Sales>();
            for (Sales salesListNewSalesToAttach : salesListNew)
            {
                salesListNewSalesToAttach = em.getReference(salesListNewSalesToAttach.getClass(), salesListNewSalesToAttach.getSalesNo());
                attachedSalesListNew.add(salesListNewSalesToAttach);
            }
            salesListNew = attachedSalesListNew;
            customer.setSalesList(salesListNew);
            customer = em.merge(customer);
            for (Sales salesListOldSales : salesListOld)
            {
                if (!salesListNew.contains(salesListOldSales))
                {
                    salesListOldSales.setCustomer(null);
                    salesListOldSales = em.merge(salesListOldSales);
                }
            }
            for (Sales salesListNewSales : salesListNew)
            {
                if (!salesListOld.contains(salesListNewSales))
                {
                    Customer oldCustomerOfSalesListNewSales = salesListNewSales.getCustomer();
                    salesListNewSales.setCustomer(customer);
                    salesListNewSales = em.merge(salesListNewSales);
                    if (oldCustomerOfSalesListNewSales != null && !oldCustomerOfSalesListNewSales.equals(customer))
                    {
                        oldCustomerOfSalesListNewSales.getSalesList().remove(salesListNewSales);
                        oldCustomerOfSalesListNewSales = em.merge(oldCustomerOfSalesListNewSales);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex)
        {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0)
            {
                Integer id = customer.getCustomerNo();
                if (findCustomer(id) == null)
                {
                    throw new NonexistentEntityException("The customer with id " + id + " no longer exists.");
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

    public void destroy(Integer id) throws NonexistentEntityException
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Customer customer;
            try
            {
                customer = em.getReference(Customer.class, id);
                customer.getCustomerNo();
            } catch (EntityNotFoundException enfe)
            {
                throw new NonexistentEntityException("The customer with id " + id + " no longer exists.", enfe);
            }
            List<Sales> salesList = customer.getSalesList();
            for (Sales salesListSales : salesList)
            {
                salesListSales.setCustomer(null);
                salesListSales = em.merge(salesListSales);
            }
            em.remove(customer);
            em.getTransaction().commit();
        } finally
        {
            if (em != null)
            {
                em.close();
            }
        }
    }

    public List<Customer> findCustomerEntities()
    {
        return findCustomerEntities(true, -1, -1);
    }

    public List<Customer> findCustomerEntities(int maxResults, int firstResult)
    {
        return findCustomerEntities(false, maxResults, firstResult);
    }

    private List<Customer> findCustomerEntities(boolean all, int maxResults, int firstResult)
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Customer.class));
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

    public Customer findCustomer(Integer id)
    {
        EntityManager em = getEntityManager();
        try
        {
            return em.find(Customer.class, id);
        } finally
        {
            em.close();
        }
    }

    public int getCustomerCount()
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Customer> rt = cq.from(Customer.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally
        {
            em.close();
        }
    }

}
