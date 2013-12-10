
/**
 * Copyright 2013 Marc Schachtel, Germany
 */

package com.cloutree.server.ui.command;

import com.vaadin.server.ExternalResource;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * LogoutCommand
 *
 * @author marc
 *
 * Since 18.07.2013
 */
public class ShowAPIDocCommand implements Command {

    private static final long serialVersionUID = 1L;

    @Override
    public void menuSelected(MenuItem selectedItem) {
	
		VerticalLayout layout = new VerticalLayout();
		layout.setMargin(false);
		layout.setSpacing(false);
		layout.setSizeFull();
		
		Window dialog = new Window("Client API Documentation");
		dialog.center();
		dialog.setModal(true);
		dialog.setHeight("500px");
		dialog.setWidth("600px");
		dialog.setResizable(true);
		
		// TODO API Documentation on ClouTree Site
		BrowserFrame browser = new BrowserFrame("Client API Documentation", new ExternalResource("http://www.google.com/"));
		browser.setWidth("100%");
		browser.setHeight("100%");
		layout.addComponent(browser);
		
		dialog.setContent(layout);
		UI.getCurrent().addWindow(dialog);
	
    }

}
