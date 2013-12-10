
/**
 * Copyright 2013 Marc Schachtel, Germany
 */

package com.cloutree.server.api;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cloutree.server.permission.Permissions;
import com.cloutree.server.permission.UserManager;
import com.cloutree.server.persistence.entity.User;

/**
 * ModelParameterServlet
 *
 * @author marc
 *
 * Since 30.08.2013
 */
public class ModelParamaterServlet extends HttpServlet {
    
    static Logger log = Logger.getLogger(ModelParamaterServlet.class.getName());

    private static final long serialVersionUID = 1L;
    
    private User user;

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
	    throws ServletException, IOException {
		
		ModelParameterResult paramResult = new ModelParameterResult();
		ServletOutputStream out = resp.getOutputStream();
		
		if(!this.authenticate(req, paramResult)) {
		    out.print(paramResult.toJSON());
		} else {
		    // Delete not supported
		    paramResult.addException("Delete not supported");
		    out.print(paramResult.toJSON());
		}
		
		super.doDelete(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	    throws ServletException, IOException {
		
		ModelParameterResult paramResult = new ModelParameterResult();
		ServletOutputStream out = resp.getOutputStream();
		
		if(!this.authenticate(req, paramResult)) {
		    out.print(paramResult.toJSON());
		} else {
		    // TODO Implement Model Parameter Get
		}
		
		super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	    throws ServletException, IOException {
		
		ModelParameterResult paramResult = new ModelParameterResult();
		ServletOutputStream out = resp.getOutputStream();
		
		if(!this.authenticate(req, paramResult)) {
		    out.print(paramResult.toJSON());
		} else {
		    // Post not supported
		    paramResult.addException("Delete not supported");
		    out.print(paramResult.toJSON());
		}
		
	super.doPost(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
	    throws ServletException, IOException {
	
		ModelParameterResult paramResult = new ModelParameterResult();
		ServletOutputStream out = resp.getOutputStream();
		
		if(!this.authenticate(req, paramResult)) {
		    out.print(paramResult.toJSON());
		} else {
		    // Delete not supported
		    paramResult.addException("Put not supported");
		    out.print(paramResult.toJSON());
		}
		
		super.doPut(req, resp);
    }
    
    private boolean authenticate(HttpServletRequest req, ModelParameterResult result) {
	
		String userName = req.getParameter("USER");
		String tenant = req.getParameter("TENANT");
		String pass = req.getParameter("PASS");
		User tempUser = null;
		
		result.setUser(userName);
		
		// Check if already logged in...
		if(UserManager.isLoggedIn()) {
		    tempUser = UserManager.getCurrentUser();
		    
		    // ... if logged in AND API user, authenticate.
		    if(tempUser.getPermissionList().contains(Permissions.API)) {
			this.setUser(tempUser);
			return true;
		    
		    // ... else if not API user AND trying to login with the same user, just leave it!
		    } else if(tempUser.getUsername().equals(userName)) {
			result.addException("User-Authentication failed!");
			log.log(Level.INFO, "User " + userName + " not a valid API-User");
			return false;
		    // ... else logout and try with sent credentials
		    } else {
			UserManager.logout();
		    }
		}
		
		if(UserManager.login(userName, tenant, pass, false)){
		    tempUser = UserManager.getCurrentUser();
		    if(tempUser != null && tempUser.getPermissionList().contains(Permissions.API)) {
			this.setUser(tempUser);
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
