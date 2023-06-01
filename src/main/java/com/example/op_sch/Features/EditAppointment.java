package com.example.op_sch.Features;

import com.example.op_sch.Appointment;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.util.Pair;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class EditAppointment {

    public void showEditModal(Appointment appointment, List<Appointment> sortedAppointments,  TableView tableView) {
        // Create a modal dialog
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Edit Appointment");
        dialog.setHeaderText("");

        // Set the button types
        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        // Create the GridPane for the input fields
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));

        // Create the input fields
        TextField textField1 = new TextField(appointment.getPatientName());
        TextField textField2 = new TextField(String.valueOf(appointment.getAge()));
        DatePicker datePicker = new DatePicker(LocalDate.parse(appointment.getDate()));
        TextField textField3 = new TextField(appointment.getDescription());
        ComboBox<String> genderComboBox = new ComboBox<>();
        genderComboBox.getItems().addAll("Male", "Female", "Other");
        genderComboBox.setValue(appointment.getGender());

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

        dialog.getDialogPane().setContent(gridPane);

        // Set the action for the save button
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                return null; // Returning null, as we only need to update the appointment in the backend
            }
            return null;
        });

        // Update the appointment in the backend when the save button is clicked
        dialog.setOnCloseRequest(event -> {

                String fieldValue1 = textField1.getText();
                String fieldValue2 = textField2.getText();
                String fieldValue3 = textField3.getText();
                String selectedGender = genderComboBox.getValue();
                LocalDate selectedDate = datePicker.getValue();

                // Update the appointment object
                appointment.setPatientName(fieldValue1);
                appointment.setAge(Integer.parseInt(fieldValue2));
                appointment.setDate(selectedDate.toString());
                appointment.setDescription(fieldValue3);
                appointment.setGender(selectedGender);

                // Perform the update action or further processing
                // You can access the appointment object and use its updated properties as needed
                System.out.println("Updated appointment details:");
                System.out.println("Patient Name: " + appointment.getPatientName());
                System.out.println("Patient Age: " + appointment.getAge());
                System.out.println("Visit Date: " + appointment.getDate());
                System.out.println("Reason for Visit: " + appointment.getDescription());
                System.out.println("Gender: " + appointment.getGender());

                // Update the ListView with the updated appointment
                tableView.refresh();

                // You can also update the appointment in the backend using the updated details
                appointment.updateAppointmentInBackend(appointment);

        });

        // Show the dialog and wait for it to close
        dialog.showAndWait();
    }
}
