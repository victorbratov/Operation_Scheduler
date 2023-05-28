package com.example.op_sch.Features;

import com.example.op_sch.Appointment;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.util.Pair;

import java.time.LocalDate;
import java.time.LocalTime;

public class AddAppointment {

    public void addAppointmentModal() {
        // Create a modal dialog
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Add Appointment");
        dialog.setHeaderText("");

        // Set the button types
        ButtonType saveButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        // Create the GridPane for the input fields
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));

        // Create the input fields
        TextField textField1 = new TextField();
        TextField textField2 = new TextField();
        DatePicker datePicker = new DatePicker(LocalDate.now());
        TextField textField3 = new TextField();
        ComboBox<String> genderComboBox = new ComboBox<>();
        genderComboBox.getItems().addAll("Male", "Female", "Other");

        // Add the input fields to the gridPane
        gridPane.add(new Label("Patient Name:"), 0, 0);
        gridPane.add(textField1, 1, 0);
        gridPane.add(new Label("Patient Age:"), 0, 1);
        gridPane.add(textField2, 1, 1);
        gridPane.add(new Label("Visit Date:"), 0, 2);
        gridPane.add(datePicker, 1, 2);
        gridPane.add(new Label("Reason For Visit:"), 0, 3);
        gridPane.add(textField3, 1, 3);
        gridPane.add(new Label("Gender:"), 0, 4);
        gridPane.add(genderComboBox, 1, 4);

        // Enable/Disable save button depending on the input fields' values
        Button saveButton = (Button) dialog.getDialogPane().lookupButton(saveButtonType);
        saveButton.setDisable(false);
        dialog.getDialogPane().setContent(gridPane);

        // Validate the input fields
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                return new Pair<>(textField1.getText(), textField2.getText());
            }
            return null;
        });

        // Set the action for the save button
        saveButton.setOnAction(event -> {
            String fieldValue1 = textField1.getText();
            String fieldValue2 = textField2.getText();
            String fieldValue3 = textField3.getText();
            String selectedGender = genderComboBox.getValue();
            LocalDate selectedDate = datePicker.getValue();

            // Create the appointment object
            Appointment appointment = new Appointment(fieldValue1 , "Brown" , fieldValue3 , selectedGender , Integer.parseInt(fieldValue2) , selectedDate.toString() ,"" );

            // Perform the save action or further processing
            // You can access the appointment object and use its properties as needed
            System.out.println("Appointment details:");
            System.out.println("Patient Name: " + appointment.getPatientName());
            System.out.println("Patient Age: " + appointment.getAge());
            System.out.println("Visit Date: " + appointment.getDate());
            System.out.println("Reason for Visit: " + appointment.getDescription());
            System.out.println("Gender: " + appointment.getGender());
        });

        // Disable the save button until all required fields are filled
        saveButton.disableProperty().bind(textField1.textProperty().isEmpty()
                .or(textField2.textProperty().isEmpty())
                .or(textField3.textProperty().isEmpty()));

        // Show the dialog and wait for it to close
        dialog.showAndWait();
    }
}
