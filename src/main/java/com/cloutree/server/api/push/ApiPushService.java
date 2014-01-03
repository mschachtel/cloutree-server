
/**
 * Copyright 2013 Marc Schachtel, Germany
 */

package com.cloutree.server.api.push;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.cloutree.server.HttpSender;
import com.cloutree.server.api.pojo.ActiveModel;
import com.cloutree.server.api.pojo.ApiJsonObject;
import com.cloutree.server.api.pojo.InstanceActiveModels;
import com.cloutree.server.persistence.entity.Apihost;
import com.cloutree.server.persistence.entity.Model;
import com.cloutree.server.persistence.entity.Modelrevision;
import com.cloutree.server.persistence.service.ModelService;

/**
 * ApiPushService
 *
 * @author mschachtel
 *
 */

public class ApiPushService {
	
	static Logger log = Logger.getLogger(ApiPushService.class.getName());
	
	private Apihost host;
	
	public ApiPushService(Apihost host) {
		this.host = host;
	}

	public ApiJsonObject getActiveModels() {
		
		if(this.host == null) {
			log.log(Level.SEVERE, "Host is null, can't synch!");
			return null;
		}
		
		InstanceActiveModels apiBody = new InstanceActiveModels();
		List<ActiveModel> activeModels = new ArrayList<ActiveModel>();
		
		ApiJsonObject apiJsonObject = new ApiJsonObject();
		apiJsonObject.setRequestName(ApiJsonObject.INITIAL_PUSH_NAME);
		
		ModelService modelService = new ModelService(this.host.getInstance());
		List<Model> releasedModels = modelService.getAllReleasedModels();
		
		for(Model model : releasedModels) {
			Modelrevision rev = modelService.getActiveRevisionForModel(model);
			if(rev != null) {
				activeModels.add(new ActiveModel(model, rev));
			}
		}
		
		apiBody.setActiveModels(activeModels);
		apiBody.setInstanceName(this.host.getInstance().getName());
		apiBody.setTimestamp(new Date());
		
		apiJsonObject.setRequestBody(apiBody);
		
		return apiJsonObject;
	}
	
	public ApiJsonObject getSingleModels(Model model) {
		
		if(this.host == null) {
			log.log(Level.SEVERE, "Host is null, can't synch!");
			return null;
		}
		
		InstanceActiveModels apiBody = new InstanceActiveModels();
		List<ActiveModel> activeModels = new ArrayList<ActiveModel>();
		
		ApiJsonObject apiJsonObject = new ApiJsonObject();
		apiJsonObject.setRequestName(ApiJsonObject.MODEL_PUSH_NAME);
		
		ModelService modelService = new ModelService(this.host.getInstance());
		
		Modelrevision rev = modelService.getActiveRevisionForModel(model);
		if(rev != null) {
			activeModels.add(new ActiveModel(model, rev));
		}
		
		apiBody.setActiveModels(activeModels);
		apiBody.setInstanceName(this.host.getInstance().getName());
		apiBody.setTimestamp(new Date());
		
		apiJsonObject.setRequestBody(apiBody);
		
		return apiJsonObject;
	}
	
	public void pushInitially(){

		if(this.host == null) {
			log.log(Level.SEVERE, "Host is null, can't synch!");
			return;
		}
		
		ApiJsonObject apiJsonObject = this.getActiveModels();
		
		HttpSender.sendHttpPostJson(apiJsonObject.toJson(), this.host.getUrl());
		log.log(Level.INFO, "Pushed " + apiJsonObject.toJson() + " to host " + this.host.getName() + "(" + this.host.getUrl() + ")");
		
	}
	
	public void pushModel(Model model){
		
		if(this.host == null) {
			log.log(Level.SEVERE, "Host is null, can't synch!");
			return;
		}
		
		ApiJsonObject apiJsonObject = this.getSingleModels(model);
		
		HttpSender.sendHttpPostJson(apiJsonObject.toJson(), this.host.getUrl());
		log.log(Level.INFO, "Pushed " + apiJsonObject.toJson() + " to host " + this.host.getName() + "(" + this.host.getUrl() + ")");
	}
	
}

