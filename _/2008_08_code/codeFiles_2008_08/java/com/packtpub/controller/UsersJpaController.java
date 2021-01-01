/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.packtpub.controller;

import com.packtpub.beans.Users;
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
import com.packtpub.beans.Employee;

/**
 *
 * @author Shamsuddin
 */
public class UsersJpaController {

    public UsersJpaController()
    {
        emf = Persistence.createEntityManagerFactory("Sales Processing SystemPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    public void create(Users users) throws PreexistingEntityException, Exception
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Employee employee = users.getEmployee();
            if (employee != null)
            {
                employee = em.getReference(employee.getClass(), employee.getEmployeeId());
                users.setEmployee(employee);
            }
            em.persist(users);
            if (employee != null)
            {
                employee.getUsersList().add(users);
                employee = em.merge(employee);
            }
            em.getTransaction().commit();
        } catch (Exception ex)
        {
            if (findUsers(users.getUserName()) != null)
            {
                throw new PreexistingEntityException("Users " + users + " already exists.", ex);
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

    public void edit(Users users) throws NonexistentEntityException, Exception
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Users persistentUsers = em.find(Users.class, users.getUserName());
            Employee employeeOld = persistentUsers.getEmployee();
            Employee employeeNew = users.getEmployee();
            if (employeeNew != null)
            {
                employeeNew = em.getReference(employeeNew.getClass(), employeeNew.getEmployeeId());
                users.setEmployee(employeeNew);
            }
            users = em.merge(users);
            if (employeeOld != null && !employeeOld.equals(employeeNew))
            {
                employeeOld.getUsersList().remove(users);
                employeeOld = em.merge(employeeOld);
            }
            if (employeeNew != null && !employeeNew.equals(employeeOld))
            {
                employeeNew.getUsersList().add(users);
                employeeNew = em.merge(employeeNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex)
        {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0)
            {
                String id = users.getUserName();
                if (findUsers(id) == null)
                {
                    throw new NonexistentEntityException("The users with id " + id + " no longer exists.");
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

    public void destroy(String id) throws NonexistentEntityException
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Users users;
            try
            {
                users = em.getReference(Users.class, id);
                users.getUserName();
            } catch (EntityNotFoundException enfe)
            {
                throw new NonexistentEntityException("The users with id " + id + " no longer exists.", enfe);
            }
            Employee employee = users.getEmployee();
            if (employee != null)
            {
                employee.getUsersList().remove(users);
                employee = em.merge(employee);
            }
            em.remove(users);
            em.getTransaction().commit();
        } finally
        {
            if (em != null)
            {
                em.close();
            }
        }
    }

    public List<Users> findUsersEntities()
    {
        return findUsersEntities(true, -1, -1);
    }

    public List<Users> findUsersEntities(int maxResults, int firstResult)
    {
        return findUsersEntities(false, maxResults, firstResult);
    }

    private List<Users> findUsersEntities(boolean all, int maxResults, int firstResult)
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Users.class));
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

    public Users findUsers(String id)
    {
        EntityManager em = getEntityManager();
        try
        {
            return em.find(Users.class, id);
        } finally
        {
            em.close();
        }
    }

    public int getUsersCount()
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Users> rt = cq.from(Users.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally
        {
            em.close();
        }
    }

}
