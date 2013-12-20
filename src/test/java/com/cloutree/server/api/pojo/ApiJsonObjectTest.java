
/**
 * Copyright 2013 Marc Schachtel, Germany
 */

package com.cloutree.server.api.pojo;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import flexjson.JSONDeserializer;

/**
 * ApiJsonObjectTest
 *
 * @author mschachtel
 *
 */

public class ApiJsonObjectTest {

	/**
	 * Test method for {@link com.cloutree.server.api.pojo.ApiJsonObject#toJson()}.
	 */
	@Test
	public void testToJson() {
		InstanceActiveModels instanceActiveModels = new InstanceActiveModels();
		List<ActiveModel> activeModels = new LinkedList<ActiveModel>();
		
		instanceActiveModels.setInstanceName("TEST-INSTANCE");
		instanceActiveModels.setActiveModels(activeModels);
		instanceActiveModels.setTimestamp(new Date());
		
		ApiJsonObject apiObject = new ApiJsonObject();
		
		apiObject.setRequestBody(instanceActiveModels);
		apiObject.setRequestName("Test-Request");
		
		String json = apiObject.toJson();
		
		Assert.assertTrue(json != null);
		
		ApiJsonObject apiObject2 = new JSONDeserializer<ApiJsonObject>().deserialize(json);
		
		Assert.assertTrue(apiObject.equals(apiObject2));
	}

}

