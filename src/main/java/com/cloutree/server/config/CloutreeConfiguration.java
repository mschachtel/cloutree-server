
/**
 * Copyright 2013 Marc Schachtel, Germany
 */

package com.cloutree.server.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.vaadin.server.VaadinService;

public class CloutreeConfiguration {

	private static Properties props;
	
	private static String version;
	
	public static void init() throws FileNotFoundException, IOException {
		String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
		
		props = new Properties();
		props.load(new FileInputStream(basepath + "/WEB-INF/cloutree.properties"));
		
		version = props.getProperty("server.version");
		
	}
	
	public static String getVersion() {
		return version;
	}
	
}

