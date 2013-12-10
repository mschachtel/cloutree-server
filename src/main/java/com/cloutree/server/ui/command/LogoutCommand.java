
/**
 * Copyright 2013 Marc Schachtel, Germany
 */

package com.cloutree.server.ui.command;

import com.cloutree.server.permission.UserManager;
import com.cloutree.server.ui.navigation.Frames;
import com.cloutree.server.ui.navigation.NavigationRegistry;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Notification;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;

/**
 * LogoutCommand
 *
 * @author marc
 *
 * Since 18.07.2013
 */
public class LogoutCommand implements Command {

    private static final long serialVersionUID = 1L;

    @Override
    public void menuSelected(MenuItem selectedItem) {
	
	UserManager.logout();
	try {
	    Navigator navigator = NavigationRegistry.getNavigator(Frames.MAIN);
	    navigator.getUI().getPage().reload();
	    Notification.show("Successfully logged out", Notification.Type.TRAY_NOTIFICATION);
	} catch (Exception e) {
	    Notification.show(e.getLocalizedMessage(), Notification.Type.ERROR_MESSAGE);
	}
	
    }

}
