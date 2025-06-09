package com.mycompany.assignment2.page;

import com.mycompany.assignment2.*;
import com.mycompany.assignment2.object.*;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.util.ArrayList;

public class VenueFormPage {
    private final VBox view = new VBox(10);
    private final App app;
    private final Venue venueToEdit;

    private ArrayList<Venue> venues;

    public VenueFormPage(App app, Venue venueToEdit, User adminUser) {
        this.app = app;
        this.venueToEdit = venueToEdit;

        Label title = new Label(venueToEdit == null ? "Add New Venue" : "Edit Venue");

        TextField nameField = new TextField();
        TextField locationField = new TextField();
        TextField capacityField = new TextField();
        CheckBox availableCheck = new CheckBox("Available");

        if (venueToEdit != null) {
            nameField.setText(venueToEdit.getName());
            locationField.setText(venueToEdit.getLocation());
            capacityField.setText(String.valueOf(venueToEdit.getCapacity()));
            availableCheck.setSelected(venueToEdit.isAvailable());
        }

        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            if(nameField.getText().isEmpty()){
                System.out.println("name required");
            }
            if(capacityField.getText().isEmpty()){
                System.out.println("capacity required");
            }
            if(locationField.getText().isEmpty()){
                System.out.println("location required");
            }
            try {
                FileHandler fileHandler = new FileHandler();
                venues = fileHandler.readVenue();
                if (venueToEdit != null) {
                    venues.removeIf(v -> v.getVenueID().equals(venueToEdit.getVenueID()));
                    venues.add(new Venue(venueToEdit.getVenueID(), nameField.getText(),
                        locationField.getText(),
                        Integer.parseInt(capacityField.getText()), 
                        availableCheck.isSelected()));
                } else {   
                    venues.add(new Venue(nameField.getText(),
                        locationField.getText(),
                        Integer.parseInt(capacityField.getText()), 
                        availableCheck.isSelected()));

                }
                fileHandler.saveVenueToFile(venues);

                app.showUpdateVenuesPage(adminUser);  // Navigate back
            } catch (Exception ex) {
                ex.printStackTrace();
                showAlert("Error saving venue.");
            }
        });

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> app.showUpdateVenuesPage(adminUser));

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10); grid.setPadding(new Insets(20, 150, 10, 10));

        grid.add(new Label("Name:"), 0, 0); grid.add(nameField, 1, 0);
        grid.add(new Label("Location:"), 0, 1); grid.add(locationField, 1, 1);
        grid.add(new Label("Capacity:"), 0, 2); grid.add(capacityField, 1, 2);
        grid.add(availableCheck, 1, 3);

        HBox buttons = new HBox(10, backButton, saveButton);
        view.setPadding(new Insets(15));
        view.getChildren().addAll(title, grid, buttons);
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
        alert.showAndWait();
    }

    public VBox getView() {
        return view;
    }
}