
/**
 * Copyright 2013 Marc Schachtel, Germany
 */

package com.cloutree.server.ui;

import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.cloutree.server.permission.UserManager;
import com.cloutree.server.persistence.entity.Instance;
import com.cloutree.server.persistence.entity.Tenant;
import com.cloutree.server.session.ClouTreeSession;
import com.cloutree.server.ui.admin.ApiAdministration;
import com.cloutree.server.ui.admin.UserAdministration;
import com.cloutree.server.ui.model.ModelEditorUI;
import com.cloutree.server.ui.model.ModelNavigation;
import com.cloutree.server.ui.navigation.NavigationController;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

/**
 * WorkBenchSplitUI
 *
 * @author marc
 *
 * Since 22.07.2013
 */
public class WorkBenchSplitViewUI extends CustomComponent implements View, Button.ClickListener {

    /*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

    @AutoGenerated
	private AbsoluteLayout mainLayout;

	@AutoGenerated
	private HorizontalLayout splitLayout;

	@AutoGenerated
	private AbsoluteLayout viewLayoutRoot;

	@AutoGenerated
	private VerticalLayout viewLayout;

	@AutoGenerated
	private Embedded embeddedLogo;

	@AutoGenerated
	private VerticalLayout navigationLayout;

	@AutoGenerated
	private TabSheet navTabs;

	@AutoGenerated
	private AbsoluteLayout tabAdministration;

	@AutoGenerated
	private VerticalLayout adminButtonLayout;

	@AutoGenerated
	private Button buttonApiAdmin;

	@AutoGenerated
	private Button buttonUserAdmin;

	@AutoGenerated
	private AbsoluteLayout tabModelNavigator;

	@AutoGenerated
	private ModelNavigation modelNavigation_1;

	@AutoGenerated
	private NativeSelect instanceSelect;

	@AutoGenerated
	private Embedded embeddedTenantLogo;

	static Logger log = Logger.getLogger(WorkBenchSplitViewUI.class.getName());
    
    private static final long serialVersionUID = 1L;
    
    /**
     * The constructor should first build the main layout, set the
     * composition root and then do any custom initialization.
     *
     * The constructor will not be automatically regenerated by the
     * visual editor.
     */
    public WorkBenchSplitViewUI() {
		buildMainLayout();
		setCompositionRoot(mainLayout);
	
		//IMPLEMENTATION
		if(!UserManager.getCurrentUser().hasWritePermission()) {
		    this.navTabs.removeTab(this.navTabs.getTab(1));
		}
		
		this.buttonUserAdmin.addClickListener(this);
		this.buttonApiAdmin.addClickListener(this);
		
		// Set Cloutree-Logo
		// Find the application directory
		String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
		FileResource resourceCloutreeLogo = new FileResource(new File(basepath + "/WEB-INF/images/cloutree-logo.png"));
		embeddedLogo.setSource(resourceCloutreeLogo);
		
		// Set TenantLogo
		FileResource resourceTenantLogo = new FileResource(new File(basepath + "/WEB-INF/storage/tenant/" + ClouTreeSession.getTenantName() + "/logo-" + ClouTreeSession.getTenantName() + ".png"));
		if(!resourceTenantLogo.getSourceFile().exists()) {
			resourceTenantLogo = new FileResource(new File(basepath + "/WEB-INF/storage/tenant/DEFAULT/logo.png"));
		}
		embeddedTenantLogo.setSource(resourceTenantLogo);
		
		this.initInstances();
	
    }
    
    private void initInstances() {
    	
    	Tenant tenant = ClouTreeSession.getUser().getTenant();
    	this.instanceSelect.setMultiSelect(false);
    	this.instanceSelect.setImmediate(true);
    	this.instanceSelect.setNullSelectionAllowed(false);
    	
    	List<Instance> instances = tenant.getInstances();;

    	if(instances != null && instances.size() > 0) {
	    	for(Instance instance : instances) {
	    		this.instanceSelect.addItem(instance);
	    	}
	    	
	    	Instance inst = ClouTreeSession.getInstance();
	    	if(inst != null && this.instanceSelect.containsId(inst)) {
	    		this.instanceSelect.select(inst);
	    	} else {
	    		this.instanceSelect.select(instances.get(0));
	    		ClouTreeSession.setInstance(instances.get(0));
	    	}
	    	
	    	this.instanceSelect.addValueChangeListener(
	    		    new Property.ValueChangeListener() {
						
						private static final long serialVersionUID = 1L;

						public void valueChange(ValueChangeEvent event) {
		    		        Instance instance = (Instance)event.getProperty().getValue();

		    		        ClouTreeSession.setInstance(instance);
		    		        NavigationController.navigateToModel(null);
		    		    }
					}
	    	);
	    	
	    	
	    	
	    	this.modelNavigation_1.initModels();
    	}
    	
    }
    
    public void initViews(String[] parameters) {
	
		if(parameters[0].equals("MODEL")) {
		    this.navTabs.setSelectedTab(0);
		    if(parameters.length > 1) {
			this.initModelView(parameters[1]);
		    }
		} else if(parameters[0].equals("ADMIN")) {
		    this.navTabs.setSelectedTab(1);
		    if(parameters.length > 1 && parameters[1].equals("USERADMIN")) {
		    	this.initUserAdmin();
		    }
		    if(parameters.length > 1 && parameters[1].equals("APIADMIN")) {
		    	this.initApiAdmin();
		    }
		}
		
    }
    
    private void initUserAdmin() {
		UserAdministration userAdmin = new UserAdministration();
		try {
		    userAdmin.setHeight("100%");
		    userAdmin.setWidth("100%");
		    this.viewLayout.removeAllComponents();
		    this.viewLayout.addComponent(userAdmin);
		    
		} catch (Exception e) {
		    log.log(Level.SEVERE, e.getMessage());
		}
    }
    
    private void initApiAdmin() {
		ApiAdministration apiAdmin = new ApiAdministration();
		try {
		    apiAdmin.setHeight("100%");
		    apiAdmin.setWidth("100%");
		    this.viewLayout.removeAllComponents();
		    this.viewLayout.addComponent(apiAdmin);
		    
		} catch (Exception e) {
		    log.log(Level.SEVERE, e.getMessage());
		}
    }
    
    private void initModelView(String modelName) {
	
    	ModelEditorUI modelEditor = new ModelEditorUI();
    	
    	if(modelName == null) {
    		return;
    	}
    	
		try {
		    modelEditor.init(modelName);
		    this.modelNavigation_1.setSelectedModel(modelName);
		    modelEditor.setHeight("100%");
		    modelEditor.setWidth("100%");
		    this.viewLayout.removeAllComponents();
		    this.viewLayout.addComponent(modelEditor);
		    
		} catch (Exception e) {
		    log.log(Level.SEVERE, e.getMessage());
		}
    }

    /* (non-Javadoc)
     * @see com.vaadin.navigator.View#enter(com.vaadin.navigator.ViewChangeListener.ViewChangeEvent)
     */
    @Override
    public void enter(ViewChangeEvent event) {
		
    }

    /* (non-Javadoc)
     * @see com.vaadin.ui.Button.ClickListener#buttonClick(com.vaadin.ui.Button.ClickEvent)
     */
    @Override
    public void buttonClick(ClickEvent event) {
		if(event.getButton().equals(this.buttonUserAdmin)) {
		    NavigationController.openUserNavigation();
		}
		if(event.getButton().equals(this.buttonApiAdmin)) {
		    NavigationController.openApiNavigation();
		}
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
		
		// splitLayout
		splitLayout = buildSplitLayout();
		mainLayout.addComponent(splitLayout,
				"top:0.0px;right:0.0px;bottom:0.0px;left:0.0px;");
		
		return mainLayout;
	}

	@AutoGenerated
	private HorizontalLayout buildSplitLayout() {
		// common part: create layout
		splitLayout = new HorizontalLayout();
		splitLayout.setImmediate(false);
		splitLayout.setWidth("100.0%");
		splitLayout.setHeight("100.0%");
		splitLayout.setMargin(false);
		
		// navigationLayout
		navigationLayout = buildNavigationLayout();
		splitLayout.addComponent(navigationLayout);
		
		// viewLayoutRoot
		viewLayoutRoot = buildViewLayoutRoot();
		splitLayout.addComponent(viewLayoutRoot);
		splitLayout.setExpandRatio(viewLayoutRoot, 1.0f);
		
		return splitLayout;
	}

	@AutoGenerated
	private VerticalLayout buildNavigationLayout() {
		// common part: create layout
		navigationLayout = new VerticalLayout();
		navigationLayout.setImmediate(false);
		navigationLayout.setWidth("-1px");
		navigationLayout.setHeight("100.0%");
		navigationLayout.setMargin(false);
		navigationLayout.setSpacing(true);
		
		// embeddedTenantLogo
		embeddedTenantLogo = new Embedded();
		embeddedTenantLogo.setImmediate(false);
		embeddedTenantLogo.setWidth("350px");
		embeddedTenantLogo.setHeight("80px");
		embeddedTenantLogo.setSource(new ThemeResource(
				"img/component/embedded_icon.png"));
		embeddedTenantLogo.setType(1);
		embeddedTenantLogo.setMimeType("image/png");
		navigationLayout.addComponent(embeddedTenantLogo);
		
		// instanceSelect
		instanceSelect = new NativeSelect();
		instanceSelect.setImmediate(false);
		instanceSelect.setWidth("100.0%");
		instanceSelect.setHeight("-1px");
		navigationLayout.addComponent(instanceSelect);
		
		// navTabs
		navTabs = buildNavTabs();
		navigationLayout.addComponent(navTabs);
		navigationLayout.setExpandRatio(navTabs, 1.0f);
		
		return navigationLayout;
	}

	@AutoGenerated
	private TabSheet buildNavTabs() {
		// common part: create layout
		navTabs = new TabSheet();
		navTabs.setImmediate(true);
		navTabs.setWidth("350px");
		navTabs.setHeight("100.0%");
		
		// tabModelNavigator
		tabModelNavigator = buildTabModelNavigator();
		navTabs.addTab(tabModelNavigator, "Models", null);
		
		// tabAdministration
		tabAdministration = buildTabAdministration();
		navTabs.addTab(tabAdministration, "Administration", null);
		
		return navTabs;
	}

	@AutoGenerated
	private AbsoluteLayout buildTabModelNavigator() {
		// common part: create layout
		tabModelNavigator = new AbsoluteLayout();
		tabModelNavigator.setImmediate(false);
		tabModelNavigator.setWidth("100.0%");
		tabModelNavigator.setHeight("100.0%");
		
		// modelNavigation_1
		modelNavigation_1 = new ModelNavigation();
		modelNavigation_1.setImmediate(false);
		modelNavigation_1.setWidth("100.0%");
		modelNavigation_1.setHeight("100.0%");
		tabModelNavigator.addComponent(modelNavigation_1,
				"top:5.0px;right:5.0px;bottom:5.0px;left:5.0px;");
		
		return tabModelNavigator;
	}

	@AutoGenerated
	private AbsoluteLayout buildTabAdministration() {
		// common part: create layout
		tabAdministration = new AbsoluteLayout();
		tabAdministration.setImmediate(false);
		tabAdministration.setWidth("100.0%");
		tabAdministration.setHeight("100.0%");
		
		// adminButtonLayout
		adminButtonLayout = buildAdminButtonLayout();
		tabAdministration.addComponent(adminButtonLayout,
				"top:0.0px;right:0.0px;left:0.0px;");
		
		return tabAdministration;
	}

	@AutoGenerated
	private VerticalLayout buildAdminButtonLayout() {
		// common part: create layout
		adminButtonLayout = new VerticalLayout();
		adminButtonLayout.setImmediate(false);
		adminButtonLayout.setWidth("100.0%");
		adminButtonLayout.setHeight("-1px");
		adminButtonLayout.setMargin(true);
		adminButtonLayout.setSpacing(true);
		
		// buttonUserAdmin
		buttonUserAdmin = new Button();
		buttonUserAdmin.setCaption("User Administration");
		buttonUserAdmin.setImmediate(true);
		buttonUserAdmin.setWidth("100.0%");
		buttonUserAdmin.setHeight("-1px");
		adminButtonLayout.addComponent(buttonUserAdmin);
		
		// buttonApiAdmin
		buttonApiAdmin = new Button();
		buttonApiAdmin.setCaption("API Administration");
		buttonApiAdmin.setImmediate(false);
		buttonApiAdmin.setWidth("100.0%");
		buttonApiAdmin.setHeight("-1px");
		adminButtonLayout.addComponent(buttonApiAdmin);
		
		return adminButtonLayout;
	}

	@AutoGenerated
	private AbsoluteLayout buildViewLayoutRoot() {
		// common part: create layout
		viewLayoutRoot = new AbsoluteLayout();
		viewLayoutRoot.setImmediate(false);
		viewLayoutRoot.setWidth("100.0%");
		viewLayoutRoot.setHeight("100.0%");
		
		// viewLayout
		viewLayout = buildViewLayout();
		viewLayoutRoot.addComponent(viewLayout,
				"top:0.0px;right:0.0px;bottom:0.0px;left:5.0px;");
		
		return viewLayoutRoot;
	}

	@AutoGenerated
	private VerticalLayout buildViewLayout() {
		// common part: create layout
		viewLayout = new VerticalLayout();
		viewLayout.setImmediate(false);
		viewLayout.setWidth("100.0%");
		viewLayout.setHeight("100.0%");
		viewLayout.setMargin(false);
		
		// embeddedLogo
		embeddedLogo = new Embedded();
		embeddedLogo.setImmediate(false);
		embeddedLogo.setWidth("-1px");
		embeddedLogo.setHeight("-1px");
		embeddedLogo.setSource(new ThemeResource(
				"img/component/embedded_icon.png"));
		embeddedLogo.setType(1);
		embeddedLogo.setMimeType("image/png");
		viewLayout.addComponent(embeddedLogo);
		viewLayout.setComponentAlignment(embeddedLogo, new Alignment(48));
		
		return viewLayout;
	}

}
