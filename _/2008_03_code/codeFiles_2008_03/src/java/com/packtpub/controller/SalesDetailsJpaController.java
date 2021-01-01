/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.packtpub.controller;

import com.packtpub.beans.SalesDetails;
import com.packtpub.beans.SalesDetailsPK;
import com.packtpub.controller.exceptions.NonexistentEntityException;
import com.packtpub.controller.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.packtpub.beans.Product;
import com.packtpub.beans.Sales;

/**
 *
 * @author Shamsuddin
 */
public class SalesDetailsJpaController {

    public SalesDetailsJpaController()
    {
        emf = Persistence.createEntityManagerFactory("Sales Processing SystemPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    public void create(SalesDetails salesDetails) throws PreexistingEntityException, Exception
    {
        if (salesDetails.getSalesDetailsPK() == null)
        {
            salesDetails.setSalesDetailsPK(new SalesDetailsPK());
        }
        salesDetails.getSalesDetailsPK().setProductCode(salesDetails.getProduct().getProductCode());
        salesDetails.getSalesDetailsPK().setSalesNo(salesDetails.getSales().getSalesNo());
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Product product = salesDetails.getProduct();
            if (product != null)
            {
                product = em.getReference(product.getClass(), product.getProductCode());
                salesDetails.setProduct(product);
            }
            Sales sales = salesDetails.getSales();
            if (sales != null)
            {
                sales = em.getReference(sales.getClass(), sales.getSalesNo());
                salesDetails.setSales(sales);
            }
            em.persist(salesDetails);
            if (product != null)
            {
                product.getSalesDetailsList().add(salesDetails);
                product = em.merge(product);
            }
            if (sales != null)
            {
                sales.getSalesDetailsList().add(salesDetails);
                sales = em.merge(sales);
            }
            em.getTransaction().commit();
        } catch (Exception ex)
        {
            if (findSalesDetails(salesDetails.getSalesDetailsPK()) != null)
            {
                throw new PreexistingEntityException("SalesDetails " + salesDetails + " already exists.", ex);
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

    public void edit(SalesDetails salesDetails) throws NonexistentEntityException, Exception
    {
        salesDetails.getSalesDetailsPK().setProductCode(salesDetails.getProduct().getProductCode());
        salesDetails.getSalesDetailsPK().setSalesNo(salesDetails.getSales().getSalesNo());
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            SalesDetails persistentSalesDetails = em.find(SalesDetails.class, salesDetails.getSalesDetailsPK());
            Product productOld = persistentSalesDetails.getProduct();
            Product productNew = salesDetails.getProduct();
            Sales salesOld = persistentSalesDetails.getSales();
            Sales salesNew = salesDetails.getSales();
            if (productNew != null)
            {
                productNew = em.getReference(productNew.getClass(), productNew.getProductCode());
                salesDetails.setProduct(productNew);
            }
            if (salesNew != null)
            {
                salesNew = em.getReference(salesNew.getClass(), salesNew.getSalesNo());
                salesDetails.setSales(salesNew);
            }
            salesDetails = em.merge(salesDetails);
            if (productOld != null && !productOld.equals(productNew))
            {
                productOld.getSalesDetailsList().remove(salesDetails);
                productOld = em.merge(productOld);
            }
            if (productNew != null && !productNew.equals(productOld))
            {
                productNew.getSalesDetailsList().add(salesDetails);
                productNew = em.merge(productNew);
            }
            if (salesOld != null && !salesOld.equals(salesNew))
            {
                salesOld.getSalesDetailsList().remove(salesDetails);
                salesOld = em.merge(salesOld);
            }
            if (salesNew != null && !salesNew.equals(salesOld))
            {
                salesNew.getSalesDetailsList().add(salesDetails);
                salesNew = em.merge(salesNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex)
        {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0)
            {
                SalesDetailsPK id = salesDetails.getSalesDetailsPK();
                if (findSalesDetails(id) == null)
                {
                    throw new NonexistentEntityException("The salesDetails with id " + id + " no longer exists.");
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

    public void destroy(SalesDetailsPK id) throws NonexistentEntityException
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            SalesDetails salesDetails;
            try
            {
                salesDetails = em.getReference(SalesDetails.class, id);
                salesDetails.getSalesDetailsPK();
            } catch (EntityNotFoundException enfe)
            {
                throw new NonexistentEntityException("The salesDetails with id " + id + " no longer exists.", enfe);
            }
            Product product = salesDetails.getProduct();
            if (product != null)
            {
                product.getSalesDetailsList().remove(salesDetails);
                product = em.merge(product);
            }
            Sales sales = salesDetails.getSales();
            if (sales != null)
            {
                sales.getSalesDetailsList().remove(salesDetails);
                sales = em.merge(sales);
            }
            em.remove(salesDetails);
            em.getTransaction().commit();
        } finally
        {
            if (em != null)
            {
                em.close();
            }
        }
    }

    public List<SalesDetails> findSalesDetailsEntities()
    {
        return findSalesDetailsEntities(true, -1, -1);
    }

    public List<SalesDetails> findSalesDetailsEntities(int maxResults, int firstResult)
    {
        return findSalesDetailsEntities(false, maxResults, firstResult);
    }

    private List<SalesDetails> findSalesDetailsEntities(boolean all, int maxResults, int firstResult)
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SalesDetails.class));
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

    public SalesDetails findSalesDetails(SalesDetailsPK id)
    {
        EntityManager em = getEntityManager();
        try
        {
            return em.find(SalesDetails.class, id);
        } finally
        {
            em.close();
        }
    }

    public int getSalesDetailsCount()
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SalesDetails> rt = cq.from(SalesDetails.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally
        {
            em.close();
        }
    }

}
