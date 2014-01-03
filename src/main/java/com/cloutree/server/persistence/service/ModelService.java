
/**
 * Copyright 2013 Marc Schachtel, Germany
 */

package com.cloutree.server.persistence.service;

import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.cloutree.server.api.push.ApiPushService;
import com.cloutree.server.config.CloutreeConfiguration;
import com.cloutree.server.persistence.entity.Apihost;
import com.cloutree.server.persistence.entity.Instance;
import com.cloutree.server.persistence.entity.Model;
import com.cloutree.server.persistence.entity.Modelrevision;
import com.cloutree.server.persistence.service.log.DBLogger;

/**
 * ModelService
 *
 * @author marc
 *
 * Since 11.07.2013
 */
public class ModelService {
    
    static Logger log = Logger.getLogger(ModelService.class.getName());
    
    private EntityManagerFactory emf;
    
    private Instance instance;
    
    public ModelService(Instance instance){
	
    	this.emf =  Persistence.createEntityManagerFactory("ClouTree");
    	this.instance = instance;
    	
    }
    
    protected Model getModel(Integer id) {
	
        Model result = null;
        EntityManager mgr = this.emf.createEntityManager();
        try {
            result = mgr.find(Model.class, id);
        } finally {
            mgr.close();
        }
        return result;
    }
    
    public Model getModelByName(String name) {
	
        Model result = null;
        EntityManager mgr = this.emf.createEntityManager();
        Query query = mgr.createQuery("SELECT m FROM " + Model.class.getName() + " m WHERE m.name = :name AND m.instance = :instance");
        query.setParameter("name", name);
        
        if(this.instance == null) {
        	return null;
        }
        
        query.setParameter("instance", this.instance.getId());
        
        Object resultObject = null;
        
        try {
        	resultObject = query.getSingleResult();
        } catch (NoResultException e) {
        	return null;
        }
        
        try {
            result = (Model)resultObject;
        } catch(NoResultException e) {
            log.log(Level.SEVERE, e.getMessage());
            e.printStackTrace();
            return null;
        } finally {
            mgr.close();
        }
        return result;
    }
    
    public boolean storeModel(Model model) {
	
    	Model exists = this.getModelByName(model.getName());
    	if(exists != null) {
    		return false;
    	}
    	
        EntityManager mgr = this.emf.createEntityManager();
        try {
            mgr.getTransaction().begin();
            mgr.persist(model);
            mgr.getTransaction().commit();
        } catch(Exception e) {
            log.log(Level.SEVERE, e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            mgr.close();
            
            DBLogger.logAccess("STORE_MODEL", model.getName());
            
        }
        return true;
    }
    
    public boolean storeRevision(Modelrevision revision) {
	
        EntityManager mgr = this.emf.createEntityManager();
        try {
            mgr.getTransaction().begin();
            mgr.persist(revision);
            mgr.getTransaction().commit();
        } catch(Exception e) {
            log.log(Level.SEVERE, e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            mgr.close();
            
            DBLogger.logAccess("STORE_REVISION", revision.getId());
            
        }
        return true;
    }
    
    public boolean updateModel(Model model) {
	
        EntityManager mgr = this.emf.createEntityManager();
        try {
            mgr.getTransaction().begin();
            mgr.merge(model);
            mgr.getTransaction().commit();
        } catch(Exception e) {
            log.log(Level.SEVERE, e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            mgr.close();
            
            DBLogger.logAccess("UPDATE_MODEL", model.getName());
            
        }
        
        // Now push model to APIs
        ApihostService apihostService = new ApihostService(this.instance);
        List<Apihost> hosts = apihostService.getAllAvailableApihosts();
        
        ApiPushService pushService;
        for(Apihost host : hosts) {
        	pushService = new ApiPushService(host);
        	pushService.pushModel(model);
        }
        
        return true;
    }
    
    public boolean deleteModel(Model model) {
	
        EntityManager mgr = this.emf.createEntityManager();
        try {
            mgr.getTransaction().begin();
            Query query = mgr.createQuery("SELECT mr FROM " + Modelrevision.class.getName() + " mr WHERE mr.model = :modelId ORDER BY mr.revision DESC");
            query.setParameter("modelId", model.getId());
            
            @SuppressWarnings("unchecked")
	    List<Modelrevision> revisions = query.getResultList();
            
            if(revisions.size() > 0) {
        	this.deleteRevisionDirectory(revisions.get(0));
            }
            
            for(Modelrevision revision : revisions) {
        	mgr.remove(revision);
            }
            
            model = mgr.merge(model);
            mgr.remove(model);
            
            mgr.getTransaction().commit();
            
        } catch(Exception e) {
            log.log(Level.SEVERE, e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            mgr.close();
            
            DBLogger.logAccess("DELETE_MODEL", model.getName());
            
        }
        return true;
    }
    
    @SuppressWarnings("unchecked")
    public List<Model> getAllAvailableModels() {
	
        List<Model> result = null;
        EntityManager mgr = this.emf.createEntityManager();
        Query query = mgr.createQuery("SELECT m FROM " + Model.class.getName() + " m WHERE m.instance = :instance");
        query.setParameter("instance", this.instance.getId());
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
    
    @SuppressWarnings("unchecked")
    public List<Model> getAllReleasedModels() {
	
        List<Model> result = null;
        EntityManager mgr = this.emf.createEntityManager();
        Query query = mgr.createQuery("SELECT m FROM " + Model.class.getName() + " m WHERE m.instance = :instance AND m.released = 1");
        query.setParameter("instance", this.instance.getId());
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
        
    @SuppressWarnings("unchecked")
    public List<Modelrevision> getRevisionsForModel(Model model) {
	
    	List<Modelrevision> result = null;
        EntityManager mgr = this.emf.createEntityManager();
        Query query = mgr.createQuery("SELECT mr FROM " + Modelrevision.class.getName() + " mr WHERE mr.model = :modelId ORDER BY mr.revision DESC");
        query.setParameter("modelId", model.getId());
        try {
            result = (List<Modelrevision>)query.getResultList();
        } catch(NoResultException e) {
            log.log(Level.SEVERE, e.getMessage());
            return null;
        } finally {
            mgr.close();
        }
        return result;
    }
    
    public Modelrevision getActiveRevisionForModel(Model model) {
	
    	Modelrevision result = null;
        EntityManager mgr = this.emf.createEntityManager();
        Query query = mgr.createQuery("SELECT mr FROM " + Modelrevision.class.getName() + " mr WHERE mr.model = :modelId AND mr.revision = :revision");
        query.setParameter("modelId", model.getId());
        query.setParameter("revision", model.getActiveVersion());
        try {
            result = (Modelrevision)query.getSingleResult();
        } catch(NoResultException e) {
            log.log(Level.SEVERE, e.getMessage());
            return null;
        } finally {
            mgr.close();
        }
        return result;
    }
    
    public Modelrevision getRevisionForModel(Model model, Integer revision) {
	
    	Modelrevision result = null;
        EntityManager mgr = this.emf.createEntityManager();
        Query query = mgr.createQuery("SELECT mr FROM " + Modelrevision.class.getName() + " mr WHERE mr.model = :modelname AND mr.revision = :revision");
        query.setParameter("modelname", model.getName());
        query.setParameter("revision", revision);
        try {
            result = (Modelrevision)query.getSingleResult();
        } catch(NoResultException e) {
            log.log(Level.SEVERE, e.getMessage());
            return null;
        } finally {
            mgr.close();
        }
        return result;
    }
    
    private void deleteRevisionDirectory(Modelrevision revision) {
    	String storagePath = CloutreeConfiguration.getProperty(CloutreeConfiguration.SERVER_STORAGE_PATH);
		File file = new File(storagePath + revision.getFile());
		if(file.exists()) {
		    file.getParentFile().delete();
		}
    }
    
}
