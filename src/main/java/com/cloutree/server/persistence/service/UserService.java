
/**
 * Copyright 2013 Marc Schachtel, Germany
 */

package com.cloutree.server.persistence.service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.cloutree.server.persistence.entity.User;
import com.cloutree.server.persistence.service.log.DBLogger;
import com.cloutree.server.session.ClouTreeSession;

/**
 * UserService
 *
 * @author marc
 *
 * Since 11.07.2013
 */
public class UserService {
    
    static Logger log = Logger.getLogger(UserService.class.getName());
    
    private EntityManagerFactory emf;
    
    public UserService(){
	
	this.emf =  Persistence.createEntityManagerFactory("ClouTree");
    }
    
    public User getUser(Integer id) {
	
        User result = null;
        EntityManager mgr = this.emf.createEntityManager();
        try {
            result = mgr.find(User.class, id);
        } finally {
            mgr.close();
        }
        return result;
    }
    
    public User getUserByUserName(String username, String tenant) {
	
        User result = null;
        EntityManager mgr = this.emf.createEntityManager();
        Query query = mgr.createQuery("SELECT u FROM " + User.class.getName() + " u WHERE u.username = :username AND u.tenant.name = :tenant");
        query.setParameter("username", username);
        query.setParameter("tenant", tenant);
        
        Object resultObject = null;
        
        try {
        	resultObject = query.getSingleResult();
        } catch (NoResultException e) {
        	return null;
        }
        
        try {
            result = (User)resultObject;
        } catch(NoResultException e) {
            log.log(Level.SEVERE, e.getMessage());
            return null;
        } finally {
            mgr.close();
        }
        return result;
    }
    
    @SuppressWarnings("unchecked")
	public List<User> getAllUsers() {
	
	List<User> result = null;
        EntityManager mgr = this.emf.createEntityManager();
        Query query = mgr.createQuery("SELECT u FROM " + User.class.getName() + " u WHERE u.tenant.name = :tenant");
        query.setParameter("tenant", ClouTreeSession.getTenantName());
        try {
            result = query.getResultList();
        } catch(NoResultException e) {
            log.log(Level.SEVERE, e.getMessage());
            e.printStackTrace();
            return null;
        } finally {
            mgr.close();
        }
        return result;
    }
    
    public boolean storeUser(User user) {
	
    	User exists = this.getUserByUserName(user.getUsername(), user.getTenant().getName());
    	if(exists != null) {
    		return false;
    	}
    	
        EntityManager mgr = this.emf.createEntityManager();
        try {
            mgr.getTransaction().begin();
            mgr.persist(user);
            mgr.getTransaction().commit();
        } catch(Exception e) {
            log.log(Level.SEVERE, e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            mgr.close();
            
            DBLogger.logAccess("STORE_USER", user.getUsername());
            
        }
        return true;
    }
    
    public boolean updateUser(User user) {
	
        EntityManager mgr = this.emf.createEntityManager();
        try {
            mgr.getTransaction().begin();
            mgr.merge(user);
            mgr.getTransaction().commit();
        } catch(Exception e) {
            log.log(Level.SEVERE, e.getMessage());
            return false;
        } finally {
            mgr.close();
            
            DBLogger.logAccess("UPDATE_USER", user.getUsername());
            
        }
        return true;
    }
        
    
}
