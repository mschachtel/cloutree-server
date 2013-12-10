
/**
 * Copyright 2013 Marc Schachtel, Germany
 */

package com.cloutree.server.api;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * APIResult
 *
 * @author marc
 *
 * Since 09.09.2013
 */
public class ModelParameterResult {

    private String user;
    
    private String modelName;
    
    private Integer revision;
    
    private String message;
    
    private Map<String, String> parameters;
    
    private List<String> exceptions;

    
    public ModelParameterResult() {
		this.exceptions = new LinkedList<String>();
		this.parameters = new HashMap<String, String>();
    }
    
    public ModelParameterResult(String user, String modelName) {
		this.exceptions = new LinkedList<String>();
		this.user = user;
		this.modelName = modelName;
    }
    
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Integer getRevision() {
        return revision;
    }

    public void setRevision(Integer revision) {
        this.revision = revision;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public List<String> getExceptions() {
        return exceptions;
    }

    public void setExceptions(List<String> exceptions) {
        this.exceptions = exceptions;
    }
    
    public void addException(String message) {
	this.exceptions.add(message);
    }
    
    public String toJSON() {
		// TODO
		return "";
    }
    
}
