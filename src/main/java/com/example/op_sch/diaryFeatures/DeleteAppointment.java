package com.example.op_sch.diaryFeatures;

import com.example.op_sch.patients.Appointment;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;

import java.util.Optional;

public class DeleteAppointment {


    Appointment appointmentHelper = new Appointment();

    public void deleteAppointment(Appointment appointment, TableView<Appointment> tableView, FilteredList<Appointment> filteredAppointments) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Delete Appointment");
        alert.setContentText("Are you sure you want to delete this appointment?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Remove the appointment from the filtered list and update the table view
            filteredAppointments.getSource().remove(appointment);
            tableView.setItems(filteredAppointments);

            // Delete the appointment from the backend or perform necessary operations
            appointmentHelper.deleteAppointmentFromBackend(appointment);


            System.out.println("Delete: " + appointment.getPatientName());
        }

    }

}
