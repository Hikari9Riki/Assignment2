/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.assignment2.object;

/**
 *
 * @author kanek
 */
public class Staff extends User {
    
    static int counter = 0;
    private String staffID;
    
    public Staff(String name, String email, String phone, String password) {
        super(name, email, phone, password, "Staff");
        counter++;
        this.staffID = "STF" + counter;
        

    }
    
   public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    
    

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
    @Override
    public String toString() {
        return super.toString() + "Staff{" + "counter=" + counter + ", staffID=" + staffID + '}';
    }

    @Override
    public String toDataString() {
        return "Staff," + userID + "," + name + "," + email + "," + phone + "," + password + "," + staffID;
    }

    
}
