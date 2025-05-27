package com.mycompany.assignment2;

import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class HistoryPage {
    private final VBox view = new VBox(10);
    private final App app;
    private final User user;

    public HistoryPage(App app, User user) {
        this.app = app;
        this.user = user;
        createUI();
    }

    private void createUI() {
        Label title = new Label("Reservation History for " + user.getEmail());

        TableView<String> table = new TableView<>();
        TableColumn<String, String> column = new TableColumn<>("Reservation Info");
        table.getColumns().add(column);
        table.setItems(FXCollections.observableArrayList("Room A - Confirmed", "Room B - Pending"));

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> app.showReservationPage(user));

        view.getChildren().addAll(title, table, backButton);
    }

    public VBox getView() {
        return view;
    }
}