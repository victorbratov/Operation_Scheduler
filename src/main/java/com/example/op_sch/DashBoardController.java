package com.example.op_sch;


import com.example.op_sch.Features.DeleteAppointment;
import com.example.op_sch.Features.EditAppointment;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableView;

import javafx.scene.layout.VBox;

import java.util.*;

public class DashBoardController {

    @FXML
    private VBox vbox;

    @FXML
    TableView<Appointment> tableView;

    @FXML
    private TextField searchField;


    private static Worker doctor;
    Appointment appointmentHelper = new Appointment();
    private Set<Appointment> appointments = new HashSet<>();

    private FilteredList<Appointment> filteredAppointments;

    public void setAppointments(Set<Appointment> appointments) {
        this.appointments = appointments;
    }



    public void initialize() {


        appointments =  appointmentHelper.getAppointmentsFromBackend();
        // Sort the appointments by date and time
        List<Appointment> sortedAppointments = new ArrayList<>(appointments);
        sortedAppointments.sort(Comparator.comparing(Appointment::getDate).thenComparing(Appointment::getTime));

        filteredAppointments = new FilteredList<>(FXCollections.observableArrayList(sortedAppointments));

        // Define the columns and map them to the corresponding fields of the Appointment class
        TableColumn<Appointment, String> patientNameColumn = new TableColumn<>("Patient Name");
        patientNameColumn.setCellValueFactory(new PropertyValueFactory<>("patientName"));

        TableColumn<Appointment, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<Appointment, String> timeColumn = new TableColumn<>("Time");
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));

        TableColumn<Appointment, String> endTimeColumn = new TableColumn<>("End Time");
//        endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("location"));

        TableColumn<Appointment, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<Appointment, Appointment> editColumn = new TableColumn<>("Edit");
        editColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        editColumn.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = new Button("Edit");

            {
                editButton.setOnAction(event -> {
                    Appointment appointment = getTableRow().getItem();
                    if (appointment != null) {
                        EditAppointment editAppointment = new EditAppointment();
                        editAppointment.showEditModal(appointment ,filteredAppointments , tableView   );
                        System.out.println("Edit: " + appointment.getPatientName());
                    }
                });
            }

            @Override
            protected void updateItem(Appointment item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    setGraphic(editButton);
                }
            }
        });


        TableColumn<Appointment, Appointment> deleteColumn = new TableColumn<>("Delete");
        deleteColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        deleteColumn.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Delete");

            {
                deleteButton.setOnAction(event -> {
                    Appointment appointment = getTableRow().getItem();
                    if (appointment != null) {
                        DeleteAppointment deleteAppointment = new DeleteAppointment(); // Create an instance of DeleteAppointment
                        deleteAppointment.deleteAppointment(appointments ,appointment , tableView );
                        filteredAppointments.setPredicate(ap -> !ap.equals(appointment)); // Filter out the deleted appointment

                        System.out.println("Delete: " + appointment.getPatientName());
//                        tableView.refresh();
//                        appointments.remove(appointment);
//                        tableView.refresh();

                    }
                });
            }




            @Override
            protected void updateItem(Appointment item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });


        // Add the columns to the table view
        tableView.getColumns().addAll(patientNameColumn, dateColumn, timeColumn, endTimeColumn, descriptionColumn, editColumn , deleteColumn);

        // Set the items for the table view
        tableView.setItems(filteredAppointments);

        // Add listener to the search field for filtering the appointments
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredAppointments.setPredicate(appointment -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true; // Show all appointments if the search field is empty
                }

                String lowerCaseFilter = newValue.toLowerCase();

                // Check if any field of the appointment contains the search text
                if (appointment.getPatientName().toLowerCase().contains(lowerCaseFilter)
                        || appointment.getDate().toLowerCase().contains(lowerCaseFilter)
                        || appointment.getTime().toString().contains(lowerCaseFilter)
                        || appointment.getDescription().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }

                return false; // Hide the appointment if it doesn't match the search text
            });
        });
    }

    public void addAppointment() {
        // Perform add appointment action
    }

    public void undo() {
        // Perform undo action
    }


    public void goToCalendarView() {
        // Perform action to go to the calendar view
    }

    public static void setDoctor(Worker Doctor) {
        doctor = Doctor;
    }
}
