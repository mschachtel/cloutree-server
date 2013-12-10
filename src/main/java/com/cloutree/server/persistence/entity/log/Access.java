
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
 * Access Entity
 *
 * @author marc
 *
 * Since 09.09.2013
 */

@Entity
public class Access {

    @Id
    private Integer id;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;
    
    private String user;
    
    private String action;
    
    private String details;

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

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
    
}
