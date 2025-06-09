package com.mycompany.assignment2.page;

import com.mycompany.assignment2.*;
import com.mycompany.assignment2.object.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class AdminPage {
    private final VBox view = new VBox(10);
    private final App app;
    private final User adminUser;
    private final TableView<Reservation> tableView = new TableView<>();
    private final ObservableList<Reservation> allReservations = FXCollections.observableArrayList();
    private ArrayList<Reservation> reservations;
    private ArrayList<Venue> venues;
    private ArrayList<User> users;

    public AdminPage(App app, User adminUser) {
        this.app = app;
        this.adminUser = adminUser;
        createUI();
    }

    public String getVenueName(String venueID){
        try{
            FileHandler fileHandler = new FileHandler();
            venues = fileHandler.readVenue();
            for (Venue venue:venues){
                if (venueID.equals(venue.getVenueID())){
                    return venue.getName();
                }
            
        }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public String getUserEmail(String userID){
        try{
            FileHandler fileHandler = new FileHandler();
            users = fileHandler.readUser();
            for (User user:users){
                if (userID.equals(user.getUserID())){
                    return user.getEmail();
                }
            
        }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    private void createUI() {
        try {
            FileHandler fileHandler = new FileHandler();
            reservations = fileHandler.readReservation();
            allReservations.setAll(reservations);
        } catch (FileNotFoundException | ParseException e) {
            e.printStackTrace();
        }

        ComboBox<String> filterCombo = new ComboBox<>();
        filterCombo.getItems().addAll("All", "Pending", "Approved", "Declined"); 
        filterCombo.setValue("All");
        filterCombo.setOnAction(e -> applyFilter(filterCombo.getValue()));
        
        TableColumn<Reservation, String> reservationCol = new TableColumn<>("Reservation ID");
        reservationCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getReservationID()));
        
        TableColumn<Reservation, String> userCol = new TableColumn<>("User");
        userCol.setCellValueFactory(data -> new SimpleStringProperty(getUserEmail(data.getValue().getUserID())));

        TableColumn<Reservation, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(data -> new SimpleStringProperty(
                new java.text.SimpleDateFormat("yyyy-MM-dd").format(data.getValue().getDate())
        ));

        TableColumn<Reservation, String> startCol = new TableColumn<>("Start");
        startCol.setCellValueFactory(data -> new SimpleStringProperty(
                new java.text.SimpleDateFormat("HH:mm").format(data.getValue().getStartTime())
        ));

        TableColumn<Reservation, String> endCol = new TableColumn<>("End");
        endCol.setCellValueFactory(data -> new SimpleStringProperty(
                new java.text.SimpleDateFormat("HH:mm").format(data.getValue().getEndTime())
        ));

        TableColumn<Reservation, String> venueCol = new TableColumn<>("Venue");
        venueCol.setCellValueFactory(data -> new SimpleStringProperty(getVenueName(data.getValue().getVenueID())));

        TableColumn<Reservation, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStatus()));

        tableView.getColumns().addAll(reservationCol,userCol, dateCol, startCol, endCol, venueCol, statusCol);
        tableView.setItems(allReservations);

        // Buttons
        Button approveButton = new Button("Approve");
        Button declineButton = new Button("Decline");

        
        approveButton.setOnAction(e -> updateSelectedReservationStatus("Approved"));
        declineButton.setOnAction(e -> updateSelectedReservationStatus("Declined"));

        HBox buttonBar = new HBox(10, approveButton, declineButton);
        buttonBar.setPadding(new Insets(10));
        buttonBar.setStyle("-fx-alignment: center-right;");

   
        Button updateUsersButton = new Button("Update Users");
        updateUsersButton.setOnAction(e -> app.showUpdateUsersPage(adminUser));

        Button updateVenuesButton = new Button("Update Venues");
        updateVenuesButton.setOnAction(e -> app.showUpdateVenuesPage(adminUser));

        HBox adminNavButtons = new HBox(10, updateUsersButton, updateVenuesButton);
        adminNavButtons.setPadding(new Insets(10));
        adminNavButtons.setStyle("-fx-alignment: center;");
        
        view.setPadding(new Insets(15));
        view.getChildren().addAll(new Label("Reservation Requests:"), filterCombo, tableView, buttonBar, adminNavButtons);
    }

    private void updateSelectedReservationStatus(String newStatus) {
        Reservation selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            selected.setStatus(newStatus);
            try {
                FileHandler fileHandler = new FileHandler();
                fileHandler.saveReservationToFile(reservations); // Save updated list
                tableView.refresh();
                System.out.println("Updated reservation " + selected.getReservationID() + " to " + newStatus);
            } catch (IOException ex) {
                ex.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to save reservation.").showAndWait();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please select a reservation to update.").showAndWait();
        }
    }

    private void applyFilter(String filter) {
        ObservableList<Reservation> filtered;
        switch (filter) {
            case "Pending":
                filtered = FXCollections.observableArrayList(
                        allReservations.stream().filter(r -> r.getStatus().equalsIgnoreCase("Pending")).collect(Collectors.toList()));
                break;
            case "Approved":
                filtered = FXCollections.observableArrayList(
                        allReservations.stream().filter(r -> r.getStatus().equalsIgnoreCase("Approved")).collect(Collectors.toList()));
                break;
            case "Declined":
                filtered = FXCollections.observableArrayList(
                        allReservations.stream().filter(r -> r.getStatus().equalsIgnoreCase("Declined")).collect(Collectors.toList()));
                break;
            default:
                filtered = allReservations;
        }
        tableView.setItems(filtered);
    }

    public VBox getView() {
        return view;
    }
}