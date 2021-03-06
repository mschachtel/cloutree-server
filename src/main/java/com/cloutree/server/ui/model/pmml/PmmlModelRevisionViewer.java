
/**
 * Copyright 2013 Marc Schachtel, Germany
 */

package com.cloutree.server.ui.model.pmml;

import com.cloutree.server.persistence.entity.Model;
import com.cloutree.server.persistence.entity.Modelrevision;
import com.cloutree.server.ui.common.TextFileViewer;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

/**
 * ModelRevisionViewer
 *
 * @author marc
 *
 * Since 28.08.2013
 */
public class PmmlModelRevisionViewer extends CustomComponent {

    /*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

    @AutoGenerated
    private AbsoluteLayout mainLayout;

    @AutoGenerated
    private TabSheet modelRevisionViewerTabs;

    @AutoGenerated
    private TextFileViewer textFileViewer;

    @AutoGenerated
    private VerticalLayout modelRevisionViewerLayout;

    @AutoGenerated
    private PmmlModelTester pmmlModelTester;

    @AutoGenerated
    private HorizontalLayout revisionInfoLayout;

    @AutoGenerated
    private Label labelModelName;

    @AutoGenerated
    private Label labelFilePath;

    @AutoGenerated
    private Label labelRevisionNumber;

    @AutoGenerated
    private Label labelRevisionId;

    private Modelrevision modelRevision;
    
    private Model model;

    private static final long serialVersionUID = 1L;
    
    /**
     * The constructor should first build the main layout, set the
     * composition root and then do any custom initialization.
     *
     * The constructor will not be automatically regenerated by the
     * visual editor.
     */
    public PmmlModelRevisionViewer() {
		buildMainLayout();
		setCompositionRoot(mainLayout);
	
		// Implementation
    }

    public void setModelRevision(Modelrevision modelResvision) {
		this.modelRevision = modelResvision;
		this.init();
    }
    
    private void init() {
		if(this.modelRevision == null) {
		    Notification.show("Revision not set!", Notification.Type.ERROR_MESSAGE);
		    return;
		}
		
		this.labelFilePath.setValue(this.modelRevision.getFile());
		
		this.labelModelName.setValue(this.model.getName());
		this.labelRevisionId.setValue(this.modelRevision.getId());
		this.labelRevisionNumber.setValue("Revision " + this.modelRevision.getRevision());
	
		this.pmmlModelTester.setModelEntity(model);
		this.pmmlModelTester.setFile(this.modelRevision.getFile());
		this.textFileViewer.setFile(this.modelRevision.getFile());
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
		
		// modelRevisionViewerTabs
		modelRevisionViewerTabs = buildModelRevisionViewerTabs();
		mainLayout.addComponent(modelRevisionViewerTabs,
			"top:0.0px;right:0.0px;bottom:0.0px;left:0.0px;");
		
		return mainLayout;
    }

    @AutoGenerated
    private TabSheet buildModelRevisionViewerTabs() {
		// common part: create layout
		modelRevisionViewerTabs = new TabSheet();
		modelRevisionViewerTabs.setImmediate(true);
		modelRevisionViewerTabs.setWidth("100.0%");
		modelRevisionViewerTabs.setHeight("100.0%");
		
		// modelRevisionViewerLayout
		modelRevisionViewerLayout = buildModelRevisionViewerLayout();
		modelRevisionViewerTabs.addTab(modelRevisionViewerLayout,
			"Revision Details", null);
		
		// textFileViewer
		textFileViewer = new TextFileViewer();
		textFileViewer.setImmediate(false);
		textFileViewer.setWidth("100.0%");
		textFileViewer.setHeight("100.0%");
		modelRevisionViewerTabs.addTab(textFileViewer, "Revision File", null);
		
		return modelRevisionViewerTabs;
    }

    @AutoGenerated
    private VerticalLayout buildModelRevisionViewerLayout() {
		// common part: create layout
		modelRevisionViewerLayout = new VerticalLayout();
		modelRevisionViewerLayout.setImmediate(false);
		modelRevisionViewerLayout.setWidth("100.0%");
		modelRevisionViewerLayout.setHeight("100.0%");
		modelRevisionViewerLayout.setMargin(false);
		
		// revisionInfoLayout
		revisionInfoLayout = buildRevisionInfoLayout();
		modelRevisionViewerLayout.addComponent(revisionInfoLayout);
		
		// pmmlModelTester
		pmmlModelTester = new PmmlModelTester();
		pmmlModelTester.setImmediate(false);
		pmmlModelTester.setWidth("100.0%");
		pmmlModelTester.setHeight("100.0%");
		modelRevisionViewerLayout.addComponent(pmmlModelTester);
		modelRevisionViewerLayout.setExpandRatio(pmmlModelTester, 1.0f);
		
		return modelRevisionViewerLayout;
    }

    @AutoGenerated
    private HorizontalLayout buildRevisionInfoLayout() {
		// common part: create layout
		revisionInfoLayout = new HorizontalLayout();
		revisionInfoLayout.setImmediate(false);
		revisionInfoLayout.setWidth("100.0%");
		revisionInfoLayout.setHeight("-1px");
		revisionInfoLayout.setMargin(true);
		revisionInfoLayout.setSpacing(true);
		
		// labelRevisionId
		labelRevisionId = new Label();
		labelRevisionId.setImmediate(false);
		labelRevisionId.setWidth("-1px");
		labelRevisionId.setHeight("-1px");
		labelRevisionId.setValue("RevisionId");
		revisionInfoLayout.addComponent(labelRevisionId);
		
		// labelRevisionNumber
		labelRevisionNumber = new Label();
		labelRevisionNumber.setImmediate(false);
		labelRevisionNumber.setWidth("-1px");
		labelRevisionNumber.setHeight("-1px");
		labelRevisionNumber.setValue("ModelName");
		revisionInfoLayout.addComponent(labelRevisionNumber);
		
		// labelFilePath
		labelFilePath = new Label();
		labelFilePath.setImmediate(false);
		labelFilePath.setWidth("-1px");
		labelFilePath.setHeight("-1px");
		labelFilePath.setValue("RevisionNumber");
		revisionInfoLayout.addComponent(labelFilePath);
		
		// labelModelName
		labelModelName = new Label();
		labelModelName.setImmediate(false);
		labelModelName.setWidth("-1px");
		labelModelName.setHeight("-1px");
		labelModelName.setValue("FilePath");
		revisionInfoLayout.addComponent(labelModelName);
		revisionInfoLayout.setExpandRatio(labelModelName, 1.0f);
		revisionInfoLayout.setComponentAlignment(labelModelName, new Alignment(
			6));
		
		return revisionInfoLayout;
    }

    public void setModel(Model model) {
        this.model = model;
    }

}
