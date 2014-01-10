
/**
 * Copyright 2013 Marc Schachtel, Germany
 */

package com.cloutree.server.api.servlet;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

public class ApiUnregistrationServlet extends HttpServlet {

	private static final long serialVersionUID = 459985480571809796L;
	
	static Logger log = Logger.getLogger(ApiUnregistrationServlet.class.getName());

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		log.log(Level.INFO, "Try to un-register API from: " + req.getRemoteAddr());
		
		String userName = req.getParameter("CLOUTREE:USER");
		String pass = req.getParameter("CLOUTREE:PASS");
		String tenant = req.getParameter("CLOUTREE:TENANT");
		String apiName = req.getParameter("CLOUTREE:APINAME");

		log.log(Level.INFO, "Un-Registration Details: User[" + userName + "] - Tenant[" + tenant + "] - API-NAME[" + apiName + "]");
		
		if(!APISession.isLoggedIn(req)) {
			if(!APISession.login(userName, tenant, pass, req)){
				log.log(Level.WARNING, "Log in of " + userName + "@" + tenant + "failed. API not un-registered.");
				return;
			}
		}
		
		List<Instance> instances = APISession.getCurrentUser(req).getTenant().getInstances();
		
		for(Instance instance : instances) {
			ApihostService service = new ApihostService(instance);
			Apihost host = service.getApihost(apiName);
			if(host != null) {
				service.switchHostStatus(host);
			}
		}
		
	}
}

