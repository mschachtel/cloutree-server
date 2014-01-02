
/**
 * Copyright 2013 Marc Schachtel, Germany
 */

package com.cloutree.server.permission;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.cloutree.server.persistence.entity.User;
import com.cloutree.server.ui.LoginUI;
import com.cloutree.server.ui.NavigationViewUI;
import com.cloutree.server.ui.WorkBenchSplitViewUI;
import com.vaadin.navigator.View;

/**
 * UIPermissionManager
 *
 * @author marc
 *
 * Since 24.07.2013
 */
public class UIPermissionManager {
    
    private static UIPermissionManager instance;
    
    private Map<Class<? extends View>, List<Permissions>> permissionMap;
    
    private UIPermissionManager() {
		this.init();
		instance = this;
    }
    
    public void init() {
	
    	this.permissionMap = new HashMap<Class<? extends View>, List<Permissions>>();
	
		/* 
		 * Initialize UI Permissions
		 */
		
		//LOGINUI
		List<Permissions> loginUiPermissions = new LinkedList<Permissions>();
		loginUiPermissions.add(Permissions.SUPERUSER);
		loginUiPermissions.add(Permissions.DEVELOPER);
		this.permissionMap.put(LoginUI.class, loginUiPermissions);
		
		//NAVIGATORVIEWUI
		List<Permissions> navigatorViewUiPermissions = new LinkedList<Permissions>();
		navigatorViewUiPermissions.add(Permissions.SUPERUSER);
		navigatorViewUiPermissions.add(Permissions.DEVELOPER);
		this.permissionMap.put(NavigationViewUI.class, navigatorViewUiPermissions);
		
		//WORKBENCHSPLITVIEWUI
		List<Permissions> workbenchSplitViewUiPermissions = new LinkedList<Permissions>();
		workbenchSplitViewUiPermissions.add(Permissions.SUPERUSER);
		workbenchSplitViewUiPermissions.add(Permissions.DEVELOPER);
		this.permissionMap.put(WorkBenchSplitViewUI.class, workbenchSplitViewUiPermissions);
	    }
	    
	public boolean hasPermission(User user, Class<? extends View> view) {
		
		if(user == null) {
		    return false;
		}
		
		List<Permissions> permissions = this.permissionMap.get(view);
		List <Permissions> userPermissions = user.getPermissionList();
		
		if(userPermissions.contains(Permissions.SUPERUSER) || userPermissions.contains(Permissions.ROOT)) {
		    return true;
		}
		
		for(Permissions perm : permissions) {
		    if(userPermissions.contains(perm)) {
		    	return true;
		    }
		}
		
		return false;
	
    }
    
    public static UIPermissionManager getInstance() {
		if(instance == null) {
		    instance = new UIPermissionManager();
		}
		
		return instance;
    }
    
}
