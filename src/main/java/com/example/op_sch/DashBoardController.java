package com.example.op_sch;

import com.example.op_sch.Features.AddAppointment;
import com.example.op_sch.Features.DeleteAppointment;
import com.example.op_sch.Features.EditAppointment;
import com.example.op_sch.Features.SearchAppointments;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.Set;

public class DashBoardController {

    @FXML
    VBox vbox;

    @FXML
    HBox hBox;
    Appointment appointmentHelper = new Appointment();
    Set<Appointment> appointments = appointmentHelper.getAppointmentsFromBackend();

    public Set<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(Set<Appointment> appointments) {
        this.appointments = appointments;
    }

    @FXML
    TextField searchField;

    ListView<String> listView = new ListView<>();
    public void initialize() {

        // Create a custom cell factory

        SearchAppointments searchAppointments = new SearchAppointments();
        searchAppointments.searchAppointments(searchField , appointments , listView);
        listView.setCellFactory(param -> new ListCell<String>() {
            private final HBox hbox = new HBox(); // Container for worker name and buttons
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Delete");

            {
                hbox.getChildren().addAll(new Label(), editButton, deleteButton);
                HBox.setHgrow(editButton, Priority.ALWAYS);
                HBox.setHgrow(deleteButton, Priority.ALWAYS);
                setGraphic(hbox);

                // Handle button actions
                editButton.setOnAction(event -> {
                    Appointment selectedAppointment = appointments.stream()
                            .filter(appointment -> appointment.getPatientName().equals(getItem()))
                            .findFirst()
                            .orElse(null);
                    if (selectedAppointment != null) {
                        EditAppointment editAppointment = new EditAppointment();
                        editAppointment.showEditModal(selectedAppointment);
                    }
                });
                deleteButton.setOnAction(event -> {
                    String patientName = getItem();
                    Appointment selectedAppointment = appointments.stream()
                            .filter(appointment -> appointment.getPatientName().equals(patientName))
                            .findFirst()
                            .orElse(null);
                    if (selectedAppointment != null) {
                        DeleteAppointment deleteAppointment = new DeleteAppointment();
                        deleteAppointment.deleteAppointment(appointments , selectedAppointment , appointmentHelper , listView , patientName);
                    }
                });
            }

            @Override
            protected void updateItem(String workerName, boolean empty) {
                super.updateItem(workerName, empty);

                if (empty || workerName == null) {
                    setGraphic(null);
                } else {
                    ((Label) hbox.getChildren().get(0)).setText(workerName);
                    setGraphic(hbox);
                }
            }
        });







        // Add worker names to the ListView
        appointments.forEach(appointment -> {
            String patientName = appointment.getPatientName().toString();
            listView.getItems().add(patientName);
        });

        vbox.getChildren().add(listView);
    }

    public void addAppointment() {
        AddAppointment appointment = new AddAppointment();
        appointment.addAppointmentModal();
    }

}
