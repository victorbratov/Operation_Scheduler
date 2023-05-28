package com.example.op_sch.Features;

import com.example.op_sch.Appointment;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.Set;

public class SearchAppointments {

    public void searchAppointments(TextField searchField , Set<Appointment> appointments, ListView listView){
        searchField.promptTextProperty().setValue("Search For Appointments");
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            String searchTerm = newValue.trim().toLowerCase();

            // Clear the list view
            listView.getItems().clear();

            // Filter appointments based on search term
            appointments.stream()
                    .filter(appointment -> appointment.getPatientName().toLowerCase().contains(searchTerm))
                    .forEach(appointment -> {
                        String patientName = appointment.getPatientName().toString();
                        listView.getItems().add(patientName);

                    });
        });
    }
}
