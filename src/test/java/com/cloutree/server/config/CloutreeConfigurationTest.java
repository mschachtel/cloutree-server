
/**
 * Copyright 2013 Marc Schachtel, Germany
 */

package com.cloutree.server.config;

import java.io.FileNotFoundException;
import java.io.IOException;

import junit.framework.Assert;

import org.junit.Test;

/**
 * CloutreeConfigurationTest
 *
 * @author mschachtel
 *
 */

public class CloutreeConfigurationTest {

	/**
	 * Test method for {@link com.cloutree.server.config.CloutreeConfiguration#init()}.
	 */
	@Test
	public void canBeInitiated(){
		
		try {
			CloutreeConfiguration.init();
		} catch (FileNotFoundException e) {
			Assert.fail(e.getMessage());
			return;
		} catch (IOException e) {
			Assert.fail(e.getMessage());
			return;
		}
		
	}
	
	/**
	 * Test method for {@link com.cloutree.server.config.CloutreeConfiguration#getVersion()}.
	 */
	@Test
	public void returnsVersion(){
		try {
			CloutreeConfiguration.init();
		} catch (FileNotFoundException e) {
			Assert.fail(e.getMessage());
			return;
		} catch (IOException e) {
			Assert.fail(e.getMessage());
			return;
		}
		
		String version = CloutreeConfiguration.getProperty(CloutreeConfiguration.SERVER_VERSION);
		
		Assert.assertTrue(version != null && !version.isEmpty());
		
	}
	
}

