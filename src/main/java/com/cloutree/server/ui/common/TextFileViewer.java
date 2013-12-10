
/**
 * Copyright 2013 Marc Schachtel, Germany
 */

package com.cloutree.server.ui.common;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;

/**
 * TextFileViewer
 *
 * @author marc
 *
 * Since 27.08.2013
 */
public class TextFileViewer extends CustomComponent {

    /*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

    static Logger log = Logger.getLogger(TextFileViewer.class.getName());
    
    private static final long serialVersionUID = 1L;
    @AutoGenerated
    private AbsoluteLayout mainLayout;
    @AutoGenerated
    private VerticalLayout textFileViewerLayout;
    @AutoGenerated
    private TextArea textArea;
    @AutoGenerated
    private Label labelFilename;
    
    /**
     * The constructor should first build the main layout, set the
     * composition root and then do any custom initialization.
     *
     * The constructor will not be automatically regenerated by the
     * visual editor.
     */
    public TextFileViewer() {
		buildMainLayout();
		setCompositionRoot(mainLayout);
	
		// IMPLEMENTATION
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
		
		// textFileViewerLayout
		textFileViewerLayout = buildTextFileViewerLayout();
		mainLayout.addComponent(textFileViewerLayout,
			"top:0.0px;right:0.0px;bottom:0.0px;left:0.0px;");
		
		return mainLayout;
    }

    @AutoGenerated
    private VerticalLayout buildTextFileViewerLayout() {
		// common part: create layout
		textFileViewerLayout = new VerticalLayout();
		textFileViewerLayout.setImmediate(false);
		textFileViewerLayout.setWidth("100.0%");
		textFileViewerLayout.setHeight("100.0%");
		textFileViewerLayout.setMargin(false);
		
		// labelFilename
		labelFilename = new Label();
		labelFilename.setImmediate(false);
		labelFilename.setWidth("-1px");
		labelFilename.setHeight("-1px");
		labelFilename.setValue("Label");
		textFileViewerLayout.addComponent(labelFilename);
		
		// textArea
		textArea = new TextArea();
		textArea.setImmediate(false);
		textArea.setWidth("100.0%");
		textArea.setHeight("100.0%");
		textFileViewerLayout.addComponent(textArea);
		textFileViewerLayout.setExpandRatio(textArea, 1.0f);
		
		return textFileViewerLayout;
    }
    
    public void setFile(String path) {
		String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
		
		BufferedReader br;
		try {
		    br = new BufferedReader(new FileReader(basepath + "/WEB-INF/storage" + path));
		} catch (FileNotFoundException e) {
		    this.labelFilename.setValue("ERROR: File not found!");
		    return;
		}
		
		try {
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();
	
		    while (line != null) {
		        sb.append(line);
		        sb.append('\n');
		        line = br.readLine();
		    }
		    
		    this.labelFilename.setValue(path);
		    String everything = sb.toString();
		    this.textArea.setValue(everything);
		    this.textArea.setReadOnly(true);
		} catch (Exception e) {
		    log.log(Level.SEVERE, e.getMessage());
		    e.printStackTrace();
		} finally {
		    try {
			br.close();
		    } catch (IOException e) {
			log.log(Level.SEVERE, e.getMessage());
		    }
		}
	
    }

}
