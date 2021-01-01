/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.packtpub.controller;

import com.packtpub.beans.Supplier;
import com.packtpub.controller.exceptions.NonexistentEntityException;
import com.packtpub.controller.exceptions.PreexistingEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.packtpub.beans.Purchase;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Shamsuddin
 */
public class SupplierJpaController {

    public SupplierJpaController()
    {
        emf = Persistence.createEntityManagerFactory("Sales Processing SystemPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    public void create(Supplier supplier) throws PreexistingEntityException, Exception
    {
        if (supplier.getPurchaseList() == null)
        {
            supplier.setPurchaseList(new ArrayList<Purchase>());
        }
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Purchase> attachedPurchaseList = new ArrayList<Purchase>();
            for (Purchase purchaseListPurchaseToAttach : supplier.getPurchaseList())
            {
                purchaseListPurchaseToAttach = em.getReference(purchaseListPurchaseToAttach.getClass(), purchaseListPurchaseToAttach.getPurchaseNo());
                attachedPurchaseList.add(purchaseListPurchaseToAttach);
            }
            supplier.setPurchaseList(attachedPurchaseList);
            em.persist(supplier);
            for (Purchase purchaseListPurchase : supplier.getPurchaseList())
            {
                Supplier oldSupplierOfPurchaseListPurchase = purchaseListPurchase.getSupplier();
                purchaseListPurchase.setSupplier(supplier);
                purchaseListPurchase = em.merge(purchaseListPurchase);
                if (oldSupplierOfPurchaseListPurchase != null)
                {
                    oldSupplierOfPurchaseListPurchase.getPurchaseList().remove(purchaseListPurchase);
                    oldSupplierOfPurchaseListPurchase = em.merge(oldSupplierOfPurchaseListPurchase);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex)
        {
            if (findSupplier(supplier.getSupplierNo()) != null)
            {
                throw new PreexistingEntityException("Supplier " + supplier + " already exists.", ex);
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

    public void edit(Supplier supplier) throws NonexistentEntityException, Exception
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Supplier persistentSupplier = em.find(Supplier.class, supplier.getSupplierNo());
            List<Purchase> purchaseListOld = persistentSupplier.getPurchaseList();
            List<Purchase> purchaseListNew = supplier.getPurchaseList();
            List<Purchase> attachedPurchaseListNew = new ArrayList<Purchase>();
            for (Purchase purchaseListNewPurchaseToAttach : purchaseListNew)
            {
                purchaseListNewPurchaseToAttach = em.getReference(purchaseListNewPurchaseToAttach.getClass(), purchaseListNewPurchaseToAttach.getPurchaseNo());
                attachedPurchaseListNew.add(purchaseListNewPurchaseToAttach);
            }
            purchaseListNew = attachedPurchaseListNew;
            supplier.setPurchaseList(purchaseListNew);
            supplier = em.merge(supplier);
            for (Purchase purchaseListOldPurchase : purchaseListOld)
            {
                if (!purchaseListNew.contains(purchaseListOldPurchase))
                {
                    purchaseListOldPurchase.setSupplier(null);
                    purchaseListOldPurchase = em.merge(purchaseListOldPurchase);
                }
            }
            for (Purchase purchaseListNewPurchase : purchaseListNew)
            {
                if (!purchaseListOld.contains(purchaseListNewPurchase))
                {
                    Supplier oldSupplierOfPurchaseListNewPurchase = purchaseListNewPurchase.getSupplier();
                    purchaseListNewPurchase.setSupplier(supplier);
                    purchaseListNewPurchase = em.merge(purchaseListNewPurchase);
                    if (oldSupplierOfPurchaseListNewPurchase != null && !oldSupplierOfPurchaseListNewPurchase.equals(supplier))
                    {
                        oldSupplierOfPurchaseListNewPurchase.getPurchaseList().remove(purchaseListNewPurchase);
                        oldSupplierOfPurchaseListNewPurchase = em.merge(oldSupplierOfPurchaseListNewPurchase);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex)
        {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0)
            {
                Integer id = supplier.getSupplierNo();
                if (findSupplier(id) == null)
                {
                    throw new NonexistentEntityException("The supplier with id " + id + " no longer exists.");
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
            Supplier supplier;
            try
            {
                supplier = em.getReference(Supplier.class, id);
                supplier.getSupplierNo();
            } catch (EntityNotFoundException enfe)
            {
                throw new NonexistentEntityException("The supplier with id " + id + " no longer exists.", enfe);
            }
            List<Purchase> purchaseList = supplier.getPurchaseList();
            for (Purchase purchaseListPurchase : purchaseList)
            {
                purchaseListPurchase.setSupplier(null);
                purchaseListPurchase = em.merge(purchaseListPurchase);
            }
            em.remove(supplier);
            em.getTransaction().commit();
        } finally
        {
            if (em != null)
            {
                em.close();
            }
        }
    }

    public List<Supplier> findSupplierEntities()
    {
        return findSupplierEntities(true, -1, -1);
    }

    public List<Supplier> findSupplierEntities(int maxResults, int firstResult)
    {
        return findSupplierEntities(false, maxResults, firstResult);
    }

    private List<Supplier> findSupplierEntities(boolean all, int maxResults, int firstResult)
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Supplier.class));
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

    public Supplier findSupplier(Integer id)
    {
        EntityManager em = getEntityManager();
        try
        {
            return em.find(Supplier.class, id);
        } finally
        {
            em.close();
        }
    }

    public int getSupplierCount()
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Supplier> rt = cq.from(Supplier.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally
        {
            em.close();
        }
    }

}
