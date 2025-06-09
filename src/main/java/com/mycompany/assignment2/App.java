package com.mycompany.assignment2;

import com.mycompany.assignment2.object.*;
import com.mycompany.assignment2.page.*;
import java.io.FileNotFoundException;
import java.text.ParseException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class App extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage stage) throws FileNotFoundException {
        this.primaryStage = stage;
        FileHandler fileHandler = new FileHandler();
        fileHandler.initializeFiles();
        showLoginPage();
    }

    public void showLoginPage() throws FileNotFoundException {
        LoginPage login = new LoginPage(this);
        primaryStage.setScene(new Scene(login.getView(), 300, 200));
        primaryStage.setTitle("Login - Venue Reservation");
        primaryStage.show();
    }

    public void showReservationPage(User user) throws FileNotFoundException, ParseException {
        ReservationPage reservationPage = new ReservationPage(this, user);
        primaryStage.setScene(new Scene(reservationPage.getView(), 800, 600));
        primaryStage.setTitle("Reserve Venue");
    }

    public void showHistoryPage(User user) {
        HistoryPage historyPage = new HistoryPage(this, user);
        primaryStage.setScene(new Scene(historyPage.getView(), 600, 400));
        primaryStage.setTitle("Reservation History");
    }
    
    public void showAdminPage(User adminUser) {
        AdminPage adminPage = new AdminPage(this, adminUser);
        primaryStage.setScene(new Scene(adminPage.getView(), 800, 600));
        primaryStage.setTitle("Admin Dashboard");
    }
    
    public void showUpdateUsersPage(User adminUser) {
        UpdateUsersPage updateUsersPage = new UpdateUsersPage(this, adminUser);
        primaryStage.getScene().setRoot(updateUsersPage.getView());
    }

    public void showUpdateVenuesPage(User adminUser) {
        UpdateVenuesPage updateVenuesPage = new UpdateVenuesPage(this, adminUser);
        primaryStage.getScene().setRoot(updateVenuesPage.getView());
    }
    
    public void showUserFormPage(User userToEdit, User adminUser) {
        UserFormPage formPage = new UserFormPage(this, userToEdit, adminUser);
        primaryStage.getScene().setRoot(formPage.getView());
    }
    
    public void showVenueFormPage(Venue venueToEdit, User adminUser) {
        VenueFormPage formPage = new VenueFormPage(this, venueToEdit, adminUser);
        primaryStage.getScene().setRoot(formPage.getView());
    }
    
    public static void main(String[] args) {
        launch();
    }
    
}