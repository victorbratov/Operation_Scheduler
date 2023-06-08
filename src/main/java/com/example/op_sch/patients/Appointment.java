package com.example.op_sch.patients;

import com.example.op_sch.HibernateUtil;
import com.example.op_sch.dataStructures.CustomSet;
import jakarta.persistence.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;


@Entity
@Table(name = "appointments")

public class Appointment {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;




    public Appointment() {
    }
    public Appointment(Appointment appointment) {
        this.patientName = appointment.getPatientName();
        this.date = appointment.getDate();
        this.time = appointment.getTime();
        this.description = appointment.getDescription();
        this.gender = appointment.getGender();
        this.doctorName = appointment.getDoctorName();
        this.age = appointment.getAge();
        // Copy any other properties as needed
    }





    public Appointment(String patientName, String doctorName, String description, String gender, int age , String date , String time) {
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.description = description;
        this.gender = gender;
        this.age = age;
        this.date = date;
        this.time = time;
    }

    public Appointment(String patientName, String doctorName, String description, String gender, int age , String date , String time , String location) {
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.description = description;
        this.gender = gender;
        this.date = date;
        this.time = time;
        this.age = age;
        this.location = location;
    }

    @Column(name = "PATIENT_NAME")
    private String patientName;

    @Column(name = "DOCTOR_NAME")
    private String doctorName;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name =  "GENDER")
    private String gender;

    @Column(name =  "DATE")
    private String date;

    @Column(name =  "TIME")
    private String time;

    @Column(name =  "LOCATION")
    private String location;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Column(name =  "AGE")
    private  int age;


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String medicalIssue) {
        this.description = medicalIssue;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }


    public void postAppointmentToBackend(Appointment appointment){
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            // save the student objects
            session.persist(appointment);
            int id  = session.createQuery("select max(A.id) from Appointment A", int.class).getSingleResult();
            appointment.setId(id);
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public Appointment fullCopy(){
        Appointment n_appointment = new Appointment(this);
        n_appointment.setId(this.getId());
        return n_appointment;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Appointment retrieveAppointmentFromBackend(int appointmentId) {
        Appointment persistentAppointment = null;
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Start a transaction
            transaction = session.beginTransaction();
            // Retrieve the appointment with the given ID from the database
            persistentAppointment = session.get(Appointment.class, appointmentId);
            // Commit the transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return persistentAppointment;
    }
    public void updateAppointmentInBackend(Appointment updatedAppointment) {
        // Retrieve the existing appointment from the backend using its unique identifier
        Appointment persistentAppointment = retrieveAppointmentFromBackend(updatedAppointment.getId());

        // Check if the appointment exists
        if (persistentAppointment != null) {
            // Update the properties of the existing appointment with the updated appointment's properties
            persistentAppointment.setPatientName(updatedAppointment.getPatientName());
            persistentAppointment.setAge(updatedAppointment.getAge());
            persistentAppointment.setDate(updatedAppointment.getDate());
            persistentAppointment.setDescription(updatedAppointment.getDescription());
            persistentAppointment.setGender(updatedAppointment.getGender());

            // Perform the update operation in the backend (e.g., update the appointment in the database)

            Transaction transaction = null;
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                // Start a transaction
                transaction = session.beginTransaction();
                // Update the appointment object
                session.update(persistentAppointment);
                // Commit the transaction
                transaction.commit();
                System.out.println("Appointment updated in the backend: " + persistentAppointment);
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }
        } else {
            System.out.println("Failed to update appointment in the backend: Appointment not found");
        }
    }



    public CustomSet<Appointment> getAppointmentsFromBackend(){
        CustomSet<Appointment> appointmentSet = new CustomSet<>();
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List< Appointment > students = session.createQuery("from Appointment ", Appointment.class).list();
            students.forEach(student ->{
                appointmentSet.add(student);
            });
            System.out.println( "Worker Size : " + appointmentSet.size());
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return appointmentSet;
    }

    public static void main(String[] args) {
        Appointment appointment1 = new Appointment("ap1", "Brown", "Description", "Male", 30, "2023-05-25", "10:00");
        Appointment appointment2 = new Appointment("ap2", "Brown", "Description", "Male", 30, "2023-05-25", "10:00");
        Appointment appointment7 = new Appointment("ap3", "Brown", "Description", "Male", 30, "2023-05-25", "10:00");
        Appointment appointment3 = new Appointment("ap3", "Doctor", "Description", "Male", 30, "2023-05-25", "10:00");
        Appointment appointment4 = new Appointment("ap4", "Brown", "Description", "Male", 30, "2023-05-25", "10:00");
        Appointment appointment5 = new Appointment("ap5", "Brown", "Description", "Male", 30, "2023-05-25", "10:00");
        Appointment appointment6 = new Appointment("ap6", "Doctor", "Description", "Male", 30, "2023-05-25", "10:00");
        Appointment appointment = new Appointment();
        appointment.postAppointmentToBackend(appointment1);
        appointment.postAppointmentToBackend(appointment2);
        appointment.postAppointmentToBackend(appointment3);
        appointment.postAppointmentToBackend(appointment4);
        appointment.postAppointmentToBackend(appointment5);
        appointment.postAppointmentToBackend(appointment6);
        appointment.postAppointmentToBackend(appointment7);


        System.out.println(appointment.getAppointmentsByDoctorName("Doctor"));
    }

    public void deleteAppointmentFromBackend(Appointment appointment) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Start a transaction
            transaction = session.beginTransaction();
            // Delete the appointment object
            session.delete(appointment);
            // Commit the transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public CustomSet<Appointment> getAppointmentsByDoctorName(String workerName) {
        CustomSet<Appointment> set = new CustomSet<>();
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Appointment> appointments = session.createQuery(
                            String.format("from Appointment A where A.doctorName = \"%s\"", workerName), Appointment.class)
                    .list();

            appointments.forEach(set::add);

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return set;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", patientName='" + patientName + '\'' +
                ", doctorName='" + doctorName + '\'' +
                '}';
    }



}
