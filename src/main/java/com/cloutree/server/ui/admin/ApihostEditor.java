
/**
 * Copyright 2013 Marc Schachtel, Germany
 */

package com.cloutree.server.ui.admin;

import java.util.logging.Logger;

import com.cloutree.server.permission.UserManager;
import com.cloutree.server.persistence.entity.Apihost;
import com.cloutree.server.persistence.service.ApihostService;
import com.cloutree.server.session.ClouTreeSession;
import com.cloutree.server.ui.navigation.NavigationController;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
/**
 * TODO
 *
 * @author marc
 *
 * Since 10.09.2013
 */
public class ApihostEditor extends CustomComponent implements View, Button.ClickListener {

    /*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

    @AutoGenerated
	private AbsoluteLayout mainLayout;

	@AutoGenerated
	private Panel apihostEditorPanel;

	@AutoGenerated
	private VerticalLayout apihostEditorPanelLayout;

	@AutoGenerated
	private Button buttonSave;

	@AutoGenerated
	private GridLayout apihostFieldGrid;

	@AutoGenerated
	private TextField textDescriptiom;

	@AutoGenerated
	private TextField textUrl;

	@AutoGenerated
	private TextField textApihostName;

	static Logger log = Logger.getLogger(ApihostEditor.class.getName());
    
    private static final long serialVersionUID = 1L;
    
    private Apihost host;
    
    /**
     * The constructor should first build the main layout, set the
     * composition root and then do any custom initialization.
     *
     * The constructor will not be automatically regenerated by the
     * visual editor.
     */
    public ApihostEditor() {
		buildMainLayout();
		setCompositionRoot(mainLayout);
	
		// Implementation
		
		if(!UserManager.getCurrentUser().hasWritePermission()) {
		    NavigationController.navigateToModel(null);
		}

		this.buttonSave.addClickListener(this);
    }

    public void setApihost(String name) {
    	ApihostService service = new ApihostService(ClouTreeSession.getInstance());
    	this.host = service.getApihost(name);	
    }
    
    public void init() {
	
		if(this.host == null) {
		    return;
		}
		
		this.textApihostName.setValue(this.host.getName());
		this.textDescriptiom.setValue(this.host.getDescription());
		this.textUrl.setValue(this.host.getUrl());
		
    }
    
    @Override
    public void enter(ViewChangeEvent event) {
    	// Nothing to do here?
    }

    @Override
    public void buttonClick(ClickEvent event) {
		
    	ApihostService service = new ApihostService(ClouTreeSession.getInstance());
    	
    	boolean newHost = false;
    	
    	if(this.host == null) {
    		this.host = new Apihost();
    		this.host.setStatus(0);
    		newHost = true;
    	}
    	
		this.host.setName(this.textApihostName.getValue());
		this.host.setDescription(this.textDescriptiom.getValue());
		this.host.setUrl(this.textUrl.getValue());
		this.host.setInstance(ClouTreeSession.getInstance());
		
		boolean stored = false;
		
		if(newHost) {
			stored = service.storeApihost(this.host, false);
		} else {
			stored = service.updateApihost(this.host);
		}
		
		if(stored){
			Notification.show("Api Host successfully stored", Notification.Type.TRAY_NOTIFICATION);
			//TODO
			NavigationController.openApiNavigation();
			for (Window window : this.getUI().getWindows()) {
			    this.getUI().removeWindow(window);
			}
		} else {
			Notification.show("Not able to store API Host, please check data", Notification.Type.WARNING_MESSAGE);
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
		
		// apihostEditorPanel
		apihostEditorPanel = buildApihostEditorPanel();
		mainLayout.addComponent(apihostEditorPanel,
				"top:0.0px;right:0.0px;bottom:0.0px;left:0.0px;");
		
		return mainLayout;
	}

	@AutoGenerated
	private Panel buildApihostEditorPanel() {
		// common part: create layout
		apihostEditorPanel = new Panel();
		apihostEditorPanel.setImmediate(false);
		apihostEditorPanel.setWidth("100.0%");
		apihostEditorPanel.setHeight("100.0%");
		
		// apihostEditorPanelLayout
		apihostEditorPanelLayout = buildApihostEditorPanelLayout();
		apihostEditorPanel.setContent(apihostEditorPanelLayout);
		
		return apihostEditorPanel;
	}

	@AutoGenerated
	private VerticalLayout buildApihostEditorPanelLayout() {
		// common part: create layout
		apihostEditorPanelLayout = new VerticalLayout();
		apihostEditorPanelLayout.setImmediate(false);
		apihostEditorPanelLayout.setWidth("100.0%");
		apihostEditorPanelLayout.setHeight("100.0%");
		apihostEditorPanelLayout.setMargin(true);
		apihostEditorPanelLayout.setSpacing(true);
		
		// apihostFieldGrid
		apihostFieldGrid = buildApihostFieldGrid();
		apihostEditorPanelLayout.addComponent(apihostFieldGrid);
		
		// buttonSave
		buttonSave = new Button();
		buttonSave.setCaption("Save");
		buttonSave.setImmediate(true);
		buttonSave.setWidth("-1px");
		buttonSave.setHeight("-1px");
		apihostEditorPanelLayout.addComponent(buttonSave);
		apihostEditorPanelLayout.setComponentAlignment(buttonSave,
				new Alignment(10));
		
		return apihostEditorPanelLayout;
	}

	@AutoGenerated
	private GridLayout buildApihostFieldGrid() {
		// common part: create layout
		apihostFieldGrid = new GridLayout();
		apihostFieldGrid.setImmediate(false);
		apihostFieldGrid.setWidth("100.0%");
		apihostFieldGrid.setHeight("-1px");
		apihostFieldGrid.setMargin(false);
		apihostFieldGrid.setSpacing(true);
		apihostFieldGrid.setColumns(2);
		apihostFieldGrid.setRows(3);
		
		// textApihostName
		textApihostName = new TextField();
		textApihostName.setCaption("Name: ");
		textApihostName.setImmediate(false);
		textApihostName.setWidth("100.0%");
		textApihostName.setHeight("-1px");
		apihostFieldGrid.addComponent(textApihostName, 0, 0);
		
		// textUrl
		textUrl = new TextField();
		textUrl.setCaption("URL: ");
		textUrl.setImmediate(false);
		textUrl.setWidth("100.0%");
		textUrl.setHeight("-1px");
		apihostFieldGrid.addComponent(textUrl, 0, 1);
		
		// textDescriptiom
		textDescriptiom = new TextField();
		textDescriptiom.setCaption("Description:");
		textDescriptiom.setImmediate(false);
		textDescriptiom.setWidth("100.0%");
		textDescriptiom.setHeight("-1px");
		apihostFieldGrid.addComponent(textDescriptiom, 1, 1);
		
		return apihostFieldGrid;
	}

}
