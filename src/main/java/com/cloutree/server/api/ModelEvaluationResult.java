
/**
 * Copyright 2013 Marc Schachtel, Germany
 */

package com.cloutree.server.api;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import com.cloutree.modelevaluator.PredictiveModelResult;

/**
 * ModelEvaluationResult
 *
 * @author marc
 *
 * Since 09.09.2013
 */
public class ModelEvaluationResult {

    private String user = "";
    
    private String modelName ="";
    
    private Integer revision = -1;
    
    private String message = "";
    
    private PredictiveModelResult result;
    
    private List<String> exceptions;

    public ModelEvaluationResult() {
    	this.exceptions = new LinkedList<String>();
    }
    
    public ModelEvaluationResult(String user, String modelName) {
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

    public PredictiveModelResult getResult() {
        return result;
    }

    public void setResult(PredictiveModelResult result) {
        this.result = result;
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
		JsonObjectBuilder builder = Json.createObjectBuilder();
		builder.add("user", this.user);
		builder.add("model", this.modelName);
		builder.add("revision", this.revision);
		builder.add("message", this.message);
		
		JsonArrayBuilder resultBuilder = Json.createArrayBuilder();
		JsonObjectBuilder resultObjectbuilder = Json.createObjectBuilder();
		if(this.result != null) {
		    
		    	JsonArrayBuilder outputBuilder = Json.createArrayBuilder();
		    	
		    	// Output Values
	    		Set<String> outputKeys = this.result.getOutputValues().keySet();
	    		for(String outputKey : outputKeys) {
	    		    Object val = this.result.getOutputValues().get(outputKey);
	    		    if(val != null) {
	    			outputBuilder.add(Json.createObjectBuilder().add(outputKey, val.toString()));
	    		    }
	    		}
	    		resultObjectbuilder.add("outputValues", outputBuilder);
	    		
	    		// Predicted values
	    		JsonArrayBuilder predictedBuilder = Json.createArrayBuilder();
	    		Set<String> predictedKeys = this.result.getPredictedValues().keySet();
	    		for(String predictedKey : predictedKeys) {
	    		    Object val = this.result.getPredictedValues().get(predictedKey);
	    		    if(val != null) {
	    			predictedBuilder.add(Json.createObjectBuilder().add(predictedKey, val.toString()));
	    		    }
	    		}
	    		resultObjectbuilder.add("predictedValues", predictedBuilder);
	    		
	    		// Errors
	    		JsonArrayBuilder errorBuilder = Json.createArrayBuilder();
	    		List<String> errors = this.result.getErrors();
	    		for(String error : errors) {
	    		    errorBuilder.add(error);
	    		}
	    		resultObjectbuilder.add("errors", errorBuilder);
		}
		
		resultBuilder.add(resultObjectbuilder);
		builder.add("modelResult", resultBuilder);
		
		JsonArrayBuilder exceptionBuilder = Json.createArrayBuilder();
		for(String exception : this.exceptions) {
		    exceptionBuilder.add(exception);
		}
		builder.add("exceptions", exceptionBuilder);
		
		
		JsonObject jsonObject = builder.build();
		
		return jsonObject.toString();
    }
    
}
