
/**
 * Copyright 2013 Marc Schachtel, Germany
 */

package com.cloutree.server.persistence.service.log;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.cloutree.server.persistence.entity.User;
import com.cloutree.server.persistence.entity.log.Access;
import com.cloutree.server.persistence.entity.log.ApiCall;

/**
 * DBLogService
 *
 * @author marc
 *
 * Since 09.09.2013
 */
public class DBLogService {
    
    static Logger log = Logger.getLogger(DBLogService.class.getName());
    
    private EntityManagerFactory emf;
    
    public DBLogService(){
	
	this.emf =  Persistence.createEntityManagerFactory("ClouTree");
    }
    
    public void logAccess(User user, String action, String details) {
	
        EntityManager mgr = this.emf.createEntityManager();
        try {
            
            Access access = new Access();
            access.setUser(user.getUsername());
            access.setAction(action);
            access.setDetails(details);
            
            mgr.getTransaction().begin();
            mgr.persist(access);
            mgr.getTransaction().commit();
            
        } catch(Exception e) {
            log.log(Level.SEVERE, e.getMessage());
            e.printStackTrace();
        } finally {
            mgr.close();
        }
    }
     
    
    public void logApiCall(User user, String action, String model, String parameters, String returnvalues) {
	
	EntityManager mgr = this.emf.createEntityManager();
        try {
            
            ApiCall apiCall = new ApiCall();
            apiCall.setUser(user.getUsername());
            apiCall.setAction(action);
            apiCall.setModel(model);
            apiCall.setParameters(parameters);
            apiCall.setReturnvalues(returnvalues);
            
            mgr.getTransaction().begin();
            mgr.persist(apiCall);
            mgr.getTransaction().commit();
            
        } catch(Exception e) {
            log.log(Level.SEVERE, e.getMessage());
        } finally {
            mgr.close();
        }
    }
    
}
