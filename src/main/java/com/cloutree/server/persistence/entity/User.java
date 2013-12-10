
/**
 * Copyright 2013 Marc Schachtel, Germany
 */

package com.cloutree.server.persistence.entity;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

import com.cloutree.server.permission.Permissions;

/**
 * User
 *
 * @author marc
 *
 * Since 16.07.2013
 */

@Entity
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @JoinColumn(name="tenant")
    private Tenant tenant;
    
    private String username;
    
    private byte[] password;
    
    private byte[] salt;
    
    private String name;
    
    private String firstname;
    
    private String email;
    
    private String permission;
    
    @Transient
    private List<Permissions> permissions;
    
    private Boolean active;
    
    public User() {
	
    }
    
    public User(Integer id, String username, byte[] password, byte[] salt) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
		this.tenant = tenant;
	}

	public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public byte[] getPassword() {
        return password;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getFullName() {
	return this.getFirstname() + " " + this.getName();
    }
    
    public List<Permissions> getPermissionList(){
	
		if(this.permissions == null) {
		    this.permissions = new LinkedList<Permissions>();
		    String[] permissionStrings = StringUtils.split(this.permission, ",");
		    
		    Permissions permTemp;
		    for(String permission : permissionStrings) {
			
			permission = permission.trim();
			
			permTemp = Permissions.valueOf(permission);
			if (permTemp != null) {
			    this.permissions.add(permTemp);
			}
			    
		    }
		}
	
		return this.permissions;
	
    }
    
    public boolean hasWritePermission() {
	
		boolean result = false;
		List<Permissions> permissions = this.getPermissionList();
		result = result || permissions.contains(Permissions.SUPERUSER);
		return result;
	
    }
    
    public boolean hasReadPermission(){
		boolean result = false;
		List<Permissions> permissions = this.getPermissionList();
		result = result || permissions.contains(Permissions.SUPERUSER);
		result = result || permissions.contains(Permissions.DEVELOPER);
		return result;
	    }
    
    public boolean isAPIUser() {
		boolean result = false;
		List<Permissions> permissions = this.getPermissionList();
		result = result || permissions.contains(Permissions.API);
		return result;
    }
    
}
