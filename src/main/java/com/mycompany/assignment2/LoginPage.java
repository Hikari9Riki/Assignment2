
package com.mycompany.assignment2;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class LoginPage {
    private final VBox view = new VBox(10);
    private final App app;

    public LoginPage(App app) {
        this.app = app;
        createUI();
    }

    private void createUI() {
        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();

        Button loginButton = new Button("Login");
        loginButton.setOnAction(e -> {
            // Dummy user login logic
            String email = emailField.getText();
            String password = passwordField.getText();
            if (!email.isEmpty() && !password.isEmpty()) {
                app.showReservationPage(new User(email));
            }
        });

        view.setAlignment(Pos.CENTER);
        view.getChildren().addAll(emailLabel, emailField, passwordLabel, passwordField, loginButton);
    }

    public VBox getView() {
        return view;
    }
}