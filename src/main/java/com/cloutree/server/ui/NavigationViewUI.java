
/**
 * Copyright 2013 Marc Schachtel, Germany
 */

package com.cloutree.server.ui;

import com.cloutree.server.config.CloutreeConfiguration;
import com.cloutree.server.permission.Permissions;
import com.cloutree.server.permission.UIPermissionManager;
import com.cloutree.server.permission.UserManager;
import com.cloutree.server.persistence.entity.User;
import com.cloutree.server.ui.command.LogoutCommand;
import com.cloutree.server.ui.command.ShowAPIDocCommand;
import com.cloutree.server.ui.navigation.Frames;
import com.cloutree.server.ui.navigation.NavigationRegistry;
import com.cloutree.server.ui.navigation.Views;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Notification;

/**
 * NavigationViewUI
 *
 * @author marc
 *
 * Since 18.07.2013
 */
public class NavigationViewUI extends CustomComponent implements View {

    /*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

    @AutoGenerated
    private AbsoluteLayout mainLayout;

    @AutoGenerated
    private WorkBenchSplitViewUI workBenchSplitViewUI;

    @AutoGenerated
    private MenuBar mainMenuBar;

    private static final long serialVersionUID = 1L;
    
    /**
     * The constructor should first build the main layout, set the
     * composition root and then do any custom initialization.
     *
     * The constructor will not be automatically regenerated by the
     * visual editor.
     */
    public NavigationViewUI() {
		buildMainLayout();
		setCompositionRoot(mainLayout);
		
		/* Menu Bar */
		// User Menu
		User currentUser = UserManager.getCurrentUser();
		if(currentUser != null) { 
		    MenuBar.MenuItem userMenu = mainMenuBar.addItem(currentUser.getFullName(),  null);
		    userMenu.addItem("Logout", new LogoutCommand());
		}
		
		// Developer Menu
		if(currentUser.getPermissionList().contains(Permissions.DEVELOPER)) {
		    
		    // TODO Commands
		    MenuBar.MenuItem devMenu = mainMenuBar.addItem("Developer",  null);
		    
		    MenuBar.MenuItem apiMenu = devMenu.addItem("API",  null);
		    apiMenu.addItem("JSON API", null);
		    apiMenu.addItem("Java API", new ShowAPIDocCommand());
		    
		    devMenu.addItem("System Stats", null);
		    
		}
		
		// Help Menu
		MenuBar.MenuItem helpMenu = mainMenuBar.addItem("About ClouTree",  null);
		helpMenu.addItem("User Documentation / Handbook", null);
		helpMenu.addItem("Version: " + CloutreeConfiguration.getVersion(), null);
	
    }

    @Override
    public void enter(ViewChangeEvent event) {
	
		if(!UIPermissionManager.getInstance().hasPermission(UserManager.getCurrentUser(), this.getClass())){
		    Notification.show("No Permission to enter this view", Notification.Type.ERROR_MESSAGE);
		    event.getNavigator().navigateTo(Views.ERROR.name());
		}
		
		if(!UserManager.isLoggedIn()) {
		    try {
			Navigator navigator = NavigationRegistry.getNavigator(Frames.MAIN);
			navigator.navigateTo(Views.LOGIN.name());
		    } catch (Exception e) {
			this.setEnabled(false);
			Notification.show(e.getLocalizedMessage(), Notification.Type.ERROR_MESSAGE);
		    }
		}
		
		if (event.getParameters() == null || event.getParameters().isEmpty()) {
		    return;
		}
		
		String[] parameters = event.getParameters().split("/");
		this.workBenchSplitViewUI.initViews(parameters);
		
    }

    @AutoGenerated
    private AbsoluteLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new AbsoluteLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("100%");
		mainLayout.setHeight("100%");
		
		// top-level component properties
		setWidth("100.0%");
		setHeight("100.0%");
		
		// mainMenuBar
		mainMenuBar = new MenuBar();
		mainMenuBar.setImmediate(false);
		mainMenuBar.setWidth("100.0%");
		mainMenuBar.setHeight("-1px");
		mainLayout.addComponent(mainMenuBar,
			"top:0.0px;right:0.0px;left:0.0px;");
		
		// workBenchSplitViewUI
		workBenchSplitViewUI = new WorkBenchSplitViewUI();
		workBenchSplitViewUI.setImmediate(false);
		workBenchSplitViewUI.setWidth("100.0%");
		workBenchSplitViewUI.setHeight("100.0%");
		mainLayout.addComponent(workBenchSplitViewUI,
			"top:40.0px;right:20.0px;bottom:20.0px;left:20.0px;");
		
		return mainLayout;
    }

}