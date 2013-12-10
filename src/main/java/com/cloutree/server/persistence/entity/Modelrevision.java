
/**
 * Copyright 2013 Marc Schachtel, Germany
 */

package com.cloutree.server.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Modelrevision
 *
 * @author marc
 *
 * Since 26.08.2013
 */

@Entity
public class Modelrevision implements Comparable<Modelrevision> {

    @Id
    private String id;
    
    private Integer model;
    
    private Integer revision;
    
    private String file;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getModel() {
        return model;
    }

    public void setModel(Integer model) {
        this.model = model;
    }

    public Integer getRevision() {
        return revision;
    }

    public void setRevision(Integer revision) {
        this.revision = revision;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(Modelrevision o) {
	
	if(this.model == null || this.revision == null) {
	    return -99;
	}
	
	if(this.model.equals(o.getModel()) && this.revision.equals(o.getRevision())) {
	    return 0;
	}
	
	if(!this.model.equals(o.getModel())) {
	    return o.getRevision().compareTo(this.revision);
	} else {
	    return o.getModel().compareTo(this.model);
	}

    }
    
}
