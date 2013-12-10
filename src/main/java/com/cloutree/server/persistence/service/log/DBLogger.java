
/**
 * Copyright 2013 Marc Schachtel, Germany
 */

package com.cloutree.server.persistence.service.log;

import com.cloutree.server.permission.UserManager;
import com.cloutree.server.persistence.entity.User;

/**
 * DBLoger
 *
 * @author marc
 *
 * Since 09.09.2013
 */
public class DBLogger {

    public static void logAccess(String action, String details) {
		DBLogService svc = new DBLogService();
		svc.logAccess(UserManager.getCurrentUser(), action, details);
    }
    
    public static void logApiCall(User user, String action, String modelName, String parameters, String returnvalues) {
		DBLogService svc = new DBLogService();
		svc.logApiCall(user, action, modelName, parameters, returnvalues);
    }
    
}
