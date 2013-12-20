
/**
 * Copyright 2013 Marc Schachtel, Germany
 */

package com.cloutree.server.ui.navigation;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.vaadin.navigator.Navigator;

/**
 * NavigationController
 *
 * @author marc
 *
 * Since 28.08.2013
 */
public class NavigationController {

    static Logger log = Logger.getLogger(NavigationController.class.getName());
    
    public static void navigateToModel(String modelName) {
	
		Navigator navigator;
		
		try {
		    navigator = NavigationRegistry.getNavigator(Frames.MAIN);
		} catch (Exception e) {
		    log.log(Level.SEVERE, e.getMessage());
		    return;
		}
		
		if(navigator == null) {
		    return;
		}
		
		if(modelName == null) {
		    navigator.navigateTo(Views.MAIN.name());
		} else {
		    navigator.navigateTo(Views.MAIN.name() + "/MODEL/" + modelName);
		}
	 }
	    
	 public static void openUserNavigation() {
		
		Navigator navigator;
		
		try {
		    navigator = NavigationRegistry.getNavigator(Frames.MAIN);
		} catch (Exception e) {
		    log.log(Level.SEVERE, e.getMessage());
		    return;
		}
		
		if(navigator == null) {
		    return;
		}
		
		navigator.navigateTo(Views.MAIN.name() + "/ADMIN/USERADMIN");
	
     }
	 
	 public static void openApiNavigation() {
			
		Navigator navigator;
		
		try {
		    navigator = NavigationRegistry.getNavigator(Frames.MAIN);
		} catch (Exception e) {
		    log.log(Level.SEVERE, e.getMessage());
		    return;
		}
		
		if(navigator == null) {
		    return;
		}
		
		navigator.navigateTo(Views.MAIN.name() + "/ADMIN/APIADMIN");
	
     }
    
}
