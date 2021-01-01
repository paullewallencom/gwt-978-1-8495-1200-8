/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.packtpub.controller;

import com.packtpub.beans.Purchase;
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
import com.packtpub.beans.Supplier;
import com.packtpub.beans.PurchaseDetails;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Shamsuddin
 */
public class PurchaseJpaController {

    public PurchaseJpaController()
    {
        emf = Persistence.createEntityManagerFactory("Sales Processing SystemPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    public void create(Purchase purchase) throws PreexistingEntityException, Exception
    {
        if (purchase.getPurchaseDetailsList() == null)
        {
            purchase.setPurchaseDetailsList(new ArrayList<PurchaseDetails>());
        }
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Supplier supplier = purchase.getSupplier();
            if (supplier != null)
            {
                supplier = em.getReference(supplier.getClass(), supplier.getSupplierNo());
                purchase.setSupplier(supplier);
            }
            List<PurchaseDetails> attachedPurchaseDetailsList = new ArrayList<PurchaseDetails>();
            for (PurchaseDetails purchaseDetailsListPurchaseDetailsToAttach : purchase.getPurchaseDetailsList())
            {
                purchaseDetailsListPurchaseDetailsToAttach = em.getReference(purchaseDetailsListPurchaseDetailsToAttach.getClass(), purchaseDetailsListPurchaseDetailsToAttach.getPurchaseDetailsPK());
                attachedPurchaseDetailsList.add(purchaseDetailsListPurchaseDetailsToAttach);
            }
            purchase.setPurchaseDetailsList(attachedPurchaseDetailsList);
            em.persist(purchase);
            if (supplier != null)
            {
                supplier.getPurchaseList().add(purchase);
                supplier = em.merge(supplier);
            }
            for (PurchaseDetails purchaseDetailsListPurchaseDetails : purchase.getPurchaseDetailsList())
            {
                Purchase oldPurchaseOfPurchaseDetailsListPurchaseDetails = purchaseDetailsListPurchaseDetails.getPurchase();
                purchaseDetailsListPurchaseDetails.setPurchase(purchase);
                purchaseDetailsListPurchaseDetails = em.merge(purchaseDetailsListPurchaseDetails);
                if (oldPurchaseOfPurchaseDetailsListPurchaseDetails != null)
                {
                    oldPurchaseOfPurchaseDetailsListPurchaseDetails.getPurchaseDetailsList().remove(purchaseDetailsListPurchaseDetails);
                    oldPurchaseOfPurchaseDetailsListPurchaseDetails = em.merge(oldPurchaseOfPurchaseDetailsListPurchaseDetails);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex)
        {
            if (findPurchase(purchase.getPurchaseNo()) != null)
            {
                throw new PreexistingEntityException("Purchase " + purchase + " already exists.", ex);
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

    public void edit(Purchase purchase) throws IllegalOrphanException, NonexistentEntityException, Exception
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Purchase persistentPurchase = em.find(Purchase.class, purchase.getPurchaseNo());
            Supplier supplierOld = persistentPurchase.getSupplier();
            Supplier supplierNew = purchase.getSupplier();
            List<PurchaseDetails> purchaseDetailsListOld = persistentPurchase.getPurchaseDetailsList();
            List<PurchaseDetails> purchaseDetailsListNew = purchase.getPurchaseDetailsList();
            List<String> illegalOrphanMessages = null;
            for (PurchaseDetails purchaseDetailsListOldPurchaseDetails : purchaseDetailsListOld)
            {
                if (!purchaseDetailsListNew.contains(purchaseDetailsListOldPurchaseDetails))
                {
                    if (illegalOrphanMessages == null)
                    {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PurchaseDetails " + purchaseDetailsListOldPurchaseDetails + " since its purchase field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null)
            {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (supplierNew != null)
            {
                supplierNew = em.getReference(supplierNew.getClass(), supplierNew.getSupplierNo());
                purchase.setSupplier(supplierNew);
            }
            List<PurchaseDetails> attachedPurchaseDetailsListNew = new ArrayList<PurchaseDetails>();
            for (PurchaseDetails purchaseDetailsListNewPurchaseDetailsToAttach : purchaseDetailsListNew)
            {
                purchaseDetailsListNewPurchaseDetailsToAttach = em.getReference(purchaseDetailsListNewPurchaseDetailsToAttach.getClass(), purchaseDetailsListNewPurchaseDetailsToAttach.getPurchaseDetailsPK());
                attachedPurchaseDetailsListNew.add(purchaseDetailsListNewPurchaseDetailsToAttach);
            }
            purchaseDetailsListNew = attachedPurchaseDetailsListNew;
            purchase.setPurchaseDetailsList(purchaseDetailsListNew);
            purchase = em.merge(purchase);
            if (supplierOld != null && !supplierOld.equals(supplierNew))
            {
                supplierOld.getPurchaseList().remove(purchase);
                supplierOld = em.merge(supplierOld);
            }
            if (supplierNew != null && !supplierNew.equals(supplierOld))
            {
                supplierNew.getPurchaseList().add(purchase);
                supplierNew = em.merge(supplierNew);
            }
            for (PurchaseDetails purchaseDetailsListNewPurchaseDetails : purchaseDetailsListNew)
            {
                if (!purchaseDetailsListOld.contains(purchaseDetailsListNewPurchaseDetails))
                {
                    Purchase oldPurchaseOfPurchaseDetailsListNewPurchaseDetails = purchaseDetailsListNewPurchaseDetails.getPurchase();
                    purchaseDetailsListNewPurchaseDetails.setPurchase(purchase);
                    purchaseDetailsListNewPurchaseDetails = em.merge(purchaseDetailsListNewPurchaseDetails);
                    if (oldPurchaseOfPurchaseDetailsListNewPurchaseDetails != null && !oldPurchaseOfPurchaseDetailsListNewPurchaseDetails.equals(purchase))
                    {
                        oldPurchaseOfPurchaseDetailsListNewPurchaseDetails.getPurchaseDetailsList().remove(purchaseDetailsListNewPurchaseDetails);
                        oldPurchaseOfPurchaseDetailsListNewPurchaseDetails = em.merge(oldPurchaseOfPurchaseDetailsListNewPurchaseDetails);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex)
        {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0)
            {
                Integer id = purchase.getPurchaseNo();
                if (findPurchase(id) == null)
                {
                    throw new NonexistentEntityException("The purchase with id " + id + " no longer exists.");
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
            Purchase purchase;
            try
            {
                purchase = em.getReference(Purchase.class, id);
                purchase.getPurchaseNo();
            } catch (EntityNotFoundException enfe)
            {
                throw new NonexistentEntityException("The purchase with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<PurchaseDetails> purchaseDetailsListOrphanCheck = purchase.getPurchaseDetailsList();
            for (PurchaseDetails purchaseDetailsListOrphanCheckPurchaseDetails : purchaseDetailsListOrphanCheck)
            {
                if (illegalOrphanMessages == null)
                {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Purchase (" + purchase + ") cannot be destroyed since the PurchaseDetails " + purchaseDetailsListOrphanCheckPurchaseDetails + " in its purchaseDetailsList field has a non-nullable purchase field.");
            }
            if (illegalOrphanMessages != null)
            {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Supplier supplier = purchase.getSupplier();
            if (supplier != null)
            {
                supplier.getPurchaseList().remove(purchase);
                supplier = em.merge(supplier);
            }
            em.remove(purchase);
            em.getTransaction().commit();
        } finally
        {
            if (em != null)
            {
                em.close();
            }
        }
    }

    public List<Purchase> findPurchaseEntities()
    {
        return findPurchaseEntities(true, -1, -1);
    }

    public List<Purchase> findPurchaseEntities(int maxResults, int firstResult)
    {
        return findPurchaseEntities(false, maxResults, firstResult);
    }

    private List<Purchase> findPurchaseEntities(boolean all, int maxResults, int firstResult)
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Purchase.class));
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

    public Purchase findPurchase(Integer id)
    {
        EntityManager em = getEntityManager();
        try
        {
            return em.find(Purchase.class, id);
        } finally
        {
            em.close();
        }
    }

    public int getPurchaseCount()
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Purchase> rt = cq.from(Purchase.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally
        {
            em.close();
        }
    }

}
