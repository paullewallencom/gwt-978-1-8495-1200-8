/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.packtpub.controller;

import com.packtpub.beans.Stock;
import com.packtpub.controller.exceptions.IllegalOrphanException;
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
import com.packtpub.beans.Branch;
import java.util.ArrayList;

/**
 *
 * @author Shamsuddin
 */
public class StockJpaController {

    public StockJpaController()
    {
        emf = Persistence.createEntityManagerFactory("Sales Processing SystemPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    public void create(Stock stock) throws IllegalOrphanException, PreexistingEntityException, Exception
    {
        List<String> illegalOrphanMessages = null;
        Product productOrphanCheck = stock.getProduct();
        if (productOrphanCheck != null)
        {
            Stock oldStockOfProduct = productOrphanCheck.getStock();
            if (oldStockOfProduct != null)
            {
                if (illegalOrphanMessages == null)
                {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Product " + productOrphanCheck + " already has an item of type Stock whose product column cannot be null. Please make another selection for the product field.");
            }
        }
        if (illegalOrphanMessages != null)
        {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Product product = stock.getProduct();
            if (product != null)
            {
                product = em.getReference(product.getClass(), product.getProductCode());
                stock.setProduct(product);
            }
            Branch branch = stock.getBranch();
            if (branch != null)
            {
                branch = em.getReference(branch.getClass(), branch.getBranchId());
                stock.setBranch(branch);
            }
            em.persist(stock);
            if (product != null)
            {
                product.setStock(stock);
                product = em.merge(product);
            }
            if (branch != null)
            {
                branch.getStockList().add(stock);
                branch = em.merge(branch);
            }
            em.getTransaction().commit();
        } catch (Exception ex)
        {
            if (findStock(stock.getProductCode()) != null)
            {
                throw new PreexistingEntityException("Stock " + stock + " already exists.", ex);
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

    public void edit(Stock stock) throws IllegalOrphanException, NonexistentEntityException, Exception
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Stock persistentStock = em.find(Stock.class, stock.getProductCode());
            Product productOld = persistentStock.getProduct();
            Product productNew = stock.getProduct();
            Branch branchOld = persistentStock.getBranch();
            Branch branchNew = stock.getBranch();
            List<String> illegalOrphanMessages = null;
            if (productNew != null && !productNew.equals(productOld))
            {
                Stock oldStockOfProduct = productNew.getStock();
                if (oldStockOfProduct != null)
                {
                    if (illegalOrphanMessages == null)
                    {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Product " + productNew + " already has an item of type Stock whose product column cannot be null. Please make another selection for the product field.");
                }
            }
            if (illegalOrphanMessages != null)
            {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (productNew != null)
            {
                productNew = em.getReference(productNew.getClass(), productNew.getProductCode());
                stock.setProduct(productNew);
            }
            if (branchNew != null)
            {
                branchNew = em.getReference(branchNew.getClass(), branchNew.getBranchId());
                stock.setBranch(branchNew);
            }
            stock = em.merge(stock);
            if (productOld != null && !productOld.equals(productNew))
            {
                productOld.setStock(null);
                productOld = em.merge(productOld);
            }
            if (productNew != null && !productNew.equals(productOld))
            {
                productNew.setStock(stock);
                productNew = em.merge(productNew);
            }
            if (branchOld != null && !branchOld.equals(branchNew))
            {
                branchOld.getStockList().remove(stock);
                branchOld = em.merge(branchOld);
            }
            if (branchNew != null && !branchNew.equals(branchOld))
            {
                branchNew.getStockList().add(stock);
                branchNew = em.merge(branchNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex)
        {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0)
            {
                Integer id = stock.getProductCode();
                if (findStock(id) == null)
                {
                    throw new NonexistentEntityException("The stock with id " + id + " no longer exists.");
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
            Stock stock;
            try
            {
                stock = em.getReference(Stock.class, id);
                stock.getProductCode();
            } catch (EntityNotFoundException enfe)
            {
                throw new NonexistentEntityException("The stock with id " + id + " no longer exists.", enfe);
            }
            Product product = stock.getProduct();
            if (product != null)
            {
                product.setStock(null);
                product = em.merge(product);
            }
            Branch branch = stock.getBranch();
            if (branch != null)
            {
                branch.getStockList().remove(stock);
                branch = em.merge(branch);
            }
            em.remove(stock);
            em.getTransaction().commit();
        } finally
        {
            if (em != null)
            {
                em.close();
            }
        }
    }

    public List<Stock> findStockEntities()
    {
        return findStockEntities(true, -1, -1);
    }

    public List<Stock> findStockEntities(int maxResults, int firstResult)
    {
        return findStockEntities(false, maxResults, firstResult);
    }

    private List<Stock> findStockEntities(boolean all, int maxResults, int firstResult)
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Stock.class));
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

    public Stock findStock(Integer id)
    {
        EntityManager em = getEntityManager();
        try
        {
            return em.find(Stock.class, id);
        } finally
        {
            em.close();
        }
    }

    public int getStockCount()
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Stock> rt = cq.from(Stock.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally
        {
            em.close();
        }
    }

}
