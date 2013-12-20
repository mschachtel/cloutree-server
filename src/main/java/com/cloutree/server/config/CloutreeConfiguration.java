
/**
 * Copyright 2013 Marc Schachtel, Germany
 */

package com.cloutree.server.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class CloutreeConfiguration {

	private static Properties props;
	
	private static String version;
	
	private static boolean initiated = false;
	
	public static void init() throws FileNotFoundException, IOException {

		String fileName = "cloutree.properties" ; 
		InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
		
		props = new Properties();
		props.load(stream);
		
		version = props.getProperty("server.version");
		
		initiated = true;
		
	}
	
	public static String getVersion() {
		
		if(!initiated) {
			try {
				init();
			} catch (FileNotFoundException e) {
				return null;
			} catch (IOException e) {
				return null;
			}
		}
		
		return version;
	}
	
}

