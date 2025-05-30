/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.assignment2;

/**
 *
 * @author kanek
 */
public class Venue {

    static int counter = 0;
    private String venueID;
    private String name;
    private String location;
    private int capacity;
    private boolean available;

    public Venue(String name, String location, int capacity, boolean available) {
        counter++;
        this.venueID = "VEN" + counter;
        this.name = name;
        this.location = location;
        this.capacity = capacity;
        this.available = available;
        
    }
    
    public Venue(String venueID, String name, String location, int capacity, boolean available) {
        this.venueID = venueID;
        this.name = name;
        this.location = location;
        this.capacity = capacity;
        this.available = available;
        
    }

    public Venue() {
    }

    @Override
    public String toString() {
        return "Venue{" + "counter=" + counter + ", venueID=" + venueID + ", name=" + name + ", location=" + location + ", capacity=" + capacity + ", available=" + available + '}';
    }

    public String getVenueID() {
        return this.venueID;
    }

    public void setVenueID(String venueID) {
        this.venueID = venueID;
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailability(boolean available) {
        this.available = available;
    }

    public boolean isAvailable(String startTime, String endTime) {
        // Implementation
        return available;
    }

    public void updateAvailability(boolean available) {
        this.available = available;
    }

    public void addVenue() {
        // Implementation
    }

    public void updateVenueDetails() {
        // Implementation
    }
    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }
    
    
    public String toDataString() {
        return venueID + "," + name + "," + location + "," + capacity + "," + available;
    }
}
