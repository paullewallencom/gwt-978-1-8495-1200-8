/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.packtpub.controller;

import com.packtpub.beans.Employee;
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
import com.packtpub.beans.Users;
import java.util.ArrayList;
import java.util.List;
import com.packtpub.beans.Sales;

/**
 *
 * @author Shamsuddin
 */
public class EmployeeJpaController {

    public EmployeeJpaController()
    {
        emf = Persistence.createEntityManagerFactory("Sales Processing SystemPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    public void create(Employee employee)
    {
        if (employee.getUsersList() == null)
        {
            employee.setUsersList(new ArrayList<Users>());
        }
        if (employee.getSalesList() == null)
        {
            employee.setSalesList(new ArrayList<Sales>());
        }
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Branch branch = employee.getBranch();
            if (branch != null)
            {
                branch = em.getReference(branch.getClass(), branch.getBranchId());
                employee.setBranch(branch);
            }
            List<Users> attachedUsersList = new ArrayList<Users>();
            for (Users usersListUsersToAttach : employee.getUsersList())
            {
                usersListUsersToAttach = em.getReference(usersListUsersToAttach.getClass(), usersListUsersToAttach.getUserName());
                attachedUsersList.add(usersListUsersToAttach);
            }
            employee.setUsersList(attachedUsersList);
            List<Sales> attachedSalesList = new ArrayList<Sales>();
            for (Sales salesListSalesToAttach : employee.getSalesList())
            {
                salesListSalesToAttach = em.getReference(salesListSalesToAttach.getClass(), salesListSalesToAttach.getSalesNo());
                attachedSalesList.add(salesListSalesToAttach);
            }
            employee.setSalesList(attachedSalesList);
            em.persist(employee);
            if (branch != null)
            {
                branch.getEmployeeList().add(employee);
                branch = em.merge(branch);
            }
            for (Users usersListUsers : employee.getUsersList())
            {
                Employee oldEmployeeOfUsersListUsers = usersListUsers.getEmployee();
                usersListUsers.setEmployee(employee);
                usersListUsers = em.merge(usersListUsers);
                if (oldEmployeeOfUsersListUsers != null)
                {
                    oldEmployeeOfUsersListUsers.getUsersList().remove(usersListUsers);
                    oldEmployeeOfUsersListUsers = em.merge(oldEmployeeOfUsersListUsers);
                }
            }
            for (Sales salesListSales : employee.getSalesList())
            {
                Employee oldEmployeeOfSalesListSales = salesListSales.getEmployee();
                salesListSales.setEmployee(employee);
                salesListSales = em.merge(salesListSales);
                if (oldEmployeeOfSalesListSales != null)
                {
                    oldEmployeeOfSalesListSales.getSalesList().remove(salesListSales);
                    oldEmployeeOfSalesListSales = em.merge(oldEmployeeOfSalesListSales);
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

    public void edit(Employee employee) throws IllegalOrphanException, NonexistentEntityException, Exception
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Employee persistentEmployee = em.find(Employee.class, employee.getEmployeeId());
            Branch branchOld = persistentEmployee.getBranch();
            Branch branchNew = employee.getBranch();
            List<Users> usersListOld = persistentEmployee.getUsersList();
            List<Users> usersListNew = employee.getUsersList();
            List<Sales> salesListOld = persistentEmployee.getSalesList();
            List<Sales> salesListNew = employee.getSalesList();
            List<String> illegalOrphanMessages = null;
            for (Users usersListOldUsers : usersListOld)
            {
                if (!usersListNew.contains(usersListOldUsers))
                {
                    if (illegalOrphanMessages == null)
                    {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Users " + usersListOldUsers + " since its employee field is not nullable.");
                }
            }
            for (Sales salesListOldSales : salesListOld)
            {
                if (!salesListNew.contains(salesListOldSales))
                {
                    if (illegalOrphanMessages == null)
                    {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Sales " + salesListOldSales + " since its employee field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null)
            {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (branchNew != null)
            {
                branchNew = em.getReference(branchNew.getClass(), branchNew.getBranchId());
                employee.setBranch(branchNew);
            }
            List<Users> attachedUsersListNew = new ArrayList<Users>();
            for (Users usersListNewUsersToAttach : usersListNew)
            {
                usersListNewUsersToAttach = em.getReference(usersListNewUsersToAttach.getClass(), usersListNewUsersToAttach.getUserName());
                attachedUsersListNew.add(usersListNewUsersToAttach);
            }
            usersListNew = attachedUsersListNew;
            employee.setUsersList(usersListNew);
            List<Sales> attachedSalesListNew = new ArrayList<Sales>();
            for (Sales salesListNewSalesToAttach : salesListNew)
            {
                salesListNewSalesToAttach = em.getReference(salesListNewSalesToAttach.getClass(), salesListNewSalesToAttach.getSalesNo());
                attachedSalesListNew.add(salesListNewSalesToAttach);
            }
            salesListNew = attachedSalesListNew;
            employee.setSalesList(salesListNew);
            employee = em.merge(employee);
            if (branchOld != null && !branchOld.equals(branchNew))
            {
                branchOld.getEmployeeList().remove(employee);
                branchOld = em.merge(branchOld);
            }
            if (branchNew != null && !branchNew.equals(branchOld))
            {
                branchNew.getEmployeeList().add(employee);
                branchNew = em.merge(branchNew);
            }
            for (Users usersListNewUsers : usersListNew)
            {
                if (!usersListOld.contains(usersListNewUsers))
                {
                    Employee oldEmployeeOfUsersListNewUsers = usersListNewUsers.getEmployee();
                    usersListNewUsers.setEmployee(employee);
                    usersListNewUsers = em.merge(usersListNewUsers);
                    if (oldEmployeeOfUsersListNewUsers != null && !oldEmployeeOfUsersListNewUsers.equals(employee))
                    {
                        oldEmployeeOfUsersListNewUsers.getUsersList().remove(usersListNewUsers);
                        oldEmployeeOfUsersListNewUsers = em.merge(oldEmployeeOfUsersListNewUsers);
                    }
                }
            }
            for (Sales salesListNewSales : salesListNew)
            {
                if (!salesListOld.contains(salesListNewSales))
                {
                    Employee oldEmployeeOfSalesListNewSales = salesListNewSales.getEmployee();
                    salesListNewSales.setEmployee(employee);
                    salesListNewSales = em.merge(salesListNewSales);
                    if (oldEmployeeOfSalesListNewSales != null && !oldEmployeeOfSalesListNewSales.equals(employee))
                    {
                        oldEmployeeOfSalesListNewSales.getSalesList().remove(salesListNewSales);
                        oldEmployeeOfSalesListNewSales = em.merge(oldEmployeeOfSalesListNewSales);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex)
        {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0)
            {
                Integer id = employee.getEmployeeId();
                if (findEmployee(id) == null)
                {
                    throw new NonexistentEntityException("The employee with id " + id + " no longer exists.");
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
            Employee employee;
            try
            {
                employee = em.getReference(Employee.class, id);
                employee.getEmployeeId();
            } catch (EntityNotFoundException enfe)
            {
                throw new NonexistentEntityException("The employee with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Users> usersListOrphanCheck = employee.getUsersList();
            for (Users usersListOrphanCheckUsers : usersListOrphanCheck)
            {
                if (illegalOrphanMessages == null)
                {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Employee (" + employee + ") cannot be destroyed since the Users " + usersListOrphanCheckUsers + " in its usersList field has a non-nullable employee field.");
            }
            List<Sales> salesListOrphanCheck = employee.getSalesList();
            for (Sales salesListOrphanCheckSales : salesListOrphanCheck)
            {
                if (illegalOrphanMessages == null)
                {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Employee (" + employee + ") cannot be destroyed since the Sales " + salesListOrphanCheckSales + " in its salesList field has a non-nullable employee field.");
            }
            if (illegalOrphanMessages != null)
            {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Branch branch = employee.getBranch();
            if (branch != null)
            {
                branch.getEmployeeList().remove(employee);
                branch = em.merge(branch);
            }
            em.remove(employee);
            em.getTransaction().commit();
        } finally
        {
            if (em != null)
            {
                em.close();
            }
        }
    }

    public List<Employee> findEmployeeEntities()
    {
        return findEmployeeEntities(true, -1, -1);
    }

    public List<Employee> findEmployeeEntities(int maxResults, int firstResult)
    {
        return findEmployeeEntities(false, maxResults, firstResult);
    }

    private List<Employee> findEmployeeEntities(boolean all, int maxResults, int firstResult)
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Employee.class));
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

    public Employee findEmployee(Integer id)
    {
        EntityManager em = getEntityManager();
        try
        {
            return em.find(Employee.class, id);
        } finally
        {
            em.close();
        }
    }

    public int getEmployeeCount()
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Employee> rt = cq.from(Employee.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally
        {
            em.close();
        }
    }

}
