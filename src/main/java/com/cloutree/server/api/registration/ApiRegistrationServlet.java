
/**
 * Copyright 2013 Marc Schachtel, Germany
 */

package com.cloutree.server.api.registration;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cloutree.server.api.pojo.ApiJsonObject;
import com.cloutree.server.api.push.ApiPushService;
import com.cloutree.server.api.session.APISession;
import com.cloutree.server.persistence.entity.Apihost;
import com.cloutree.server.persistence.entity.Instance;
import com.cloutree.server.persistence.service.ApihostService;

/**
 * TODO ApiRegstrationServlet
 *
 * @author mschachtel
 *
 */

public class ApiRegistrationServlet extends HttpServlet {

	private static final long serialVersionUID = 459985480571809796L;
	
	static Logger log = Logger.getLogger(ApiRegistrationServlet.class.getName());

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		log.log(Level.INFO, "Try to register API from: " + req.getRemoteAddr());
		
		ApiJsonObject result = new ApiJsonObject();
		
		String userName = req.getParameter("CLOUTREE:USER");
		String pass = req.getParameter("CLOUTREE:PASS");
		String tenant = req.getParameter("CLOUTREE:TENANT");
		String instanceStr = req.getParameter("CLOUTREE:INSTANCE");
		String apiName = req.getParameter("CLOUTREE:APINAME");

		log.log(Level.INFO, "Registration Details: User[" + userName + "] - Tenant[" + tenant + "] - API-NAME[" + apiName + "]");
		
		if(!APISession.isLoggedIn(req)) {
			if(!APISession.login(userName, tenant, pass, req)){
				log.log(Level.WARNING, "Log in of " + userName + "@" + tenant + "failed. API not registered.");
				return;
			}
		}
		
		resp.setContentType("text/x-json;charset=UTF-8");
		
		List<Instance> instances = APISession.getCurrentUser(req).getTenant().getInstances();
		
		Instance inst = null;
		
		for(Instance instance : instances) {
			if(instance.getId().equals(Integer.valueOf(instanceStr))) {
				inst = instance;
			}
		}
		
		if(inst == null) {
			log.log(Level.WARNING, "Instance " + instanceStr + " not found!");
			result.setError("Instance " + instanceStr + " not found!");
			result.setValid(false);
			resp.getWriter().write(result.toJson());
			return;
		}
		
		ApihostService service = new ApihostService(inst);
		Apihost host = service.getApihost(apiName);
		
		if(host == null) {
			log.log(Level.WARNING, "APIHost " + apiName + " not found!");
			result.setError("APIHost " + apiName + " not found!");
			result.setValid(false);
			resp.getWriter().write(result.toJson());
			return;
		}
		
		ApiPushService pushService = new ApiPushService(host);
		result = pushService.getActiveModels();
		
		resp.getWriter().write(result.toJson());
		
	}
}

