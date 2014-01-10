
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

import com.cloutree.server.persistence.entity.Apihost;
import com.cloutree.server.persistence.entity.Instance;
import com.cloutree.server.persistence.service.log.DBLogger;

/**
 * ModelService
 *
 * @author marc
 *
 * Since 11.07.2013
 */
public class ApihostService {
    
    static Logger log = Logger.getLogger(ApihostService.class.getName());
    
    private EntityManagerFactory emf;
    
    Instance instance;
    
    public ApihostService(Instance instance){
	
    	this.emf =  Persistence.createEntityManagerFactory("ClouTree");
    	this.instance = instance;
    	
    }
    
    public Apihost getApihost(String name) {
	
        Apihost result = null;
        EntityManager mgr = this.emf.createEntityManager();
        try {
            result = mgr.find(Apihost.class, name);
        } finally {
            mgr.close();
        }
        
        if(result != null && !result.getInstance().getId().equals(this.instance.getId())){
        	return null;
        }
        
        return result;
    }
    
    public boolean storeApihost(Apihost apihost, boolean updateIfExists) {
	
    	if(!apihost.getInstance().getId().equals(this.instance.getId())) {
    		log.log(Level.WARNING, "Not allowed to save Apihost with instance" + apihost.getInstance().getName());
    	}
    	
    	Apihost exists = this.getApihost(apihost.getName());
    	if(exists != null && !updateIfExists) {
    		return false;
    	} else if (exists != null && !updateIfExists) {
    		return this.updateApihost(apihost);
    	}
    	
        EntityManager mgr = this.emf.createEntityManager();
        try {
            mgr.getTransaction().begin();
            mgr.persist(apihost);
            mgr.getTransaction().commit();
        } catch(Exception e) {
            log.log(Level.SEVERE, e.getMessage());
            return false;
        } finally {
            mgr.close();
            DBLogger.logAccess("STORE_APIHOST", apihost.getName());
        }
        return true;
    }
    
    public boolean updateApihost(Apihost apihost) {
	
    	if(!apihost.getInstance().getId().equals(this.instance.getId())) {
    		log.log(Level.WARNING, "Not allowed to save Apihost with instance" + apihost.getInstance().getName());
    	}
    	
        EntityManager mgr = this.emf.createEntityManager();
        try {
            mgr.getTransaction().begin();
            mgr.merge(apihost);
            mgr.getTransaction().commit();
        } catch(Exception e) {
            log.log(Level.SEVERE, e.getMessage());
            return false;
        } finally {
            mgr.close();
            DBLogger.logAccess("UPDATE_APIHOST", apihost.getName());
        }
        return true;
    }
    
    public boolean switchHostStatus(Apihost apihost) {
    	
    	if(!apihost.getInstance().getId().equals(this.instance.getId())) {
    		log.log(Level.WARNING, "Not allowed to save Apihost with instance" + apihost.getInstance().getName());
    	}
    	
    	if(apihost.getStatus() > 0) {
    		apihost.setStatus(0);
    	} else {
    		apihost.setStatus(1);
    	}
    	
        EntityManager mgr = this.emf.createEntityManager();
        try {
            mgr.getTransaction().begin();
            mgr.merge(apihost);
            mgr.getTransaction().commit();
        } catch(Exception e) {
            log.log(Level.SEVERE, e.getMessage());
            return false;
        } finally {
            mgr.close();
        }
        return true;
    }
    
    public boolean deleteApihost(Apihost apihost) {
	
    	if(!apihost.getInstance().getId().equals(this.instance.getId())) {
    		log.log(Level.WARNING, "Not allowed to delete Apihost with instance" + apihost.getInstance().getName());
    	}
    	
        EntityManager mgr = this.emf.createEntityManager();
        try {
        	mgr.getTransaction().begin();
            Apihost host = mgr.merge(apihost);
            mgr.remove(host);
            mgr.getTransaction().commit();
        } catch(Exception e) {
            log.log(Level.SEVERE, e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            mgr.close();
            
            DBLogger.logAccess("DELETE_APIHOST", apihost.getName());
            
        }
        return true;
    }
    
    @SuppressWarnings("unchecked")
	public List<Apihost> getAllAvailableApihosts() {
	
        List<Apihost> result = null;
        EntityManager mgr = this.emf.createEntityManager();
        Query query = mgr.createQuery("SELECT ah FROM " + Apihost.class.getName() + " ah WHERE ah.instance = :instance");
        query.setParameter("instance", this.instance);
        try {
            result = query.getResultList();
        } catch(NoResultException e) {
            log.log(Level.SEVERE, e.getMessage());
            return null;
        } finally {
            mgr.close();
        }
        return result;
    }
    
}
