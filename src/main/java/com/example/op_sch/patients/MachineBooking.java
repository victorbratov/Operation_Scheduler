package com.example.op_sch.patients;


import com.example.op_sch.HibernateUtil;
import com.example.op_sch.dataStructures.CustomSet;
import jakarta.persistence.*;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "machineBooking")

public class MachineBooking {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;



    @Column(name = "patientName")
     String patientName;

    @Column(name = "doctorName")
     String doctorName;

    @Column(name = "machineName")
     String machineName;
    @Column(name =  "date")
     String date;

    public MachineBooking() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public MachineBooking(String patientName, String doctorName, String machineName, String date) {
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.machineName = machineName;
        this.date = date;
    }


    public HashSet<MachineBooking> getMachinesByDoctorName(String workerName) {
        HashSet<MachineBooking> set = new HashSet<>();
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<MachineBooking> appointments = session.createQuery(
                            String.format("from MachineBooking A where A.doctorName = \"%s\"", workerName), MachineBooking.class)
                    .list();

            appointments.forEach(student ->{
                set.add(student);
            });

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return set;
    }

    public void deleteMachineFromBackend(MachineBooking machineBooking) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Start a transaction
            transaction = session.beginTransaction();
            // Delete the appointment object
            session.delete(machineBooking);
            // Commit the transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }


    public void postBookingToBackend(MachineBooking machineBooking){
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            // save the student objects
            session.persist(machineBooking);
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        MachineBooking machineBooking = new MachineBooking("" , "" , "" , "");
        machineBooking.postBookingToBackend(machineBooking);
    }



    @Override
    public String toString() {
        return "MachineBooking{" +
                "  patientName='" + patientName + '\'' +
                ", doctorName='" + doctorName + '\'' +
                ", machineName='" + machineName + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
