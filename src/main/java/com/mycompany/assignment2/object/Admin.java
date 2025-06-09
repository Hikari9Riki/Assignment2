/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.assignment2.object;

/**
 *
 * @author kanek
 */
public class Admin extends Staff {
    
    static int counter = 0;
    private String adminID;

    public Admin(String name, String email, String phone, String password) {
        super(name, email, phone, password);
        counter++;
        super.role = "Admin";
        this.adminID = "ADMIN" + counter;
        
    }
    
    public void approveReservation(Reservation reserve){
        reserve.setStatus("Approve");
    }
    
    public void rejectReservation(Reservation reserve){
        reserve.setStatus("Decline");
    }
    
    @Override
    public String toString() {
        return super.toString() +  "Admin{" + "counter=" + counter + ", adminID=" + adminID + '}';
    }

    @Override
    public String toDataString() {
        return "Admin," + userID + "," + name + "," + email + "," + phone + "," + password + "," + getStaffID() + "," + adminID;
    }

}

