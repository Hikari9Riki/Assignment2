
package com.mycompany.assignment2;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class ReservationPage {
    private final VBox view = new VBox(10);
    private final App app;
    private final User user;

    public ReservationPage(App app, User user) {
        this.app = app;
        this.user = user;
        createUI();
    }

    private void createUI() {
        ComboBox<String> venueSelector = new ComboBox<>();
        venueSelector.getItems().addAll("Hall A", "Room B", "Auditorium");

        TextField capacityField = new TextField("100");
        capacityField.setEditable(false);

        TextField locationField = new TextField("Block A");
        locationField.setEditable(false);

        DatePicker datePicker = new DatePicker();
        Spinner<String> startTime = new Spinner<>(new SpinnerValueFactory.ListSpinnerValueFactory<>(
                javafx.collections.FXCollections.observableArrayList("08:00", "09:00", "10:00")));
        Spinner<String> endTime = new Spinner<>(new SpinnerValueFactory.ListSpinnerValueFactory<>(
                javafx.collections.FXCollections.observableArrayList("10:00", "11:00", "12:00")));

        Button checkButton = new Button("Check Availability");

        Button historyButton = new Button("View History");
        historyButton.setOnAction(e -> app.showHistoryPage(user));

        view.setPadding(new Insets(20));
        view.getChildren().addAll(
                new Label("Select Venue:"), venueSelector,
                new Label("Capacity:"), capacityField,
                new Label("Location:"), locationField,
                new Label("Date:"), datePicker,
                new Label("Start Time:"), startTime,
                new Label("End Time:"), endTime,
                checkButton, historyButton
        );
    }

    public VBox getView() {
        return view;
    }
}