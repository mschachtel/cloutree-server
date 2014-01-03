
/**
 * Copyright 2013 Marc Schachtel, Germany
 */

package com.cloutree.server.api.servlet;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cloutree.server.api.session.APISession;
import com.cloutree.server.config.CloutreeConfiguration;
import com.cloutree.server.persistence.entity.Instance;
import com.cloutree.server.persistence.entity.Model;
import com.cloutree.server.persistence.entity.Modelrevision;
import com.cloutree.server.persistence.service.ModelService;

/**
 * TODO ApiRegstrationServlet
 *
 * @author mschachtel
 *
 */

public class ModelFileDownloadServlet extends HttpServlet {

	private static final long serialVersionUID = 459985480571809796L;
	
	static Logger log = Logger.getLogger(ModelFileDownloadServlet.class.getName());
	private static final int BUFSIZE = 4096;

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		log.log(Level.INFO, "Try Download ModelFile from: " + req.getRemoteAddr());
		
		String userName = req.getParameter("CLOUTREE:USER");
		String pass = req.getParameter("CLOUTREE:PASS");
		String tenant = req.getParameter("CLOUTREE:TENANT");
		String instanceStr = req.getParameter("CLOUTREE:INSTANCE");
		String modelName = req.getParameter("CLOUTREE:MODEL");

		log.log(Level.INFO, "File Download Details: User[" + userName + "] - Tenant[" + tenant + "] - MODEL-NAME[" + modelName + "]");
		
		if(!APISession.isLoggedIn(req)) {
			if(!APISession.login(userName, tenant, pass, req)){
				log.log(Level.WARNING, "Log in of " + userName + "@" + tenant + "failed. File not sent.");
				return;
			}
		}
		
		String filePath;
		
		// TODO get Filepath from model
		List<Instance> instances = APISession.getCurrentUser(req).getTenant().getInstances();
		Instance inst = null;
		for(Instance instance : instances) {
			if(instance.getId().equals(Integer.valueOf(instanceStr))) {
				inst = instance;
			}
		}
		
		if(inst == null) {
			resp.sendError(404, "Instance not found");
			return;
		}
		
		ModelService modelService = new ModelService(inst);
		Model model = modelService.getModelByName(modelName);
		if(model == null) {
			resp.sendError(404, "Model not found");
			return;
		}
		
		Modelrevision rev = modelService.getActiveRevisionForModel(model);
		if(rev == null) {
			resp.sendError(404, "No active Revision found");
			return;
		}
		
		String storagePath = CloutreeConfiguration.getProperty(CloutreeConfiguration.SERVER_STORAGE_PATH);
		filePath = storagePath + rev.getFile();
		
		File file = new File(filePath);
        int length   = 0;
        ServletOutputStream outStream = resp.getOutputStream();
        ServletContext context  = getServletConfig().getServletContext();
        String mimetype = context.getMimeType(filePath);
        
        // sets response content type
        if (mimetype == null) {
            mimetype = "application/octet-stream";
        }
        resp.setContentType(mimetype);
        resp.setContentLength((int)file.length());
        String fileName = (new File(filePath)).getName();
        
        // sets HTTP header
        resp.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        
        byte[] byteBuffer = new byte[BUFSIZE];
        DataInputStream in = new DataInputStream(new FileInputStream(file));
        
        // reads the file's bytes and writes them to the response stream
        while ((in != null) && ((length = in.read(byteBuffer)) != -1))
        {
            outStream.write(byteBuffer,0,length);
        }
        
        in.close();
        outStream.close();
		
	}
}

