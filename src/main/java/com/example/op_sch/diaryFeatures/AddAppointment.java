package com.example.op_sch.diaryFeatures;

import com.example.op_sch.customComponents.CustomAlert;
import com.example.op_sch.patients.Appointment;
import com.example.op_sch.professionals.Worker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

public class AddAppointment {

    Appointment appointmentHelper = new Appointment();
    String formattedDate;

    public Appointment getAppointmentHelper() {
        return appointmentHelper;
    }

    public void setAppointmentHelper(Appointment appointmentHelper) {
        this.appointmentHelper = appointmentHelper;
    }

    public String getFormattedDate() {
        return formattedDate;
    }

    public void setFormattedDate(String formattedDate) {
        this.formattedDate = formattedDate;
    }

    public CustomAlert getAlert() {
        return alert;
    }

    public void setAlert(CustomAlert alert) {
        this.alert = alert;
    }

    CustomAlert alert = new CustomAlert();


    public void onChoosing(ComboBox timeBox , Worker worker ,DatePicker datePicker , DateTimeFormatter formatter ){
        Set<String> timeSet = new HashSet<>();
        timeSet.add("10:00");
        timeSet.add("10:30");
        timeSet.add("11:00");
        timeSet.add("11:30");
        timeSet.add("12:00");
        timeSet.add("12:30");
        timeSet.add("13:00");
        timeBox.getItems().clear();
        String selectedDoctor  =  worker.getName();
        if (selectedDoctor != null && datePicker.getValue() != null) {
            formattedDate = datePicker.getValue().format(formatter).toString();

            System.out.println(formattedDate);


            Set<Appointment> allAppointments = appointmentHelper.getAppointmentsByDoctorName(selectedDoctor).toSet();
            for (Appointment appointment : allAppointments) {
                if(formattedDate.equals(appointment.getDate())){
                    timeSet.remove(appointment.getTime());
                }
            }
            timeBox.getItems().addAll(timeSet);
        }
    }




    public void addAppointmentModal(TableView tableView , FilteredList<Appointment> sortedAppointments, ObservableList<Appointment> appointments , Worker worker) {
        final FilteredList<Appointment>[] updatedAppointments = new FilteredList[]{sortedAppointments};

        // Create a modal dialog`
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

        ComboBox<String> timeBox = new ComboBox<>();

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
        gridPane.add(new Label("Time:"), 0, 5);
        gridPane.add(timeBox, 1, 5);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        onChoosing(timeBox , worker, datePicker , formatter);


        // Enable/Disable save button depending on the input fields' values
        Button saveButton = (Button) dialog.getDialogPane().lookupButton(saveButtonType);
        saveButton.setDisable(false);
        dialog.getDialogPane().setContent(gridPane);

        datePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(date.isBefore(LocalDate.now()));
                setDisable(date.isBefore(LocalDate.now()) || date.isAfter(LocalDate.now().plusMonths(1)));
            }
        });

        // Set the date format for the DatePicker

        StringConverter<LocalDate> converter = new StringConverter<>() {
            public String toString(LocalDate date) {
                if (date != null) {
                    return formatter.format(date);
                } else {
                    return "";
                }
            }

            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, formatter);
                } else {
                    return null;
                }
            }
        };
        datePicker.setConverter(converter);


        datePicker.setOnAction(event -> {
            onChoosing(timeBox , worker, datePicker , formatter);
        });

        timeBox.setOnAction(event -> {
            onChoosing(timeBox , worker, datePicker , formatter);
        });


        // Validate the input fields
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                return new Pair<>(textField1.getText(), textField2.getText());
            }
            return null;
        });

        // Set the action for the save button
        saveButton.setOnAction(event -> {
            // ...

            // Create the appointment object

            // Perform the save action or further processing
//            System.out.println("Appointment details:");
//            System.out.println("Patient Name: " + appointment.getPatientName());
//            System.out.println("Patient Age: " + appointment.getAge());
//            System.out.println("Visit Date: " + appointment.getDate());
//            System.out.println("Reason for Visit: " + appointment.getDescription());
//            System.out.println("Gender: " + appointment.getGender());





            if(textField1.getText().isEmpty() || textField2.getText().isEmpty() || textField3.getText().isEmpty() || timeBox.getValue() ==null || datePicker.getValue() == null){
                alert.showAlert("Invalid Inputs" , "Enter all Credentials to continue");
                dialog.close();
            }

            else {
                try {
                        int age  = Integer.parseInt(textField2.getText());
                        Appointment appointment = new Appointment(textField1.getText() , worker.getName() , textField3.getText() , genderComboBox.getValue() , age , datePicker.getValue().toString() ,timeBox.getValue().toString() , worker.getLocation());
                        appointment.postAppointmentToBackend(appointment);
                        appointments.add(appointment);
                        updatedAppointments[0] = new FilteredList<>(FXCollections.observableArrayList(appointments));
                        tableView.setItems(updatedAppointments[0]);
                        tableView.refresh();
                        dialog.close();
                    }
                    catch (Exception e ){
                        alert.showAlert("Error" , "Invalid Age");
                        dialog.close();
                    }

            }

        });


        // Disable the save button until all required fields are filled
//        saveButton.disableProperty().bind(textField1.textProperty().isEmpty()
//                .or(textField2.textProperty().isEmpty())
//                .or(textField3.textProperty().isEmpty()));

        // Show the dialog and wait for it to close
        dialog.showAndWait();
    }
}
