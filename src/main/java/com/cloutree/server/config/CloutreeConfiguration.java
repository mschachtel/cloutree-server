
/**
 * Copyright 2013 Marc Schachtel, Germany
 */

package com.cloutree.server.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


public class CloutreeConfiguration {
	
	static Logger log = Logger.getLogger(CloutreeConfiguration.class.getName());

	private static Properties props;
	
	private static String version;
	
	private static boolean initiated = false;
	
	public static void init() throws FileNotFoundException, IOException {

		String fileName = "/cloutree.properties"; 
		InputStream stream = CloutreeConfiguration.class.getResourceAsStream(fileName);
		
		if(stream == null) {
			throw new FileNotFoundException("Stream seems to be null pointing at config-file " + fileName + ". Unable to initiate configuration!");
		}
		
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
				log.log(Level.SEVERE, "Configuration not found: " + e.getMessage());
				return null;
			} catch (IOException e) {
				log.log(Level.SEVERE, "Configuration not found: " + e.getMessage());
				return null;
			}
		}
		
		return version;
	}
	
}

