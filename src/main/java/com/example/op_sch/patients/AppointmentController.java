package com.example.op_sch.patients;

import com.example.op_sch.EntryPoint;
import com.example.op_sch.customComponents.CustomAlert;
import com.example.op_sch.patients.Appointment;
import com.example.op_sch.professionals.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class AppointmentController {

    @FXML
    TextField patientName;
    @FXML
    TextField doctorName;
    @FXML
    TextField patientAge;
    @FXML
    TextField description;
    @FXML DatePicker datePicker;

    @FXML
    ComboBox<String> doctorChoose;
    @FXML
    ComboBox<String> choiceBox;
    @FXML
    ComboBox<String> timeBox;

    Worker workerHelper = new Worker();
    Set<Worker> allWorkers = workerHelper.getWorkersFromBackend().toSet();


    public void validation() {
        if(patientName.getText() == null || patientName.getText().isEmpty()){
            CustomAlert alert = new CustomAlert();
            alert.showAlert("Enter Patient's Name", "In order to move ahead, you need to complete all details!");
            return;
        }
        if(patientAge.getText() == null || patientAge.getText().isEmpty()){
            CustomAlert alert = new CustomAlert();
            alert.showAlert("Enter Patient's Age", "In order to move ahead, you need to complete all details!");
            return;
        }
        if(description.getText() == null || description.getText().isEmpty()){
            CustomAlert alert = new CustomAlert();
            alert.showAlert("Enter Description of your visit", "In order to move ahead, you need to complete all details!");
            return;
        }
        if (datePicker.getValue() == null){
            CustomAlert alert = new CustomAlert();
            alert.showAlert("Enter Date of the Appointment", "In order to move ahead, you need to complete all details!");
            return;
        }
    }

    Appointment appointmentHelper = new Appointment();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String formattedDate;

    public void initialize() {


        datePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(date.isBefore(LocalDate.now()));
                setDisable(date.isBefore(LocalDate.now()) || date.isAfter(LocalDate.now().plusMonths(1)));
            }
        });

        // Set the date format for the DatePicker
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
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


        doctorChoose.getItems().addAll(allWorkers.stream().map(Worker::getName).collect(Collectors.toList()));
        doctorChoose.setOnAction(event -> {

            onChoosing();

        });

        datePicker.setOnAction(event -> {
            onChoosing();
        });
    }


    public void onChoosing(){
        Set<String> timeSet = new HashSet<>();
        timeSet.add("10:00");
        timeSet.add("10:30");
        timeSet.add("11:00");
        timeSet.add("11:30");
        timeSet.add("12:00");
        timeSet.add("12:30");
        timeSet.add("13:00");
        timeBox.getItems().clear();
        String selectedDoctor = doctorChoose.getValue();
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

    public void createAppointment(){
        validation();
        Appointment newAppointment = new Appointment(patientName.getText(), doctorChoose.getValue().toString() , description.getText(), choiceBox.getValue().toString() , 19 , datePicker.getValue().format(formatter).toString(), "12:00");
        try {
            newAppointment.postAppointmentToBackend(newAppointment);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Booking Successful");
            alert.setContentText("Your booking has been successfully made, click below to go back");
            ButtonType buttonTypeOne = new ButtonType("Go Back");
            alert.getButtonTypes().setAll(buttonTypeOne);
            alert.showAndWait().ifPresent(event ->{
                if(event == buttonTypeOne){
                    EntryPoint.manager().goTo("HOME_SCREEN");
                }
            });
        } catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }

    public void goBack(){
        EntryPoint.manager().goTo("HOME_SCREEN");
    }
}
