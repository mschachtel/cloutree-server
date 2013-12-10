
/**
 * Copyright 2013 Marc Schachtel, Germany
 */
package com.cloutree.server.api;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cloutree.modelevaluator.ModelTypes;
import com.cloutree.modelevaluator.PredictiveModel;
import com.cloutree.modelevaluator.PredictiveModelFactory;
import com.cloutree.modelevaluator.PredictiveModelResult;
import com.cloutree.server.api.session.APISession;
import com.cloutree.server.permission.Permissions;
import com.cloutree.server.persistence.entity.Model;
import com.cloutree.server.persistence.entity.Modelrevision;
import com.cloutree.server.persistence.entity.User;
import com.cloutree.server.persistence.service.ModelService;
import com.cloutree.server.persistence.service.log.DBLogger;
import com.cloutree.server.session.ClouTreeSession;

/**
 * ModelParameterServlet
 *
 * @author marc
 *
 * Since 30.08.2013
 */
public class ModelEvaluationServlet extends HttpServlet {
    
    static Logger log = Logger.getLogger(ModelEvaluationServlet.class.getName());

    private static final long serialVersionUID = 1L;

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
	    throws ServletException, IOException {
		
		ModelEvaluationResult apiResult = new ModelEvaluationResult();
		ServletOutputStream out = resp.getOutputStream();
		
		if(!this.authenticate(req, apiResult)) {
		    out.print(apiResult.toJSON());
		} else {
		    // Delete not supported
		    apiResult.addException("Delete not supported");
		    out.print(apiResult.toJSON());
		}

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	    throws ServletException, IOException {
		
		ModelEvaluationResult apiResult = new ModelEvaluationResult();
		ServletOutputStream out = resp.getOutputStream();
		
		if(!this.authenticate(req, apiResult)) {
		    out.print(apiResult.toJSON());
		} else {
		    // Delete not supported
		    apiResult.addException("Get not supported");
		    out.print(apiResult.toJSON());
		}
	
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	    throws ServletException, IOException {
	
		ModelEvaluationResult apiResult = new ModelEvaluationResult();
		ServletOutputStream out = resp.getOutputStream();
		
		if(!this.authenticate(req, apiResult)) {
		    out.print(apiResult.toJSON());
		} else {
		    String model  = req.getParameter("CLOUTREE:MODEL");
		    String revisionString  = req.getParameter("CLOUTREE:REVISION");
		    Integer revision = null;
		    if(model != null && !model.isEmpty()) {
			apiResult.setModelName(model);
		    } else {
			apiResult.addException("Model not set!");
			apiResult.setMessage("FAILED");
			out.print(apiResult.toJSON());
			return;
		    }
		    
		    Model modelEntity = null;
		    Modelrevision revisionEntity = null;
		    ModelService modelService = new ModelService(ClouTreeSession.getInstance());
		    
		    modelEntity = modelService.getModelByName(model);
		    if(modelEntity == null || !modelEntity.getReleased()) {
			apiResult.addException("Model + " + model + " not found!");
			apiResult.setMessage("FAILED");
			out.print(apiResult.toJSON());
			return;
		    }
		    
		    if(revisionString == null || revisionString.isEmpty() || revisionString.equals(0)) {
			revision = modelEntity.getActiveVersion();
			
			if(revision.equals(0)){
			    apiResult.addException("Model + " + model + " has no active revision!");
			    apiResult.setMessage("FAILED");
			    out.print(apiResult.toJSON());
			    return;
			}
			
		    } else {
			revision = Integer.valueOf(revisionString);
		    }
		    
		    apiResult.setRevision(revision);
		    
		    revisionEntity = modelService.getRevisionForModel(modelEntity, revision);
		    if(revisionEntity == null) {
			apiResult.addException("Revision " + revisionString + " for  model " + model + " not found!");
			apiResult.setMessage("FAILED");
			out.print(apiResult.toJSON());
			return;
		    }
		    
		    PredictiveModel predModel = this.initiatePredictiveModel(modelEntity, revisionEntity.getFile());
		    PredictiveModelResult result = this.evalPredictiveModel(predModel, this.initParameterMap(req));
		    
		    apiResult.setResult(result);
		    if(result.isValid()) {
			apiResult.setMessage("EVALUATED");
		    } else {
			apiResult.setMessage("FAILED");
		    }
		    String json = apiResult.toJSON();
		    out.print(json);
		    
		    DBLogger.logApiCall(APISession.getCurrentUser(req), "EVALUATE MODEL", apiResult.getModelName(), apiResult.getMessage(), apiResult.toJSON());
		}
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
	    throws ServletException, IOException {
	
		ModelEvaluationResult apiResult = new ModelEvaluationResult();
		ServletOutputStream out = resp.getOutputStream();
		
		if(!this.authenticate(req, apiResult)) {
		    out.print(apiResult.toJSON());
		} else {
		    // Delete not supported
		    apiResult.addException("Put not supported");
		    out.print(apiResult.toJSON());
		}

    }
    
    private boolean authenticate(HttpServletRequest req, ModelEvaluationResult result) {
	
		String userName = req.getParameter("CLOUTREE:USER");
		String pass = req.getParameter("CLOUTREE:PASS");
		String tenant = req.getParameter("CLOUTREE:TENANT");
		
		if(userName == null || userName.isEmpty()) {
		    result.addException("User-Authentication failed!");
		    log.log(Level.INFO, "User not set!");
		    return false;
		}
		
		if(tenant == null || tenant.isEmpty()) {
		    result.addException("User-Authentication failed!");
		    log.log(Level.INFO, "Tenant not set!");
		    return false;
		}
		
		User tempUser = null;
		
		result.setUser(userName);
		
		// Check if already logged in...
		if(APISession.isLoggedIn(req)) {
		    tempUser = APISession.getCurrentUser(req);
		    
		    // ... if logged in AND API user, authenticate.
		    if(tempUser.getPermissionList().contains(Permissions.API) && tempUser.getUsername().equals(userName)) {
			return true;
		    
		    // ... else if not API user AND trying to login with the same user, just leave it!
		    } else if(tempUser.getUsername().equals(userName)) {
			result.addException("User-Authentication failed!");
			log.log(Level.INFO, "User " + userName + " not a valid API-User");
			return false;
		    // ... else logout and try with sent credentials
		    } else {
			APISession.logout(req);
		    }
		}
		
		if(APISession.login(userName, tenant, pass, req)){
		    tempUser = APISession.getCurrentUser(req);
		    if(tempUser != null && tempUser.getPermissionList().contains(Permissions.API)) {
			return true;
		    } else {
			result.addException("User-Authentication failed!");
			log.log(Level.INFO, "User " + userName + " not a valid API-User");
			return false;
		    }
		}
		
		result.addException("User-Authentication failed!");
		log.log(Level.INFO, "User " + userName + " could not be logged in");
		return false;
	
    }
    
    private PredictiveModelResult evalPredictiveModel(PredictiveModel model, Map<String, String> params) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		
		// Create parameter map
		Set<String> parameterNames = params.keySet();
		
		for(String param : parameterNames) {
		    parameters.put(param, params.get(param));
		}
		
		// Eval
		return model.eval(parameters);
    }
    
    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServlet#getLastModified(javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected long getLastModified(HttpServletRequest req) {
        // TODO Auto-generated method stub
        return super.getLastModified(req);
    }
    
    /**
     * 
     */
    public ModelEvaluationServlet() {
		
    }
    
    private PredictiveModel initiatePredictiveModel(Model model, String filePath) {
	
		// Set File
		String basepath = getServletContext().getRealPath("");
		File file = new File(basepath + "/WEB-INF/storage" + filePath);
		PredictiveModel result = null;
		
		try {
		    // TODO Implement R
		    result = PredictiveModelFactory.getPredictiveModel(ModelTypes.PMML, file);
		} catch (Exception e) {
		    log.log(Level.SEVERE, e.getMessage());
		    return null;
		}
		
		return result;
	
    }
    
    private Map<String, String> initParameterMap(HttpServletRequest req) {
	
		Map<String, String> result = new HashMap<String, String>();
		
		for(String paramName : req.getParameterMap().keySet()) {
		    if(paramName.startsWith("PARAM:")) {
	    	    	String realName = paramName.replaceAll("PARAM:", "");
	    	    	result.put(realName, req.getParameter(paramName));
		    }
		}
		
		return result;
	
    }

}
