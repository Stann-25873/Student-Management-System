/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

/**
 *
 * @author 25873
 */

import com.dao.UserDAO;
import com.model.User;
import java.sql.SQLException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthController {
    
    private UserDAO dao;

   
    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    
 
    private static final int MIN_PASSWORD_LENGTH = 6; 

    public AuthController() {
        this.dao = new UserDAO();
    }

    public Optional<User> authenticate(String username, String password) throws SQLException {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty.");
        }
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty.");
        }
        
       
        return dao.authenticate(username.trim(), password);
    }

    
    public boolean registerUser(String username, String password, String confirmPassword, String email) throws SQLException {
        
     
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty.");
        }
        if (password == null || password.isEmpty() || confirmPassword == null || confirmPassword.isEmpty()) {
            throw new IllegalArgumentException("Password fields cannot be empty.");
        }
        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException("Passwords do not match.");
        }
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new IllegalArgumentException("Password must be at least " + MIN_PASSWORD_LENGTH + " characters long.");
        }
        
       
        if (email == null || email.trim().isEmpty()) {
             throw new IllegalArgumentException("Email cannot be empty.");
        }
        Matcher matcher = EMAIL_PATTERN.matcher(email.trim());
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid email format.");
        }

      
        if (dao.isUsernameTaken(username.trim())) {
            throw new IllegalArgumentException("Username is already taken.");
        }
        if (dao.isEmailTaken(email.trim())) {
            throw new IllegalArgumentException("Email is already registered.");
        }

  
        
        User newUser = new User(username.trim(), password, email.trim()); 
        
        return dao.registerUser(newUser);
    }
}