package com.mycompany.assignment2.page;

import com.mycompany.assignment2.*;
import com.mycompany.assignment2.object.*;
import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class UserFormPage {
    private final VBox view = new VBox(10);
    private ArrayList<User> users;
    
    public UserFormPage(App app, User editingUser, User adminUser) {
        Label titleLabel = new Label(editingUser == null ? "Add New User" : "Edit User");
        TextField nameField = new TextField();
        TextField emailField = new TextField();
        TextField phoneField = new TextField();
        PasswordField passwordField = new PasswordField();
        ComboBox<String> roleCombo = new ComboBox<>();
        roleCombo.getItems().addAll("Student", "Staff", "Admin");

        if (editingUser != null) {
            nameField.setText(editingUser.name);
            emailField.setText(editingUser.email);
            phoneField.setText(editingUser.phone);
            passwordField.setText(editingUser.password);
            roleCombo.setValue(editingUser.role);
        }

        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            try {
                FileHandler fileHandler = new FileHandler();
                users = fileHandler.readUser();
                if (editingUser != null) {
                    users.removeIf(u -> u.getUserID().equals(editingUser.getUserID()));
                }
                users.add(new User(
                    nameField.getText(),
                    emailField.getText(),
                    phoneField.getText(),
                    passwordField.getText(),
                    roleCombo.getValue()
                ));
                fileHandler.saveUsersToFile(users);

            } catch (Exception ex) {
                ex.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Error saving user.").showAndWait();
            }
        });

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> app.showUpdateUsersPage(adminUser));

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(15));
        grid.add(new Label("Name:"), 0, 0); grid.add(nameField, 1, 0);
        grid.add(new Label("Email:"), 0, 1); grid.add(emailField, 1, 1);
        grid.add(new Label("Phone:"), 0, 2); grid.add(phoneField, 1, 2);
        grid.add(new Label("Password:"), 0, 3); grid.add(passwordField, 1, 3);
        grid.add(new Label("Role:"), 0, 4); grid.add(roleCombo, 1, 4);

        view.setPadding(new Insets(20));
        view.getChildren().addAll(titleLabel, grid, saveButton, backButton);
    }

    private void SvaeChanges() {
       try {
           FileHandler fileHandler = new FileHandler();
           users.clear();
           users.addAll(fileHandler.readUser());
       } catch (Exception e) {
           e.printStackTrace();
       }
   }
    
    public VBox getView() {
        return view;
    }
}