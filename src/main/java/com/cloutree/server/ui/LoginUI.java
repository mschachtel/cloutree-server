
/**
 * Copyright 2013 Marc Schachtel, Germany
 */

package com.cloutree.server.ui;

import java.io.File;

import com.cloutree.server.permission.UserManager;
import com.cloutree.server.session.ClouTreeSession;
import com.cloutree.server.ui.navigation.Frames;
import com.cloutree.server.ui.navigation.NavigationRegistry;
import com.cloutree.server.ui.navigation.Views;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

/**
 * LoginUI
 *
 * @author marc
 *
 * Since 09.07.2013
 */

public class LoginUI extends CustomComponent implements Button.ClickListener, View {

    /*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

    @AutoGenerated
	private AbsoluteLayout mainLayout;

	@AutoGenerated
	private HorizontalLayout screenLayout;

	@AutoGenerated
	private VerticalLayout centerLayout;

	@AutoGenerated
	private GridLayout loginLayout;

	@AutoGenerated
	private HorizontalLayout buttonLayout;

	@AutoGenerated
	private Button loginButton;

	@AutoGenerated
	private CheckBox keeploggedinLabel;

	@AutoGenerated
	private TextField tenantTextField;

	@AutoGenerated
	private PasswordField passTextField;

	@AutoGenerated
	private TextField userTextField;

	@AutoGenerated
	private HorizontalLayout horizontalLayout_1;

	@AutoGenerated
	private Embedded logoImage;

	@AutoGenerated
	private MenuBar menuBar;

	private static final long serialVersionUID = 1L;
    
    /**
     * The constructor should first build the main layout, set the
     * composition root and then do any custom initialization.
     *
     * The constructor will not be automatically regenerated by the
     * visual editor.
     */
    public LoginUI() {
		buildMainLayout();
		setCompositionRoot(mainLayout);
		
		// IMPLEMENTATION
		this.init();
	
    }
    
    private void init() {
    	// First, start a session if not already started.
		VaadinService.getCurrentRequest().getWrappedSession(true);
		
		// Set Logo
		// Find the application directory
		String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
		FileResource resourceLogo = new FileResource(new File(basepath + "/WEB-INF/images/cloutree-logo.png"));
		logoImage.setSource(resourceLogo);
		
		/* MenuBar */
		// About
		MenuBar.MenuItem about = menuBar.addItem("About ClouTree", null);
		about.addItem("Version (TBD)", null);
		
		// Enter-Listener for login
		ShortcutListener enterListener = new ShortcutListener("ENTER", ShortcutAction.KeyCode.ENTER, null) {
	
		    private static final long serialVersionUID = 1L;
	
			@Override
			public void handleAction(Object sender, Object target) {
				if(target==passTextField || target == userTextField || target == tenantTextField || target == keeploggedinLabel){
					buttonClick(null);
				}
			}
		};
		
		this.userTextField.addShortcutListener(enterListener);
		this.passTextField.addShortcutListener(enterListener);
		
		this.userTextField.setInputPrompt("Username");
		this.passTextField.setInputPrompt("Password");
		this.tenantTextField.setInputPrompt("Tenant");
		
		String prevUser = ClouTreeSession.getPreviousUserName();
		String prevTenant = ClouTreeSession.getPreviousTenant();
		
		if(prevTenant != null) {
			this.tenantTextField.setValue(prevTenant);
		}
		
		if(prevUser != null) {
		    this.userTextField.setValue(prevUser);
		    this.passTextField.focus();
		} else {
		    this.userTextField.focus();
		}
		
		String rememberMe = ClouTreeSession.wasRememberMe();
		if(rememberMe != null && rememberMe.equals("true")) {
		    this.keeploggedinLabel.setValue(true);
		}
		
		this.loginButton.addClickListener(this);
		
		if(UserManager.isLoggedIn()) {
		    buttonClick(null);
		}
    }
    
    @Override
    public void buttonClick(ClickEvent event) {
	
		Navigator navigator;
		try {
		    navigator = NavigationRegistry.getNavigator(Frames.MAIN);
		} catch (Exception e) {
		    Notification.show(e.getLocalizedMessage(), Notification.Type.ERROR_MESSAGE);
		    return;
		}
		
		if(UserManager.isLoggedIn()) {
		    navigator.navigateTo(Views.MAIN.name());
		} else if(UserManager.login(this.userTextField.getValue(), this.tenantTextField.getValue(), this.passTextField.getValue(), this.keeploggedinLabel.getValue())){
		    navigator.navigateTo(Views.MAIN.name());
		    Notification.show("Welcome back, " + UserManager.getCurrentUser().getFirstname(), Notification.Type.TRAY_NOTIFICATION); 
		} else {
		    Notification.show("Error logging in, please check your credentials", Notification.Type.WARNING_MESSAGE);
		}
		
    }

    @Override
    public void enter(ViewChangeEvent event) {

    	// Nothing to do here
    	
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
		
		// menuBar
		menuBar = new MenuBar();
		menuBar.setImmediate(false);
		menuBar.setWidth("100.0%");
		menuBar.setHeight("-1px");
		mainLayout.addComponent(menuBar, "top:0.0px;right:0.0px;left:0.0px;");
		
		// screenLayout
		screenLayout = buildScreenLayout();
		mainLayout.addComponent(screenLayout,
				"top:22.0px;right:0.0px;bottom:0.0px;left:0.0px;");
		
		return mainLayout;
	}

	@AutoGenerated
	private HorizontalLayout buildScreenLayout() {
		// common part: create layout
		screenLayout = new HorizontalLayout();
		screenLayout.setImmediate(false);
		screenLayout.setWidth("100.0%");
		screenLayout.setHeight("100.0%");
		screenLayout.setMargin(false);
		
		// centerLayout
		centerLayout = buildCenterLayout();
		screenLayout.addComponent(centerLayout);
		screenLayout.setComponentAlignment(centerLayout, new Alignment(48));
		
		return screenLayout;
	}

	@AutoGenerated
	private VerticalLayout buildCenterLayout() {
		// common part: create layout
		centerLayout = new VerticalLayout();
		centerLayout.setImmediate(false);
		centerLayout.setWidth("-1px");
		centerLayout.setHeight("250px");
		centerLayout.setMargin(false);
		
		// horizontalLayout_1
		horizontalLayout_1 = buildHorizontalLayout_1();
		centerLayout.addComponent(horizontalLayout_1);
		centerLayout.setComponentAlignment(horizontalLayout_1,
				new Alignment(48));
		
		// loginLayout
		loginLayout = buildLoginLayout();
		centerLayout.addComponent(loginLayout);
		
		return centerLayout;
	}

	@AutoGenerated
	private HorizontalLayout buildHorizontalLayout_1() {
		// common part: create layout
		horizontalLayout_1 = new HorizontalLayout();
		horizontalLayout_1.setImmediate(false);
		horizontalLayout_1.setWidth("-1px");
		horizontalLayout_1.setHeight("-1px");
		horizontalLayout_1.setMargin(false);
		
		// logoImage
		logoImage = new Embedded();
		logoImage.setImmediate(false);
		logoImage.setWidth("-1px");
		logoImage.setHeight("-1px");
		logoImage
				.setSource(new ThemeResource("img/component/embedded_icon.png"));
		logoImage.setType(1);
		logoImage.setMimeType("image/jpg");
		horizontalLayout_1.addComponent(logoImage);
		
		return horizontalLayout_1;
	}

	@AutoGenerated
	private GridLayout buildLoginLayout() {
		// common part: create layout
		loginLayout = new GridLayout();
		loginLayout.setImmediate(false);
		loginLayout.setWidth("-1px");
		loginLayout.setHeight("100px");
		loginLayout.setMargin(false);
		loginLayout.setSpacing(true);
		loginLayout.setColumns(2);
		loginLayout.setRows(2);
		
		// userTextField
		userTextField = new TextField();
		userTextField.setCaption("Username");
		userTextField.setImmediate(false);
		userTextField.setWidth("220px");
		userTextField.setHeight("-1px");
		userTextField.setTabIndex(1);
		userTextField.setRequired(true);
		loginLayout.addComponent(userTextField, 0, 0);
		loginLayout.setComponentAlignment(userTextField, new Alignment(33));
		
		// passTextField
		passTextField = new PasswordField();
		passTextField.setCaption("Password");
		passTextField.setImmediate(false);
		passTextField.setWidth("220px");
		passTextField.setHeight("-1px");
		passTextField.setTabIndex(3);
		passTextField.setRequired(true);
		loginLayout.addComponent(passTextField, 1, 0);
		loginLayout.setComponentAlignment(passTextField, new Alignment(33));
		
		// tenantTextField
		tenantTextField = new TextField();
		tenantTextField.setCaption("Tenant");
		tenantTextField.setImmediate(false);
		tenantTextField.setWidth("220px");
		tenantTextField.setHeight("-1px");
		tenantTextField.setTabIndex(2);
		tenantTextField.setRequired(true);
		loginLayout.addComponent(tenantTextField, 0, 1);
		loginLayout.setComponentAlignment(tenantTextField, new Alignment(33));
		
		// buttonLayout
		buttonLayout = buildButtonLayout();
		loginLayout.addComponent(buttonLayout, 1, 1);
		loginLayout.setComponentAlignment(buttonLayout, new Alignment(10));
		
		return loginLayout;
	}

	@AutoGenerated
	private HorizontalLayout buildButtonLayout() {
		// common part: create layout
		buttonLayout = new HorizontalLayout();
		buttonLayout.setImmediate(false);
		buttonLayout.setWidth("100.0%");
		buttonLayout.setHeight("-1px");
		buttonLayout.setMargin(false);
		
		// keeploggedinLabel
		keeploggedinLabel = new CheckBox();
		keeploggedinLabel.setCaption("Remember me!");
		keeploggedinLabel.setImmediate(false);
		keeploggedinLabel.setWidth("-1px");
		keeploggedinLabel.setHeight("-1px");
		keeploggedinLabel.setTabIndex(4);
		buttonLayout.addComponent(keeploggedinLabel);
		buttonLayout
				.setComponentAlignment(keeploggedinLabel, new Alignment(33));
		
		// loginButton
		loginButton = new Button();
		loginButton.setStyleName("v-button");
		loginButton.setCaption("Login");
		loginButton.setImmediate(true);
		loginButton.setWidth("100px");
		loginButton.setHeight("-1px");
		loginButton.setTabIndex(5);
		buttonLayout.addComponent(loginButton);
		buttonLayout.setComponentAlignment(loginButton, new Alignment(6));
		
		return buttonLayout;
	}

}
