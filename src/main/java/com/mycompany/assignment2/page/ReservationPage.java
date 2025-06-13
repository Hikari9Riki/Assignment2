
package com.mycompany.assignment2.page;

import com.mycompany.assignment2.*;
import com.mycompany.assignment2.object.*;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.StringConverter;

public class ReservationPage {
    private final VBox view = new VBox(10);
    private final App app;
    private final User user;
    private final ArrayList<Venue> venues;
    private final ArrayList<Reservation> reservations;

    public ReservationPage(App app, User user) throws FileNotFoundException, ParseException {
        this.app = app;
        this.user = user;
        FileHandler fileHandler = new FileHandler();
        venues = fileHandler.readVenue();
        reservations = fileHandler.readReservation();
        createUI();
    }

    private void createUI() {
        FileHandler fileHandler = new FileHandler();
        ComboBox<Venue> venueSelector = new ComboBox<>();
        venueSelector.getItems().addAll(venues);
        
        // Show only the name
        venueSelector.setConverter(new StringConverter<Venue>() {
            @Override
            public String toString(Venue venue) {
                return venue != null ? venue.getName() : "";
            }

            @Override
            public Venue fromString(String string) {
                return null;
            }
        });

        TextField capacityField = new TextField();
        capacityField.setEditable(false);

        TextField locationField = new TextField();
        locationField.setEditable(false);
        
        venueSelector.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                capacityField.setText(String.valueOf(newVal.getCapacity()));
                locationField.setText(newVal.getLocation());
            }
        });
        
        DatePicker datePicker = new DatePicker();
        Spinner<String> startTime = new Spinner<>(new SpinnerValueFactory.ListSpinnerValueFactory<>(
                javafx.collections.FXCollections.observableArrayList("08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00")));
        Spinner<String> endTime = new Spinner<>(new SpinnerValueFactory.ListSpinnerValueFactory<>(
                javafx.collections.FXCollections.observableArrayList("09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00")));

        Button checkButton = new Button("Make Reservation");
        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> {
            try {
                app.showLoginPage();
            } catch (FileNotFoundException ex) {
                System.getLogger(ReservationPage.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        });
        checkButton.setOnAction(e -> {
            try {
                Venue selectedVenue = venueSelector.getValue();
                if (selectedVenue == null || datePicker.getValue() == null) {
                    new Alert(Alert.AlertType.WARNING, "Please select a venue and date.").show();
                    return;
                }

                // Parse the date and times
                String selectedDate = datePicker.getValue().toString(); // yyyy-MM-dd
                String start = startTime.getValue();
                String end = endTime.getValue();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

                Date startDateTime = formatter.parse("1970-1-1" + " " + start);
                Date endDateTime = formatter.parse("1970-1-1" + " " + end);
                Date dateOnly = new java.text.SimpleDateFormat("yyyy-MM-dd").parse(selectedDate);

                // Create new reservation
                Reservation newRes = new Reservation(dateOnly, startDateTime, 
                        endDateTime, selectedVenue.getVenueID(), user.getUserID());
                System.out.println(newRes);
                // Check and add
                if (newRes.checkAvailability(reservations)) {
                    
                    reservations.add(newRes);  // Save to list
                    new Alert(Alert.AlertType.INFORMATION, "Reservation created successfully!").show();
                    System.out.println("Reservation Created: " + newRes);
                    fileHandler.saveReservationToFile(reservations);
                    
                } else {
                    new Alert(Alert.AlertType.ERROR, "Time conflict! Reservation not created.").show();
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Error while creating reservation.").show();
            }
        });
        
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
                checkButton, historyButton, logoutButton
        );
    }

    public VBox getView() {
        return view;
    }
}