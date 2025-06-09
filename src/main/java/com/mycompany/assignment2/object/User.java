/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.assignment2.object;

import java.util.Date;

/**
 *
 * @author kanek
 */
public class User {
   
    static int counter = 0;
    protected String userID;
    public String name;
    public String email;
    public String phone;
    public String password;
    public String role;
    private String id;
    protected boolean login = false;
    protected boolean isLoggedIn;
    
    
    public User(String name, String email, String phone, String password, String role) {
        counter++;
        this.userID = "USER"+ counter;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.role  = role;
        
    }
    
    public User(String name, String email, String phone, String password, String role, String id) {
        counter++;
        this.userID = "USER"+ counter;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.role  = role;
        this.id = id;
        
    }
    
    public boolean login(String pass) {
        isLoggedIn = pass.equals(this.password);
        System.out.println(name + (isLoggedIn ? " logged in successfully" : " login failed"));
        return isLoggedIn;
    }
    
    public void logout() {
        isLoggedIn = false;
        System.out.println(name + (" logged out successfully"));
    }
    
    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserID() {
        return userID;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }
    
    

    
    public void setPassword(String password) {
        this.password = password;
    }

    
    @Override
    public String toString() {
        return "User{" + "counter=" + counter + ", userID=" + userID + ", name=" + name + ", email=" + email + ", phone=" + phone + ", login=" + login + ", password=" + password + ", role=" + role + '}';
    }

    public boolean checkPassword(String input) {
        return this.password.equals(input);
    }
    
    public String toDataString() {
    return role + "," + userID + "," + name + "," + email + "," + phone + "," + password;
}

}
