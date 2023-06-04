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



    @Column(name = "PATIENT_NAME")
    private String patientName;

    @Column(name = "DOCTOR_NAME")
    private String doctorName;

    @Column(name = "MACHINE_NAME")
    private String machineName;
    @Column(name =  "DATE")
    private String date;

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


    public void addMachineBooking(Appointment appointment ,ObservableList<MachineBooking> machineBookings , Set<MachineBooking> machineBookingSet , TableView<MachineBooking> machineBookingTableView){
        machineBookings = FXCollections.observableArrayList(machineBookingSet);
        Dialog<MachineBooking> dialog = new Dialog<>();
        dialog.setTitle("Add Machine Booking");

        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        ComboBox<String> machineComboBox = new ComboBox<>();
        machineComboBox.getItems().addAll("Blood Test", "MRI", "ECG" , "ECHO"); // Add your machine options here

        DatePicker datePicker = new DatePicker();

        Label machineLabel = new Label("Machine:");
        Label dateLabel = new Label("Date:");

        gridPane.add(machineLabel, 0, 0);
        gridPane.add(machineComboBox, 1, 0);
        gridPane.add(dateLabel, 0, 1);
        gridPane.add(datePicker, 1, 1);

        dialogPane.setContent(gridPane);

        ObservableList<MachineBooking> finalMachineBookings = machineBookings;
        dialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                 if (datePicker.getValue()!=null && !machineLabel.getText().isEmpty()){
                     MachineBooking machineBooking = new MachineBooking(appointment.getPatientName() , appointment.getDoctorName(), machineComboBox.getValue().toString() , datePicker.getValue().toString());
                     machineBookingSet.add(machineBooking);
                     finalMachineBookings.setAll(machineBookingSet);
                     machineBookingTableView.setItems(finalMachineBookings);
                 }
            }
            return null;
        });

        dialog.showAndWait();
    }




    public void machineBookingTableCreation(ObservableList<MachineBooking> machineBookings  , Set<MachineBooking> machineBookingSet , TableView<MachineBooking> machineBookingTableView) {
        MachineBooking machineBookingHelper = new MachineBooking();
        machineBookings = FXCollections.observableArrayList(machineBookingSet);

        TableColumn<MachineBooking, String> patientName = new TableColumn<>("Patient Name");
        patientName.setCellValueFactory(new PropertyValueFactory<>("patientName"));

        TableColumn<MachineBooking, String> machineName = new TableColumn<>("Machine");
        machineName.setCellValueFactory(new PropertyValueFactory<>("machineName"));

        TableColumn<MachineBooking, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<MachineBooking, MachineBooking> deleteColumn = new TableColumn<>("Delete");
        deleteColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        ObservableList<MachineBooking> finalMachineBookings = machineBookings;
        deleteColumn.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Delete");

            {
                deleteButton.setOnAction(event -> {
                    MachineBooking machineBooking = getTableRow().getItem();
                    if (machineBooking != null) {
                        // Perform delete action here
                        machineBookingSet.remove(machineBooking);
                        finalMachineBookings.setAll(machineBookingSet);
                        machineBookingTableView.setItems(finalMachineBookings);
                        // Call method to delete machine booking from backend or update the data
                        // ...
                    }
                });
            }

            @Override
            protected void updateItem(MachineBooking item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });

        machineBookingTableView.setItems(machineBookings);
        machineBookingTableView.getColumns().addAll(patientName, machineName, dateColumn, deleteColumn);
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
