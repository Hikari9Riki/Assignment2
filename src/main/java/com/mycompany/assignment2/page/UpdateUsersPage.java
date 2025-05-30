package com.mycompany.assignment2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.util.ArrayList;
import java.util.function.Consumer;
import javafx.beans.property.SimpleStringProperty;

public class UpdateUsersPage {
    private final VBox view = new VBox(10);
    private final App app;
    private final User adminUser;
    private final TableView<User> userTable = new TableView<>();
    private final ObservableList<User> userList = FXCollections.observableArrayList();
    private ArrayList<User> users;
    
    public UpdateUsersPage(App app, User adminUser) {
        
        this.app = app;
        this.adminUser = adminUser;
        
        try {
            FileHandler fileHandler = new FileHandler();
            userList.setAll(fileHandler.readUser());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> app.showAdminPage(adminUser));
        
        TableColumn<User, String> idCol = new TableColumn<>("User ID");
        idCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getUserID()));

        TableColumn<User, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().name));

        TableColumn<User, String> roleCol = new TableColumn<>("Role");
        roleCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getRole()));

        userTable.getColumns().addAll(idCol, nameCol, roleCol);
        userTable.setItems(userList);

        Button toggleButton = new Button("Edit");
        toggleButton.setOnAction(e -> toggleUser(app, adminUser));

        Button addButton = new Button("Add User");
        addButton.setOnAction(e -> addUser(app, adminUser));

        HBox controls = new HBox(10, backButton, toggleButton, addButton);
        controls.setPadding(new Insets(10));

        view.setPadding(new Insets(15));
        view.getChildren().addAll(new Label("Update Users"), userTable, controls);
    }

    private void addUser(App app, User adminUser) {
        app.showUserFormPage(null, adminUser);
    }

    private void toggleUser(App app, User adminUser) {
        User selected = userTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            app.showUserFormPage(selected, adminUser);
  
        }
    }

    
    public VBox getView() {
        return view;
    }
}
