
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
 * InstanceActiveModelsTest
 *
 * @author mschachtel
 *
 */

public class InstanceActiveModelsTest {

	/**
	 * Test method for {@link com.cloutree.server.api.pojo.InstanceActiveModels#toJson()}.
	 */
	@Test
	public void testToJson() {

		InstanceActiveModels instanceActiveModels = new InstanceActiveModels();
		List<ActiveModel> activeModels = new LinkedList<ActiveModel>();
		
		instanceActiveModels.setInstanceName("TEST-INSTANCE");
		instanceActiveModels.setActiveModels(activeModels);
		instanceActiveModels.setTimestamp(new Date());
		
		String json = instanceActiveModels.toJson();
		
		Assert.assertTrue(json != null);
		
		InstanceActiveModels instanceActiveModels2 = new JSONDeserializer<InstanceActiveModels>().deserialize(json);
		
		Assert.assertTrue(instanceActiveModels.equals(instanceActiveModels2));
		
	}

}