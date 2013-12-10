
/**
 * Copyright 2013 Marc Schachtel, Germany
 */

package com.cloutree.server.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

@Entity
public class Instance {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String name;
	
	@JoinColumn(name="tenant")
	private Tenant tenant;

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Tenant getTenant() {
		return tenant;
	}
	
	@Override
	public String toString() {
		
		String result = "";
		result = result + this.tenant.getName();
		result = result + ": " + this.getName();
		result = result + " [" + this.getId() + "]";
		
		return result;
		
	}
	
}
