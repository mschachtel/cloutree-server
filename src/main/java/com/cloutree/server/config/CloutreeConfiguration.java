
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
	private static boolean initiated = false;
	
	/** PROPERTIES KEYS **/
	public static String SERVER_VERSION = "server.version";
	public static String SERVER_STORAGE_PATH = "server.storage.path";
	
	public static void init() throws FileNotFoundException, IOException {

		String fileName = "cloutree.properties" ; 
		InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
		
		props = new Properties();
		props.load(stream);
		
		initiated = true;
		
	}
	
	private static void hopeInit() {
		try {
			init();
		} catch (FileNotFoundException e) {
			log.log(Level.SEVERE, e.getMessage());
			return;
		} catch (IOException e) {
			log.log(Level.SEVERE, e.getMessage());
			return;
		}
	}
	
	public static String getProperty(String key) {
		if(!initiated) hopeInit();
		return props.getProperty(key);
	}
	
}

