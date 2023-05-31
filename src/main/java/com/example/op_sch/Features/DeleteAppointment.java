package com.example.op_sch.Features;

import com.example.op_sch.Appointment;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;

import java.util.ArrayList;
import java.util.Set;

public class DeleteAppointment {



    public void deleteAppointment(Set<Appointment> appointments, Appointment selectedAppointment, TableView<Appointment> tableView) {
        // Remove the appointment from the backend
        selectedAppointment.deleteAppointmentFromBackend(selectedAppointment);

        // Remove the appointment from the set
        boolean removed = appointments.remove(selectedAppointment);

        if (removed) {
            // Remove the appointment from the table view
            tableView.getItems().remove(selectedAppointment);
            tableView.refresh();
        }
    }

}
