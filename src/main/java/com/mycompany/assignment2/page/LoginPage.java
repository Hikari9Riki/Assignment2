package com.mycompany.assignment2.page;

import com.mycompany.assignment2.*;
import com.mycompany.assignment2.object.*;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class LoginPage {
    private final VBox view = new VBox(10);
    private final App app;
    private final ArrayList<User> users ;
    
    public LoginPage(App app) throws FileNotFoundException {
        this.app = app;
        createUI();
        FileHandler fileHandler = new FileHandler();
        users = fileHandler.readUser();
        
    }

    private void createUI() {
        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();

        Button loginButton = new Button("Login");

        loginButton.setOnAction(e -> {
            String email = emailField.getText();
            String password = passwordField.getText();
            
            if (!email.isEmpty() && !password.isEmpty()) {
                Boolean logError = true;
                
                for (User user : users){
                    if (email.equalsIgnoreCase(user.getEmail())){
                        if("admin".equalsIgnoreCase(user.getRole())){
                            if (password.equalsIgnoreCase(user.getPassword())) {
                                app.showAdminPage(user);
                                logError = false;
                            }
                        } else {
                            if (password.equalsIgnoreCase(user.getPassword())) {
                                logError = false;
                                try {
                                    app.showReservationPage(user);
                                } catch (FileNotFoundException ex) {
                                    ex.printStackTrace();
                                } catch (ParseException ex) {
                                    ex.printStackTrace();
                                }
                            } 
                        }
                    }
                }
                
                if (logError){
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Wrong password or email.", ButtonType.OK);
                    alert.showAndWait();
                }
                
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter both email and password.", ButtonType.OK);
                alert.showAndWait();
            }
        });
        view.getChildren().addAll(emailLabel, emailField, passwordLabel, passwordField, loginButton);
    }

    public VBox getView() {
        return view;
    }
}