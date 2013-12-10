
/**
 * Copyright 2013 Marc Schachtel, Germany
 */

package com.cloutree.server.persistence.entity.log;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * ApiCall Entity
 *
 * @author marc
 *
 * Since 09.09.2013
 */

@Entity
public class ApiCall {

    @Id
    private Integer id;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;
    
    private String user;
    
    private String action;
    
    private String model;
    
    private String parameters;
    
    private String returnvalues;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public String getReturnvalues() {
        return returnvalues;
    }

    public void setReturnvalues(String returnvalues) {
        this.returnvalues = returnvalues;
    }
    
}
