
/**
 * Copyright 2013 Marc Schachtel, Germany
 */

package com.cloutree.server.persistence.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Tenant {

	@Id
	private String name;
	
	private Integer adminuser;
	
	@OneToMany(mappedBy="tenant", fetch = FetchType.EAGER)
	private List<Instance> instances;

	public String getName() {
		return name;
	}

	public Integer getAdminuser() {
		return adminuser;
	}

	public List<Instance> getInstances() {
		return instances;
	}
	
}
