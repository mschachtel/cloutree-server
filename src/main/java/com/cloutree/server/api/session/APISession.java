
/**
 * Copyright 2013 Marc Schachtel, Germany
 */

package com.cloutree.server.api.session;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.cloutree.server.permission.PasswordService;
import com.cloutree.server.permission.Permissions;
import com.cloutree.server.persistence.entity.User;
import com.cloutree.server.persistence.service.UserService;

/**
 * APISession
 *
 * @author marc
 *
 * Since 18.07.2013
 */

public class APISession {
    
    static Logger log = Logger.getLogger(APISession.class.getName());
    
    private static void sessionLogin(User user, HttpServletRequest req) {
		HttpSession session = req.getSession();
		session.setAttribute("USER", user);
    }
    
    public static void logout(HttpServletRequest req) {
		HttpSession session = req.getSession();
		session.removeAttribute("USER");
		session.invalidate();
		
    }
    
    public static boolean isLoggedIn(HttpServletRequest req) {
	
		HttpSession session = req.getSession();
		if(session.getAttribute("USER") != null) {
		    return true;
		}
		
		return false;
	
    }
    
    public static User getCurrentUser(HttpServletRequest req) {
		HttpSession session = req.getSession();
		return (User)session.getAttribute("USER");
    }
    
    public static boolean login(String username, String tenant, String password, HttpServletRequest req) {
	
		PasswordService passwordService = new PasswordService();
		UserService userService = new UserService();
		
		User user = userService.getUserByUserName(username, tenant);
		
		if(user == null || !user.getActive()) {
		    log.log(Level.INFO, username + " not logged in. Null or inactive.");
		    return false;
		} else if(!user.getPermissionList().contains(Permissions.API)){
			log.log(Level.INFO, username + " not permitted to use API");
		    return false;
		} else {
		    
		    boolean authenticated;
		    
		    try {
				authenticated = passwordService.authenticate(password, user.getPassword(), user.getSalt());
			    } catch (Exception e) {
					log.log(Level.SEVERE, e.getMessage());
					return false;
		    }
		    
		    if (authenticated) {
				sessionLogin(user, req);
				log.log(Level.INFO, "API-User logged in: " + user.getUsername());
				return true;
		    }
		    
		    return false;
		    
		}
	
    }
    
}
