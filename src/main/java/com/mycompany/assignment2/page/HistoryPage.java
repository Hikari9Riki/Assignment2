package com.mycompany.assignment2.page;

import com.mycompany.assignment2.*;
import com.mycompany.assignment2.object.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.beans.property.SimpleStringProperty;

public class HistoryPage {
    private final VBox view = new VBox(10);
    private final App app;
    private final User user;
    private final TableView<Reservation> table = new TableView<>();
    private final ObservableList<Reservation> userReservations = FXCollections.observableArrayList();
    private ArrayList<Reservation> reservations;
    private ArrayList<Venue> venues;


    public HistoryPage(App app, User user) {
        this.app = app;
        this.user = user;
        loadReservations();
        createUI();
    }

    private void loadReservations() {
        try {
            FileHandler fileHandler = new FileHandler();
            reservations = fileHandler.readReservation();
            for (Reservation res : reservations) {
                if (res.getUserID().equals(user.getUserID())) {
                    if (res.getStatus().equalsIgnoreCase("cancel")){
                        continue;
                    } else {
                        userReservations.add(res);
                    }
                }
            }

        } catch (FileNotFoundException | ParseException e) {
            e.printStackTrace();
        }
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
    private void createUI() {
        Label title = new Label("Reservation History for " + user.getEmail());

        TableColumn<Reservation, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(data -> new SimpleStringProperty(
                new SimpleDateFormat("yyyy-MM-dd").format(data.getValue().getDate())));

        TableColumn<Reservation, String> startCol = new TableColumn<>("Start");
        startCol.setCellValueFactory(data -> new SimpleStringProperty(
                new SimpleDateFormat("HH:mm").format(data.getValue().getStartTime())));

        TableColumn<Reservation, String> endCol = new TableColumn<>("End");
        endCol.setCellValueFactory(data -> new SimpleStringProperty(
                new SimpleDateFormat("HH:mm").format(data.getValue().getEndTime())));

        TableColumn<Reservation, String> venueCol = new TableColumn<>("Venue Name");
        venueCol.setCellValueFactory(data -> new SimpleStringProperty(getVenueName(data.getValue().getVenueID())));

        TableColumn<Reservation, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStatus()));

        table.getColumns().addAll(dateCol, startCol, endCol, venueCol, statusCol);
        table.setItems(userReservations);

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            try {
                app.showReservationPage(user);
            } catch (FileNotFoundException | ParseException ex) {
                ex.printStackTrace();
            }
        });

        Button cancelButton = new Button("Cancel Selected Reservation");
        cancelButton.setOnAction(e -> {
            Reservation selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                for ( Reservation reserve : reservations){
                    if (reserve.getReservationID().equalsIgnoreCase(selected.getReservationID())){
                        selected.cancelReservation(reserve);
                        break;
                    }
                }
                FileHandler fileHandler = new FileHandler();
                try {
                    fileHandler.saveReservationToFile(reservations);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                table.refresh();
                System.out.println("Reservation " + selected.getReservationID() + " Canceled.");
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a reservation to cancel.");
                alert.showAndWait();
            }
        });

        HBox bottomButtons = new HBox(10, backButton, cancelButton);
        bottomButtons.setAlignment(Pos.BOTTOM_RIGHT);

        view.setPadding(new Insets(15));
        view.getChildren().addAll(title, table, bottomButtons);
    }

    public VBox getView() {
        return view;
    }
}