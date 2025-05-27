package com.mycompany.assignment2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        showLoginPage();
    }

    public void showLoginPage() {
        LoginPage login = new LoginPage(this);
        primaryStage.setScene(new Scene(login.getView(), 400, 300));
        primaryStage.setTitle("Login - Venue Reservation");
        primaryStage.show();
    }

    public void showReservationPage(User user) {
        ReservationPage reservationPage = new ReservationPage(this, user);
        primaryStage.setScene(new Scene(reservationPage.getView(), 800, 600));
        primaryStage.setTitle("Reserve Venue");
    }

    public void showHistoryPage(User user) {
        HistoryPage historyPage = new HistoryPage(this, user);
        primaryStage.setScene(new Scene(historyPage.getView(), 600, 400));
        primaryStage.setTitle("Reservation History");
    }

    public static void main(String[] args) {
        launch();
    }
}