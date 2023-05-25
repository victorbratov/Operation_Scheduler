package com.example.op_sch;

import jakarta.persistence.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "appointments")

public class Appointment {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;


    public Appointment() {
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
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public Set getAppointmentsFromBackend(){
        Set<Worker> appointmentSet = new HashSet<>();
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List< Worker > students = session.createQuery("from Worker ", Worker.class).list();
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
//        Appointment appointment1 = new Appointment("Khush" ,"Doc Test", "Testing" , "MALE" ,19 );
//        appointment1.postAppointmentToBackend(appointment1);
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
