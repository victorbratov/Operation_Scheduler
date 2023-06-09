package tests;

import com.example.op_sch.dataStructures.CustomSet;
import com.example.op_sch.patients.Appointment;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AppointmentTest {

    private Appointment appointment;

    @Before
    public void setUp() {
        appointment = new Appointment("John Doe", "Dr. Smith", "Checkup", "Male", 30, "2023-06-08", "10:00", "");
    }

    @Test
    public void testPostAppointmentToBackend() {
        // here we test the posting of an appointment to backend
        appointment.postAppointmentToBackend(appointment);
        assertNotNull(appointment.getId());
    }

    @Test
    public void testRetrieveAppointmentFromBackend() {
        // here we test persisting and retrieving the appointments from the backend
        appointment.postAppointmentToBackend(appointment);
        Appointment retrievedAppointment = appointment.retrieveAppointmentFromBackend(appointment.getId());
        assertNotNull(retrievedAppointment);
        assertEquals(appointment.getPatientName(), retrievedAppointment.getPatientName());
        assertEquals(appointment.getDoctorName(), retrievedAppointment.getDoctorName());
    }


    @Test
    public void testGetAppointmentsFromBackend() {
        CustomSet<Appointment> appointmentSet = appointment.getAppointmentsFromBackend();
        assertNotNull(appointmentSet);
    }

    @Test
    public void testDeleteAppointmentFromBackend() {
        // here we test deletion of appointments from the backend
        appointment.postAppointmentToBackend(appointment);
        int appointmentId = appointment.getId();
        appointment.deleteAppointmentFromBackend(appointment);
        Appointment deletedAppointment = appointment.retrieveAppointmentFromBackend(appointmentId);
        assertNull(deletedAppointment);
    }

    @Test
    public void testGetAppointmentsByDoctorName() {
        // here we test getting appointments, of a particular doctor
        appointment.postAppointmentToBackend(appointment);
        CustomSet<Appointment> doctorAppointments = appointment.getAppointmentsByDoctorName("Dr. Smith");
        assertNotNull(doctorAppointments);
    }


}
