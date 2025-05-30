/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.assignment2;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.List;
import java.io.PrintWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author kanek
 */
public class FileHandler {

    
    
    public void initializeFiles() {
        createFile("users.txt");
        createFile("venues.txt");
        createFile("reservations.txt");
    }

    private void createFile(String filename) {
        try {
            File file = new File(filename);
            if (file.createNewFile()) {
                System.out.println(filename + " created.");
            } else {
                System.out.println(filename + " already exists.");
            }
        } catch (IOException e) {
            System.out.println("Error creating file: " + filename);
            e.printStackTrace();
        }
    }

    // Method to save users to file
    public void saveUsersToFile(List<User> users) {
        try (FileWriter writer = new FileWriter("users.txt", false)) { // overwrite existing file
            for (User user : users) {
                writer.write(user.toDataString() + "\n");
            }
            System.out.println("Users saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving users to file.");
            e.printStackTrace();
        }
    }
    
    public void saveReservationToFile(List<Reservation> reservationList) throws IOException {
        File file = new File("reservations.txt");
        
        try (FileWriter writer = new FileWriter(file, false)) { // overwrite existing file
            for (Reservation reserves : reservationList) {
                writer.write(reserves.toDataString() + "\n");
            }
            System.out.println("Users saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving users to file.");
            e.printStackTrace();
        }
    }
    
    public void saveVenueToFile(List<Venue> venues) {
        try (FileWriter writer = new FileWriter("venues.txt", false)) { // overwrite existing file
            for (Venue venue : venues) {
                writer.write(venue.toDataString() + "\n");
            }
            System.out.println("Users saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving users to file.");
            e.printStackTrace();
        }
    }
    
    public ArrayList<Reservation> readReservation() throws FileNotFoundException, ParseException {
        ArrayList<Reservation> reserves = new ArrayList<>();
        File file = new File("reservations.txt");
        Scanner read = new Scanner(file);

        // Match current reservation.txt format
        SimpleDateFormat dfDate = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dfTime = new SimpleDateFormat("HH:mm");

        while (read.hasNext()) {
            String line = read.nextLine();
            String[] parts = line.split(",");

            String id = parts[0].trim();
            Date date = dfDate.parse(parts[1].trim());      // e.g., 2024-12-23
            Date strTime = dfTime.parse(parts[2].trim());    // e.g., 12:30
            Date endTime = dfTime.parse(parts[3].trim());    // e.g., 14:30
            String status = parts[4].trim();
            boolean active = Boolean.parseBoolean(parts[5].trim());
            String venueID = parts[6].trim();
            String userID = parts[7].trim();

            Reservation reserve = new Reservation(id, date, strTime, endTime, status, active, venueID, userID);
            reserves.add(reserve);
        }

        return reserves;
    }


    
    public ArrayList<User> readUser() throws FileNotFoundException{
        ArrayList<User> users = new ArrayList();
        File file = new File("users.txt");
        Scanner read = new Scanner(file);
        
        while(read.hasNext()){
            String line = read.nextLine();
            String[] parts = line.split(",");
            String role = parts[0].trim();
            String userID = parts[1].trim();
            String name = parts[2].trim();
            String email = parts[3].trim();
            String phone = parts[4].trim();
            String password = parts[5].trim();
            
            User user = new User(name, email, phone,  password, role, userID);
            users.add(user);
                    
        }
        System.out.println("test");
        
        return users;
    }
    
    public ArrayList<Venue> readVenue() throws FileNotFoundException{
        ArrayList<Venue> venues = new ArrayList();
        File file = new File("venues.txt");
        Scanner read = new Scanner(file);
        
        while(read.hasNext()){
            String line = read.nextLine();
            String[] parts = line.split(",");
            String venueID = parts[0].trim();
            String name = parts[1].trim();
            String location = parts[2].trim();
            String capacity = parts[3].trim();
            String available = parts[4].trim();
            
            
            Venue venue = new Venue(venueID, name, location,  Integer.parseInt(capacity), Boolean.parseBoolean(available));
            venues.add(venue);
                    
        }
        System.out.println("test");
        
        return venues;
    }

   
}
