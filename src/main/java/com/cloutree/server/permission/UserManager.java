
/**
 * Copyright 2013 Marc Schachtel, Germany
 */

package com.cloutree.server.permission;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.cloutree.server.persistence.entity.User;
import com.cloutree.server.persistence.service.UserService;
import com.cloutree.server.persistence.service.log.DBLogger;
import com.cloutree.server.session.ClouTreeSession;

/**
 * UserManager
 *
 * @author marc
 *
 * Since 16.07.2013
 */
public class UserManager {
    
    static Logger log = Logger.getLogger(UserManager.class.getName());
    
    public static boolean isLoggedIn() {
		return ClouTreeSession.isLoggedIn();
    }
    
    public static User getCurrentUser() {
		User user = ClouTreeSession.getUser();
		return user;
    }
    
    public static List<User> getAllUsers() {
    	List<User> result;
		
		UserService service = new UserService();
		result = service.getAllUsers();
		
		return result;
    }
    
    public static boolean login(String username, String tenant, String password, boolean rememberMe) {
	
		PasswordService passwordService = new PasswordService();
		UserService userService = new UserService();
		
		User user = userService.getUserByUserName(username, tenant);
		
		if(user == null || !user.getActive()) {
		    log.log(Level.INFO, "Not logged in. User null or inactive.");
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
				ClouTreeSession.login(user, rememberMe);
				DBLogger.logAccess("LOGIN", "SUCCEED");
				log.log(Level.INFO, "User logged in: " + user.getUsername());
				return true;
		    }
		    
		    return false;
		    
		}
	
    }
    
    public static void logout() {
		DBLogger.logAccess("LOGOUT", "SUCCEED");
		ClouTreeSession.logout();
    }
    
    public static boolean createUser(String username, String password) {
	
		User user = new User();
		UserService userService = new UserService();
		byte[] encryptedPassword;
		byte[] salt;
		PasswordService passwordService = new PasswordService();
		
		user.setUsername(username);
		try {
		    salt = passwordService.generateSalt();
		} catch (NoSuchAlgorithmException e) {
		    log.log(Level.SEVERE, e.getMessage());
		    return false;
		}
		
		try {
		    encryptedPassword = passwordService.getEncryptedPassword(password, salt);
		} catch (NoSuchAlgorithmException e) {
		    log.log(Level.SEVERE, e.getMessage());
		    return false;
		} catch (InvalidKeySpecException e) {
		    log.log(Level.SEVERE, e.getMessage());
		    return false;
		}
		
		user.setSalt(salt);
		user.setPassword(encryptedPassword);
		
		return userService.storeUser(user);

    }
    
    public static boolean createUser(User user) {
		UserService userService = new UserService();
		return userService.storeUser(user);
    }
    
    public static boolean updateUser(User user) {
		UserService userService = new UserService();
		return userService.updateUser(user);
    }
    
    public static User getUser(Integer id) {
	
		UserService service = new UserService();
		return service.getUser(id);
	
    }
    
    public static List<String> evaluateUserData(User user) {
	
		List<String> errors = new LinkedList<String>();
		
		if(user.getUsername() == null || user.getUsername().length() < 4) {
		    errors.add("Username must be longer than 3");
		}
		
		if(user.getPassword() == null || user.getPassword().length < 5) {
		    errors.add("Password must be longer than 4");
		}
		
		return errors;
	
    }
    
}
