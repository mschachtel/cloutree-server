
/**
 * Copyright 2013 Marc Schachtel, Germany
 */

package com.cloutree.server.ui;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;

/**
 * ErrorUI
 *
 * @author marc
 *
 * Since 01.08.2013
 */
public class ErrorUI extends CustomComponent implements View {

    /*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

    private static final long serialVersionUID = 1L;
    
    @AutoGenerated
    private AbsoluteLayout mainLayout;
    
    @AutoGenerated
    private Label label_1;
    /**
     * The constructor should first build the main layout, set the
     * composition root and then do any custom initialization.
     *
     * The constructor will not be automatically regenerated by the
     * visual editor.
     */
    public ErrorUI() {
		buildMainLayout();
		setCompositionRoot(mainLayout);
	
		// Implementation
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
		
		// label_1
		label_1 = new Label();
		label_1.setImmediate(false);
		label_1.setWidth("-1px");
		label_1.setHeight("-1px");
		label_1.setValue("ERROR");
		mainLayout.addComponent(label_1, "top:20.0px;left:20.0px;");
		
		return mainLayout;
    }

    /* (non-Javadoc)
     * @see com.vaadin.navigator.View#enter(com.vaadin.navigator.ViewChangeListener.ViewChangeEvent)
     */
    @Override
    public void enter(ViewChangeEvent event) {
		// Nothing to do here
		
    }

}