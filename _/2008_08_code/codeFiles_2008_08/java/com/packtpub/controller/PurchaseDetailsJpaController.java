/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.packtpub.controller;

import com.packtpub.beans.PurchaseDetails;
import com.packtpub.beans.PurchaseDetailsPK;
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
import com.packtpub.beans.Purchase;
import com.packtpub.beans.Product;

/**
 *
 * @author Shamsuddin
 */
public class PurchaseDetailsJpaController {

    public PurchaseDetailsJpaController()
    {
        emf = Persistence.createEntityManagerFactory("Sales Processing SystemPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    public void create(PurchaseDetails purchaseDetails) throws PreexistingEntityException, Exception
    {
        if (purchaseDetails.getPurchaseDetailsPK() == null)
        {
            purchaseDetails.setPurchaseDetailsPK(new PurchaseDetailsPK());
        }
        purchaseDetails.getPurchaseDetailsPK().setPurchaseNo(purchaseDetails.getPurchase().getPurchaseNo());
        purchaseDetails.getPurchaseDetailsPK().setProductCode(purchaseDetails.getProduct().getProductCode());
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Purchase purchase = purchaseDetails.getPurchase();
            if (purchase != null)
            {
                purchase = em.getReference(purchase.getClass(), purchase.getPurchaseNo());
                purchaseDetails.setPurchase(purchase);
            }
            Product product = purchaseDetails.getProduct();
            if (product != null)
            {
                product = em.getReference(product.getClass(), product.getProductCode());
                purchaseDetails.setProduct(product);
            }
            em.persist(purchaseDetails);
            if (purchase != null)
            {
                purchase.getPurchaseDetailsList().add(purchaseDetails);
                purchase = em.merge(purchase);
            }
            if (product != null)
            {
                product.getPurchaseDetailsList().add(purchaseDetails);
                product = em.merge(product);
            }
            em.getTransaction().commit();
        } catch (Exception ex)
        {
            if (findPurchaseDetails(purchaseDetails.getPurchaseDetailsPK()) != null)
            {
                throw new PreexistingEntityException("PurchaseDetails " + purchaseDetails + " already exists.", ex);
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

    public void edit(PurchaseDetails purchaseDetails) throws NonexistentEntityException, Exception
    {
        purchaseDetails.getPurchaseDetailsPK().setPurchaseNo(purchaseDetails.getPurchase().getPurchaseNo());
        purchaseDetails.getPurchaseDetailsPK().setProductCode(purchaseDetails.getProduct().getProductCode());
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            PurchaseDetails persistentPurchaseDetails = em.find(PurchaseDetails.class, purchaseDetails.getPurchaseDetailsPK());
            Purchase purchaseOld = persistentPurchaseDetails.getPurchase();
            Purchase purchaseNew = purchaseDetails.getPurchase();
            Product productOld = persistentPurchaseDetails.getProduct();
            Product productNew = purchaseDetails.getProduct();
            if (purchaseNew != null)
            {
                purchaseNew = em.getReference(purchaseNew.getClass(), purchaseNew.getPurchaseNo());
                purchaseDetails.setPurchase(purchaseNew);
            }
            if (productNew != null)
            {
                productNew = em.getReference(productNew.getClass(), productNew.getProductCode());
                purchaseDetails.setProduct(productNew);
            }
            purchaseDetails = em.merge(purchaseDetails);
            if (purchaseOld != null && !purchaseOld.equals(purchaseNew))
            {
                purchaseOld.getPurchaseDetailsList().remove(purchaseDetails);
                purchaseOld = em.merge(purchaseOld);
            }
            if (purchaseNew != null && !purchaseNew.equals(purchaseOld))
            {
                purchaseNew.getPurchaseDetailsList().add(purchaseDetails);
                purchaseNew = em.merge(purchaseNew);
            }
            if (productOld != null && !productOld.equals(productNew))
            {
                productOld.getPurchaseDetailsList().remove(purchaseDetails);
                productOld = em.merge(productOld);
            }
            if (productNew != null && !productNew.equals(productOld))
            {
                productNew.getPurchaseDetailsList().add(purchaseDetails);
                productNew = em.merge(productNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex)
        {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0)
            {
                PurchaseDetailsPK id = purchaseDetails.getPurchaseDetailsPK();
                if (findPurchaseDetails(id) == null)
                {
                    throw new NonexistentEntityException("The purchaseDetails with id " + id + " no longer exists.");
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

    public void destroy(PurchaseDetailsPK id) throws NonexistentEntityException
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            PurchaseDetails purchaseDetails;
            try
            {
                purchaseDetails = em.getReference(PurchaseDetails.class, id);
                purchaseDetails.getPurchaseDetailsPK();
            } catch (EntityNotFoundException enfe)
            {
                throw new NonexistentEntityException("The purchaseDetails with id " + id + " no longer exists.", enfe);
            }
            Purchase purchase = purchaseDetails.getPurchase();
            if (purchase != null)
            {
                purchase.getPurchaseDetailsList().remove(purchaseDetails);
                purchase = em.merge(purchase);
            }
            Product product = purchaseDetails.getProduct();
            if (product != null)
            {
                product.getPurchaseDetailsList().remove(purchaseDetails);
                product = em.merge(product);
            }
            em.remove(purchaseDetails);
            em.getTransaction().commit();
        } finally
        {
            if (em != null)
            {
                em.close();
            }
        }
    }

    public List<PurchaseDetails> findPurchaseDetailsEntities()
    {
        return findPurchaseDetailsEntities(true, -1, -1);
    }

    public List<PurchaseDetails> findPurchaseDetailsEntities(int maxResults, int firstResult)
    {
        return findPurchaseDetailsEntities(false, maxResults, firstResult);
    }

    private List<PurchaseDetails> findPurchaseDetailsEntities(boolean all, int maxResults, int firstResult)
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PurchaseDetails.class));
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

    public PurchaseDetails findPurchaseDetails(PurchaseDetailsPK id)
    {
        EntityManager em = getEntityManager();
        try
        {
            return em.find(PurchaseDetails.class, id);
        } finally
        {
            em.close();
        }
    }

    public int getPurchaseDetailsCount()
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PurchaseDetails> rt = cq.from(PurchaseDetails.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally
        {
            em.close();
        }
    }

}
