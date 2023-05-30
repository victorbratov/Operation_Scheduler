package com.example.op_sch.Features;

import com.example.op_sch.Appointment;
import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.Set;

public class DeleteAppointment {



    public void deleteAppointment(Set appointments , Appointment selectedAppointment , Appointment appointmentHelper , ListView listView, String patientName){
        appointments.remove(selectedAppointment);
        appointmentHelper.deleteAppointmentFromBackend(selectedAppointment);

        listView.getItems().remove(selectedAppointment);

        listView.refresh();
    }

}
