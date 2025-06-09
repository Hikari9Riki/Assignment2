/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.assignment2.object;

/**
 *
 * @author kanek
 */
public class Student extends User {
   
    static int counter = 0;
    private String matricNo;

    public Student(String name, String email, String phone, String password) {
        super(name, email, phone, password, "Student");
        counter++;
        this.matricNo = "STD" + counter;
        
    }

    
    public String getMatricNo() {
        return matricNo;
    }

    @Override
    public String toString() {
        return  super.toString() + "Student{" + "counter=" + counter + ", matricNo=" + matricNo + '}';
    }
    
    @Override
    public String toDataString() {
        return "Student," + userID + "," + name + "," + email + "," + phone + "," + password + "," + matricNo;
    }



}
