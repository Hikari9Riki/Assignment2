package com.mycompany.assignment2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.util.ArrayList;
import java.util.function.Consumer;
import javafx.beans.property.SimpleStringProperty;

public class UpdateVenuesPage {
    private final VBox view = new VBox(10);
    private final TableView<Venue> venueTable = new TableView<>();
    private final ObservableList<Venue> venueList = FXCollections.observableArrayList();
    
    public UpdateVenuesPage(App app, User adminUser) {
        try {
            FileHandler fileHandler = new FileHandler();
            venueList.setAll(fileHandler.readVenue());
        } catch (Exception e) {
            e.printStackTrace();
        }

        
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> app.showAdminPage(adminUser));
        
        TableColumn<Venue, String> idCol = new TableColumn<>("Venue ID");
        idCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getVenueID()));

        TableColumn<Venue, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));

        TableColumn<Venue, String> statusCol = new TableColumn<>("Available");
        statusCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().isAvailable() ? "Yes" : "No"));

        venueTable.getColumns().addAll(idCol, nameCol, statusCol);
        venueTable.setItems(venueList);

        Button toggleButton = new Button("edit");
        toggleButton.setOnAction(e -> toggleVenue(app, adminUser));

        Button addButton = new Button("Add Venue");
        addButton.setOnAction(e -> addVenue(app, adminUser));

        HBox controls = new HBox(10, backButton, toggleButton, addButton);
        controls.setPadding(new Insets(10));

        view.setPadding(new Insets(15));
        view.getChildren().addAll(new Label("Update Venues"), venueTable, controls);
    }

    private void toggleVenue(App app, User adminUser) {
        Venue selected = venueTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
             app.showVenueFormPage(selected,adminUser);
            saveChanges();
        }
    }

    private void addVenue(App app, User adminUser) {
        // Placeholder venue
        app.showVenueFormPage(null,adminUser);
    }

    private void saveChanges() {
        try {
            FileHandler fileHandler = new FileHandler();
            fileHandler.saveVenueToFile(new ArrayList<>(venueList));
            venueTable.refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public VBox getView() {
        return view;
    }
    
    
}