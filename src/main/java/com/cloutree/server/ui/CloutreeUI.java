
/**
 * Copyright 2013 Marc Schachtel, Germany
 */

package com.cloutree.server.ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.cloutree.server.config.CloutreeConfiguration;
import com.cloutree.server.permission.UserManager;
import com.cloutree.server.ui.navigation.ClouTreeMainNavigator;
import com.cloutree.server.ui.navigation.Frames;
import com.cloutree.server.ui.navigation.NavigationRegistry;
import com.cloutree.server.ui.navigation.Views;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

/**
 * ClouTreeUI
 *
 * @author marc
 *
 * Since 09.07.2013
 */
@Theme("reindeer")
@SuppressWarnings("serial")
public class CloutreeUI extends UI {
    
	static Logger log = Logger.getLogger(CloutreeUI.class.getName());
	
	@Override
	protected void init(VaadinRequest request) {
	    
		try {
			CloutreeConfiguration.init();
		} catch (FileNotFoundException e) {
			log.log(Level.SEVERE, e.getLocalizedMessage());
			return;
		} catch (IOException e) {
			log.log(Level.SEVERE, e.getLocalizedMessage());
			return;
		}
		
	    this.getPage().setTitle("cloutree - realtime scoring engine");
	    
	    ClouTreeMainNavigator navigator = new ClouTreeMainNavigator(this, this);
	    NavigationRegistry.registerNavigator(Frames.MAIN, navigator);
	    
	    if(UserManager.isLoggedIn()){
	    	navigator.navigateTo(Views.MAIN.name());
	    } else {
	    	navigator.navigateTo(Views.LOGIN.name());
	    }
	}

}