
/**
 * Copyright 2013 Marc Schachtel, Germany
 */

package com.cloutree.server.api.pojo;

import com.cloutree.server.config.CloutreeConfiguration;

import flexjson.JSONSerializer;

/**
 * ApiJsonObject
 *
 * @author mschachtel
 *
 */

public class ApiJsonObject {
	
	public static String INITIAL_PUSH_NAME = "INITIAL_PUSH";	
	public static String MODEL_PUSH_NAME = "MODEL_PUSH";

	private String requestName;
	
	private String requestorVersion;
	
	private boolean valid = true;
	
	private String error = "";
	
	private String tenant = "";
	
	private ApiBody requestBody;

	public ApiJsonObject() {
		this.requestorVersion = CloutreeConfiguration.getProperty(CloutreeConfiguration.SERVER_VERSION);
	}
	
	
	/**
	 * @param requestName the requestName to set
	 */
	public void setRequestName(String requestName) {
		this.requestName = requestName;
	}



	/**
	 * @param requestBody the requestBody to set
	 */
	public void setRequestBody(ApiBody requestBody) {
		this.requestBody = requestBody;
	}



	/**
	 * @return the requestName
	 */
	public String getRequestName() {
		return requestName;
	}

	/**
	 * @return the requestBody
	 */
	public ApiBody getRequestBody() {
		return requestBody;
	}

	/**
	 * @return the requestorVersion
	 */
	public String getRequestorVersion() {
		return requestorVersion;
	}
	
	
	/**
	 * @return the valid
	 */
	public boolean isValid() {
		return valid;
	}


	/**
	 * @param valid the valid to set
	 */
	public void setValid(boolean valid) {
		this.valid = valid;
	}


	/**
	 * @return the error
	 */
	public String getError() {
		return error;
	}


	/**
	 * @param error the error to set
	 */
	public void setError(String error) {
		this.error = error;
	}


	/**
	 * @return the tenant
	 */
	public String getTenant() {
		return tenant;
	}


	/**
	 * @param tenant the tenant to set
	 */
	public void setTenant(String tenant) {
		this.tenant = tenant;
	}


	/**
	 * @param requestorVersion the requestorVersion to set
	 */
	public void setRequestorVersion(String requestorVersion) {
		this.requestorVersion = requestorVersion;
	}


	public String toJson() {
		JSONSerializer serializer = new JSONSerializer();
		return serializer.deepSerialize(this);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		
		ApiJsonObject compare;
		try {
			compare = (ApiJsonObject)obj;
		} catch(ClassCastException e) {
			return false;
		}
		
		boolean request = (this.requestName == null && compare.getRequestName() == null) || this.requestName.equals(compare.getRequestName());
		boolean version = (this.requestorVersion == null && compare.getRequestorVersion() == null) || this.requestorVersion.equals(compare.getRequestorVersion());
		boolean body = (this.requestBody == null && compare.getRequestBody() == null) || this.requestBody.equals(compare.getRequestBody());
		
		return request && version && body;
	}
	
}

