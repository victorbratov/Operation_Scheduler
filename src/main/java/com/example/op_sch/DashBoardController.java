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

import java.util.*;

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

    ListView<Appointment> listView = new ListView<>();

    List<Appointment> sortedAppointments = new ArrayList<>(appointments);


    public void initialize() {
        sortedAppointments.sort(Comparator.comparing(Appointment::getDate).thenComparing(Appointment::getTime));

        // Create a custom cell factory
        SearchAppointments searchAppointments = new SearchAppointments();
        searchAppointments.searchAppointments(searchField, appointments, listView);
        listView.setCellFactory(param -> new ListCell<Appointment>() {
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
                    Appointment selectedAppointment = getItem();
                    if (selectedAppointment != null) {
                        EditAppointment editAppointment = new EditAppointment();
                        editAppointment.showEditModal(selectedAppointment, sortedAppointments, listView);
                    }
                });
                deleteButton.setOnAction(event -> {
                    Appointment selectedAppointment = getItem();
                    if (selectedAppointment != null) {
                        DeleteAppointment deleteAppointment = new DeleteAppointment();
                        deleteAppointment.deleteAppointment(appointments, selectedAppointment, appointmentHelper, listView, selectedAppointment.getPatientName());
                    }
                });
            }

            @Override
            protected void updateItem(Appointment appointment, boolean empty) {
                super.updateItem(appointment, empty);

                if (empty || appointment == null) {
                    setGraphic(null);
                } else {
                    ((Label) hbox.getChildren().get(0)).setText(appointment.getPatientName());
                    setGraphic(hbox);
                }
            }
        });

        // Clear the ListView before adding sorted appointments
        listView.getItems().clear();

        // Add sorted appointments to the ListView
        listView.getItems().addAll(sortedAppointments);

        vbox.getChildren().add(listView);
    }

    public void addAppointment() {
        AddAppointment appointment = new AddAppointment();
        appointment.addAppointmentModal(listView, sortedAppointments);
    }

    public void goToCalenderView(){
        EntryPoint.manager().goTo("CALENDAR_VIEW");
    }
}
