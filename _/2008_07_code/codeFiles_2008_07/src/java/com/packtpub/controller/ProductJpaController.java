/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.packtpub.controller;

import com.packtpub.beans.Product;
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
import com.packtpub.beans.PurchaseDetails;
import java.util.ArrayList;
import java.util.List;
import com.packtpub.beans.SalesDetails;

/**
 *
 * @author Shamsuddin
 */
public class ProductJpaController {

    public ProductJpaController()
    {
        emf = Persistence.createEntityManagerFactory("Sales Processing SystemPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    public void create(Product product) throws PreexistingEntityException, Exception
    {
        if (product.getPurchaseDetailsList() == null)
        {
            product.setPurchaseDetailsList(new ArrayList<PurchaseDetails>());
        }
        if (product.getSalesDetailsList() == null)
        {
            product.setSalesDetailsList(new ArrayList<SalesDetails>());
        }
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Stock stock = product.getStock();
            if (stock != null)
            {
                stock = em.getReference(stock.getClass(), stock.getProductCode());
                product.setStock(stock);
            }
            List<PurchaseDetails> attachedPurchaseDetailsList = new ArrayList<PurchaseDetails>();
            for (PurchaseDetails purchaseDetailsListPurchaseDetailsToAttach : product.getPurchaseDetailsList())
            {
                purchaseDetailsListPurchaseDetailsToAttach = em.getReference(purchaseDetailsListPurchaseDetailsToAttach.getClass(), purchaseDetailsListPurchaseDetailsToAttach.getPurchaseDetailsPK());
                attachedPurchaseDetailsList.add(purchaseDetailsListPurchaseDetailsToAttach);
            }
            product.setPurchaseDetailsList(attachedPurchaseDetailsList);
            List<SalesDetails> attachedSalesDetailsList = new ArrayList<SalesDetails>();
            for (SalesDetails salesDetailsListSalesDetailsToAttach : product.getSalesDetailsList())
            {
                salesDetailsListSalesDetailsToAttach = em.getReference(salesDetailsListSalesDetailsToAttach.getClass(), salesDetailsListSalesDetailsToAttach.getSalesDetailsPK());
                attachedSalesDetailsList.add(salesDetailsListSalesDetailsToAttach);
            }
            product.setSalesDetailsList(attachedSalesDetailsList);
            em.persist(product);
            if (stock != null)
            {
                Product oldProductOfStock = stock.getProduct();
                if (oldProductOfStock != null)
                {
                    oldProductOfStock.setStock(null);
                    oldProductOfStock = em.merge(oldProductOfStock);
                }
                stock.setProduct(product);
                stock = em.merge(stock);
            }
            for (PurchaseDetails purchaseDetailsListPurchaseDetails : product.getPurchaseDetailsList())
            {
                Product oldProductOfPurchaseDetailsListPurchaseDetails = purchaseDetailsListPurchaseDetails.getProduct();
                purchaseDetailsListPurchaseDetails.setProduct(product);
                purchaseDetailsListPurchaseDetails = em.merge(purchaseDetailsListPurchaseDetails);
                if (oldProductOfPurchaseDetailsListPurchaseDetails != null)
                {
                    oldProductOfPurchaseDetailsListPurchaseDetails.getPurchaseDetailsList().remove(purchaseDetailsListPurchaseDetails);
                    oldProductOfPurchaseDetailsListPurchaseDetails = em.merge(oldProductOfPurchaseDetailsListPurchaseDetails);
                }
            }
            for (SalesDetails salesDetailsListSalesDetails : product.getSalesDetailsList())
            {
                Product oldProductOfSalesDetailsListSalesDetails = salesDetailsListSalesDetails.getProduct();
                salesDetailsListSalesDetails.setProduct(product);
                salesDetailsListSalesDetails = em.merge(salesDetailsListSalesDetails);
                if (oldProductOfSalesDetailsListSalesDetails != null)
                {
                    oldProductOfSalesDetailsListSalesDetails.getSalesDetailsList().remove(salesDetailsListSalesDetails);
                    oldProductOfSalesDetailsListSalesDetails = em.merge(oldProductOfSalesDetailsListSalesDetails);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex)
        {
            if (findProduct(product.getProductCode()) != null)
            {
                throw new PreexistingEntityException("Product " + product + " already exists.", ex);
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

    public void edit(Product product) throws IllegalOrphanException, NonexistentEntityException, Exception
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Product persistentProduct = em.find(Product.class, product.getProductCode());
            Stock stockOld = persistentProduct.getStock();
            Stock stockNew = product.getStock();
            List<PurchaseDetails> purchaseDetailsListOld = persistentProduct.getPurchaseDetailsList();
            List<PurchaseDetails> purchaseDetailsListNew = product.getPurchaseDetailsList();
            List<SalesDetails> salesDetailsListOld = persistentProduct.getSalesDetailsList();
            List<SalesDetails> salesDetailsListNew = product.getSalesDetailsList();
            List<String> illegalOrphanMessages = null;
            if (stockOld != null && !stockOld.equals(stockNew))
            {
                if (illegalOrphanMessages == null)
                {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Stock " + stockOld + " since its product field is not nullable.");
            }
            for (PurchaseDetails purchaseDetailsListOldPurchaseDetails : purchaseDetailsListOld)
            {
                if (!purchaseDetailsListNew.contains(purchaseDetailsListOldPurchaseDetails))
                {
                    if (illegalOrphanMessages == null)
                    {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PurchaseDetails " + purchaseDetailsListOldPurchaseDetails + " since its product field is not nullable.");
                }
            }
            for (SalesDetails salesDetailsListOldSalesDetails : salesDetailsListOld)
            {
                if (!salesDetailsListNew.contains(salesDetailsListOldSalesDetails))
                {
                    if (illegalOrphanMessages == null)
                    {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain SalesDetails " + salesDetailsListOldSalesDetails + " since its product field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null)
            {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (stockNew != null)
            {
                stockNew = em.getReference(stockNew.getClass(), stockNew.getProductCode());
                product.setStock(stockNew);
            }
            List<PurchaseDetails> attachedPurchaseDetailsListNew = new ArrayList<PurchaseDetails>();
            for (PurchaseDetails purchaseDetailsListNewPurchaseDetailsToAttach : purchaseDetailsListNew)
            {
                purchaseDetailsListNewPurchaseDetailsToAttach = em.getReference(purchaseDetailsListNewPurchaseDetailsToAttach.getClass(), purchaseDetailsListNewPurchaseDetailsToAttach.getPurchaseDetailsPK());
                attachedPurchaseDetailsListNew.add(purchaseDetailsListNewPurchaseDetailsToAttach);
            }
            purchaseDetailsListNew = attachedPurchaseDetailsListNew;
            product.setPurchaseDetailsList(purchaseDetailsListNew);
            List<SalesDetails> attachedSalesDetailsListNew = new ArrayList<SalesDetails>();
            for (SalesDetails salesDetailsListNewSalesDetailsToAttach : salesDetailsListNew)
            {
                salesDetailsListNewSalesDetailsToAttach = em.getReference(salesDetailsListNewSalesDetailsToAttach.getClass(), salesDetailsListNewSalesDetailsToAttach.getSalesDetailsPK());
                attachedSalesDetailsListNew.add(salesDetailsListNewSalesDetailsToAttach);
            }
            salesDetailsListNew = attachedSalesDetailsListNew;
            product.setSalesDetailsList(salesDetailsListNew);
            product = em.merge(product);
            if (stockNew != null && !stockNew.equals(stockOld))
            {
                Product oldProductOfStock = stockNew.getProduct();
                if (oldProductOfStock != null)
                {
                    oldProductOfStock.setStock(null);
                    oldProductOfStock = em.merge(oldProductOfStock);
                }
                stockNew.setProduct(product);
                stockNew = em.merge(stockNew);
            }
            for (PurchaseDetails purchaseDetailsListNewPurchaseDetails : purchaseDetailsListNew)
            {
                if (!purchaseDetailsListOld.contains(purchaseDetailsListNewPurchaseDetails))
                {
                    Product oldProductOfPurchaseDetailsListNewPurchaseDetails = purchaseDetailsListNewPurchaseDetails.getProduct();
                    purchaseDetailsListNewPurchaseDetails.setProduct(product);
                    purchaseDetailsListNewPurchaseDetails = em.merge(purchaseDetailsListNewPurchaseDetails);
                    if (oldProductOfPurchaseDetailsListNewPurchaseDetails != null && !oldProductOfPurchaseDetailsListNewPurchaseDetails.equals(product))
                    {
                        oldProductOfPurchaseDetailsListNewPurchaseDetails.getPurchaseDetailsList().remove(purchaseDetailsListNewPurchaseDetails);
                        oldProductOfPurchaseDetailsListNewPurchaseDetails = em.merge(oldProductOfPurchaseDetailsListNewPurchaseDetails);
                    }
                }
            }
            for (SalesDetails salesDetailsListNewSalesDetails : salesDetailsListNew)
            {
                if (!salesDetailsListOld.contains(salesDetailsListNewSalesDetails))
                {
                    Product oldProductOfSalesDetailsListNewSalesDetails = salesDetailsListNewSalesDetails.getProduct();
                    salesDetailsListNewSalesDetails.setProduct(product);
                    salesDetailsListNewSalesDetails = em.merge(salesDetailsListNewSalesDetails);
                    if (oldProductOfSalesDetailsListNewSalesDetails != null && !oldProductOfSalesDetailsListNewSalesDetails.equals(product))
                    {
                        oldProductOfSalesDetailsListNewSalesDetails.getSalesDetailsList().remove(salesDetailsListNewSalesDetails);
                        oldProductOfSalesDetailsListNewSalesDetails = em.merge(oldProductOfSalesDetailsListNewSalesDetails);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex)
        {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0)
            {
                Integer id = product.getProductCode();
                if (findProduct(id) == null)
                {
                    throw new NonexistentEntityException("The product with id " + id + " no longer exists.");
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
            Product product;
            try
            {
                product = em.getReference(Product.class, id);
                product.getProductCode();
            } catch (EntityNotFoundException enfe)
            {
                throw new NonexistentEntityException("The product with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Stock stockOrphanCheck = product.getStock();
            if (stockOrphanCheck != null)
            {
                if (illegalOrphanMessages == null)
                {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Product (" + product + ") cannot be destroyed since the Stock " + stockOrphanCheck + " in its stock field has a non-nullable product field.");
            }
            List<PurchaseDetails> purchaseDetailsListOrphanCheck = product.getPurchaseDetailsList();
            for (PurchaseDetails purchaseDetailsListOrphanCheckPurchaseDetails : purchaseDetailsListOrphanCheck)
            {
                if (illegalOrphanMessages == null)
                {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Product (" + product + ") cannot be destroyed since the PurchaseDetails " + purchaseDetailsListOrphanCheckPurchaseDetails + " in its purchaseDetailsList field has a non-nullable product field.");
            }
            List<SalesDetails> salesDetailsListOrphanCheck = product.getSalesDetailsList();
            for (SalesDetails salesDetailsListOrphanCheckSalesDetails : salesDetailsListOrphanCheck)
            {
                if (illegalOrphanMessages == null)
                {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Product (" + product + ") cannot be destroyed since the SalesDetails " + salesDetailsListOrphanCheckSalesDetails + " in its salesDetailsList field has a non-nullable product field.");
            }
            if (illegalOrphanMessages != null)
            {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(product);
            em.getTransaction().commit();
        } finally
        {
            if (em != null)
            {
                em.close();
            }
        }
    }

    public List<Product> findProductEntities()
    {
        return findProductEntities(true, -1, -1);
    }

    public List<Product> findProductEntities(int maxResults, int firstResult)
    {
        return findProductEntities(false, maxResults, firstResult);
    }

    private List<Product> findProductEntities(boolean all, int maxResults, int firstResult)
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Product.class));
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

    public Product findProduct(Integer id)
    {
        EntityManager em = getEntityManager();
        try
        {
            return em.find(Product.class, id);
        } finally
        {
            em.close();
        }
    }

    public int getProductCount()
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Product> rt = cq.from(Product.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally
        {
            em.close();
        }
    }

}
