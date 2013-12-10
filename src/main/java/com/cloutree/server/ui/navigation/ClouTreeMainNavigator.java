
/**
 * Copyright 2013 Marc Schachtel, Germany
 */

package com.cloutree.server.ui.navigation;

import com.cloutree.server.ui.ErrorUI;
import com.cloutree.server.ui.LoginUI;
import com.cloutree.server.ui.NavigationViewUI;
import com.vaadin.navigator.NavigationStateManager;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.SingleComponentContainer;
import com.vaadin.ui.UI;

/**
 * ClouTreeMainNavigator
 *
 * @author marc
 *
 * Since 19.07.2013
 */
public class ClouTreeMainNavigator extends Navigator {

    private static final long serialVersionUID = 1L;
    
    public ClouTreeMainNavigator(UI ui, NavigationStateManager stateManager,
	    ViewDisplay display) {
		super(ui, stateManager, display);
		this.registerViews();
    }

    public ClouTreeMainNavigator(UI ui, SingleComponentContainer container) {
		super(ui, container);
		this.registerViews();
    }

    public ClouTreeMainNavigator(UI ui, ViewDisplay display) {
		super(ui, display);
		this.registerViews();
    }

    public ClouTreeMainNavigator(UI ui, ComponentContainer container) {
		super(ui, container);
		this.registerViews();
    }
    
    private void registerViews() {
		this.addView(Views.ERROR.name(), ErrorUI.class);
		this.addView(Views.LOGIN.name(), LoginUI.class);
		this.addView(Views.MAIN.name(), NavigationViewUI.class);
    }
    
}
