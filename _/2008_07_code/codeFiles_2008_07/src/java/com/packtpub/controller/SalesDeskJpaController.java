/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.packtpub.controller;

import com.packtpub.beans.SalesDesk;
import com.packtpub.controller.exceptions.IllegalOrphanException;
import com.packtpub.controller.exceptions.NonexistentEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.packtpub.beans.Branch;
import com.packtpub.beans.Sales;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Shamsuddin
 */
public class SalesDeskJpaController {

    public SalesDeskJpaController()
    {
        emf = Persistence.createEntityManagerFactory("Sales Processing SystemPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    public void create(SalesDesk salesDesk)
    {
        if (salesDesk.getSalesList() == null)
        {
            salesDesk.setSalesList(new ArrayList<Sales>());
        }
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Branch branch = salesDesk.getBranch();
            if (branch != null)
            {
                branch = em.getReference(branch.getClass(), branch.getBranchId());
                salesDesk.setBranch(branch);
            }
            List<Sales> attachedSalesList = new ArrayList<Sales>();
            for (Sales salesListSalesToAttach : salesDesk.getSalesList())
            {
                salesListSalesToAttach = em.getReference(salesListSalesToAttach.getClass(), salesListSalesToAttach.getSalesNo());
                attachedSalesList.add(salesListSalesToAttach);
            }
            salesDesk.setSalesList(attachedSalesList);
            em.persist(salesDesk);
            if (branch != null)
            {
                branch.getSalesDeskList().add(salesDesk);
                branch = em.merge(branch);
            }
            for (Sales salesListSales : salesDesk.getSalesList())
            {
                SalesDesk oldSalesDeskOfSalesListSales = salesListSales.getSalesDesk();
                salesListSales.setSalesDesk(salesDesk);
                salesListSales = em.merge(salesListSales);
                if (oldSalesDeskOfSalesListSales != null)
                {
                    oldSalesDeskOfSalesListSales.getSalesList().remove(salesListSales);
                    oldSalesDeskOfSalesListSales = em.merge(oldSalesDeskOfSalesListSales);
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

    public void edit(SalesDesk salesDesk) throws IllegalOrphanException, NonexistentEntityException, Exception
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            SalesDesk persistentSalesDesk = em.find(SalesDesk.class, salesDesk.getSalesDeskId());
            Branch branchOld = persistentSalesDesk.getBranch();
            Branch branchNew = salesDesk.getBranch();
            List<Sales> salesListOld = persistentSalesDesk.getSalesList();
            List<Sales> salesListNew = salesDesk.getSalesList();
            List<String> illegalOrphanMessages = null;
            for (Sales salesListOldSales : salesListOld)
            {
                if (!salesListNew.contains(salesListOldSales))
                {
                    if (illegalOrphanMessages == null)
                    {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Sales " + salesListOldSales + " since its salesDesk field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null)
            {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (branchNew != null)
            {
                branchNew = em.getReference(branchNew.getClass(), branchNew.getBranchId());
                salesDesk.setBranch(branchNew);
            }
            List<Sales> attachedSalesListNew = new ArrayList<Sales>();
            for (Sales salesListNewSalesToAttach : salesListNew)
            {
                salesListNewSalesToAttach = em.getReference(salesListNewSalesToAttach.getClass(), salesListNewSalesToAttach.getSalesNo());
                attachedSalesListNew.add(salesListNewSalesToAttach);
            }
            salesListNew = attachedSalesListNew;
            salesDesk.setSalesList(salesListNew);
            salesDesk = em.merge(salesDesk);
            if (branchOld != null && !branchOld.equals(branchNew))
            {
                branchOld.getSalesDeskList().remove(salesDesk);
                branchOld = em.merge(branchOld);
            }
            if (branchNew != null && !branchNew.equals(branchOld))
            {
                branchNew.getSalesDeskList().add(salesDesk);
                branchNew = em.merge(branchNew);
            }
            for (Sales salesListNewSales : salesListNew)
            {
                if (!salesListOld.contains(salesListNewSales))
                {
                    SalesDesk oldSalesDeskOfSalesListNewSales = salesListNewSales.getSalesDesk();
                    salesListNewSales.setSalesDesk(salesDesk);
                    salesListNewSales = em.merge(salesListNewSales);
                    if (oldSalesDeskOfSalesListNewSales != null && !oldSalesDeskOfSalesListNewSales.equals(salesDesk))
                    {
                        oldSalesDeskOfSalesListNewSales.getSalesList().remove(salesListNewSales);
                        oldSalesDeskOfSalesListNewSales = em.merge(oldSalesDeskOfSalesListNewSales);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex)
        {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0)
            {
                Integer id = salesDesk.getSalesDeskId();
                if (findSalesDesk(id) == null)
                {
                    throw new NonexistentEntityException("The salesDesk with id " + id + " no longer exists.");
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
            SalesDesk salesDesk;
            try
            {
                salesDesk = em.getReference(SalesDesk.class, id);
                salesDesk.getSalesDeskId();
            } catch (EntityNotFoundException enfe)
            {
                throw new NonexistentEntityException("The salesDesk with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Sales> salesListOrphanCheck = salesDesk.getSalesList();
            for (Sales salesListOrphanCheckSales : salesListOrphanCheck)
            {
                if (illegalOrphanMessages == null)
                {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This SalesDesk (" + salesDesk + ") cannot be destroyed since the Sales " + salesListOrphanCheckSales + " in its salesList field has a non-nullable salesDesk field.");
            }
            if (illegalOrphanMessages != null)
            {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Branch branch = salesDesk.getBranch();
            if (branch != null)
            {
                branch.getSalesDeskList().remove(salesDesk);
                branch = em.merge(branch);
            }
            em.remove(salesDesk);
            em.getTransaction().commit();
        } finally
        {
            if (em != null)
            {
                em.close();
            }
        }
    }

    public List<SalesDesk> findSalesDeskEntities()
    {
        return findSalesDeskEntities(true, -1, -1);
    }

    public List<SalesDesk> findSalesDeskEntities(int maxResults, int firstResult)
    {
        return findSalesDeskEntities(false, maxResults, firstResult);
    }

    private List<SalesDesk> findSalesDeskEntities(boolean all, int maxResults, int firstResult)
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SalesDesk.class));
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

    public SalesDesk findSalesDesk(Integer id)
    {
        EntityManager em = getEntityManager();
        try
        {
            return em.find(SalesDesk.class, id);
        } finally
        {
            em.close();
        }
    }

    public int getSalesDeskCount()
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SalesDesk> rt = cq.from(SalesDesk.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally
        {
            em.close();
        }
    }

}
