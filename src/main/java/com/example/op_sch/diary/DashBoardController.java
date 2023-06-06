package com.example.op_sch.diary;


import com.example.op_sch.*;
import com.example.op_sch.customComponents.CustomAlert;
import com.example.op_sch.diaryFeatures.*;
import com.example.op_sch.patients.Appointment;
import com.example.op_sch.patients.MachineBooking;
import com.example.op_sch.professionals.Worker;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.Pair;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.*;





public class DashBoardController {
    @FXML
    private VBox vbox;

    @FXML
    private TableView<TaskList> taskTable;

    @FXML
    private Label monthText;

    @FXML
    TableView<Appointment> tableView;

    @FXML
    TableView<MachineBooking> machineBookingTableView;

    @FXML
    private TextField searchField;


    @FXML
    private GridPane calendarGrid;

    @FXML
    Label workerName;


    private static Worker doctor;
    MachineBooking machineBookingHelper = new MachineBooking();

    Appointment appointmentHelper = new Appointment();
    private ObservableList<Appointment> appointments = FXCollections.observableArrayList();

    private ObservableList<TaskList> tasks ;

    private Set<MachineBooking>  machineBookingSet = machineBookingHelper.getMachinesByDoctorName(doctor.getName());


    private ObservableList<MachineBooking> machineBookings ;

    private FilteredList<Appointment> filteredAppointments;

    public DashBoardController() {

    }




    TaskList taskListHelper = new TaskList();
    Set<TaskList> taskSet = taskListHelper.getTasksByDoctorName(doctor.getName());




    public void tableRefresh(){
        tableView.refresh();
    }

    public void initialize() {
        appointmentTableCreation();
        taskTableCreation();
        YearMonth currentMonth = YearMonth.now();
        createCalendarView(currentMonth);
        machineTableCreation();
    }

    public void addAppointment() {
        AddAppointment appointment = new AddAppointment();
        appointment.addAppointmentModal(tableView , filteredAppointments , appointments , doctor);
        YearMonth currentMonth = YearMonth.now();
        createCalendarView(currentMonth);
    }

    public void undo() {
        // Perform undo action
    }


    public void logout(){
        EntryPoint.manager().goTo("HOME_SCREEN");
    }



    public void addToTask(){
        TaskList taskList = new TaskList();
        taskList.addTasks(doctor , taskTable ,  tasks);
    }

    public void taskTableCreation() {
        TaskList tableCreator  = new TaskList();
        tableCreator.taskTableCreation(taskTable , tasks , taskSet);


    }
    private void createCalendarView(YearMonth yearMonth) {
        CustomCalendar customCalendar = new CustomCalendar();
        customCalendar.createCalendarView(yearMonth , calendarGrid , appointments);
    }
    public void draggingAppointments(){
        tableView.setRowFactory(tv -> {
            TableRow<Appointment> row = new TableRow<>();

            // Create a drag event handler for each row
            row.setOnDragDetected(event -> {
                if (!row.isEmpty()) {
                    // Start the drag-and-drop gesture
                    Dragboard db = row.startDragAndDrop(TransferMode.MOVE);
                    ClipboardContent content = new ClipboardContent();
                    content.putString(Integer.toString(row.getIndex()));
                    db.setContent(content);
                    event.consume();
                }
            });

            row.setOnDragOver(event -> {
                if (event.getGestureSource() != row && event.getDragboard().hasString()) {
                    // Allow the drop
                    event.acceptTransferModes(TransferMode.MOVE);
                }
                event.consume();
            });

            row.setOnDragEntered(event -> {
                if (event.getGestureSource() != row && event.getDragboard().hasString()) {
                    // Apply visual feedback when a dragged row enters this row
                    row.setStyle("-fx-background-color: lightblue;");
                }
                event.consume();
            });

            row.setOnDragExited(event -> {
                // Remove visual feedback when a dragged row exits this row
                row.setStyle("");
                event.consume();
            });

            row.setOnDragDropped(event -> {
                if (event.getGestureSource() != row && event.getDragboard().hasString()) {
                    // Get the index of the source row
                    int sourceIndex = Integer.parseInt(event.getDragboard().getString());

                    // Get the index of the target row
                    int targetIndex = row.getIndex();

                    if (sourceIndex >= 0 && targetIndex >= 0 && targetIndex < filteredAppointments.size()) {
                        List<Appointment> sourceList = new ArrayList<>(filteredAppointments.getSource());
                        Appointment appointment = sourceList.remove(sourceIndex);
                        sourceList.add(targetIndex, appointment);
                        filteredAppointments = new FilteredList<>(FXCollections.observableArrayList(sourceList), p -> true);
                        tableView.setItems(filteredAppointments);
                    }
                    event.setDropCompleted(true);
                }
                event.consume();
            });

            return row;
        });
    }





    public void appointmentTableCreation() {
        draggingAppointments();
        searchingAppointments();

        appointments.addAll(appointmentHelper.getAppointmentsByDoctorName(doctor.getName()).toSet());
        appointments.sort(Comparator.comparing(Appointment::getDate).thenComparing(Appointment::getTime));
        filteredAppointments = new FilteredList<>(appointments , p->true);
        YearMonth currentYearMonth = YearMonth.now();
        String monthName = currentYearMonth.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        monthText.setText(monthName);
        workerName.setText("Hello, " + doctor.getName());


        TableColumn<Appointment, String> patientNameColumn = new TableColumn<>("Patient Name");
        patientNameColumn.setCellValueFactory(new PropertyValueFactory<>("patientName"));

        TableColumn<Appointment, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<Appointment, String> timeColumn = new TableColumn<>("Time");
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));

        TableColumn<Appointment, String> locationCol = new TableColumn<>("Location");
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));

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
                        YearMonth currentMonth = YearMonth.now();
                        createCalendarView(currentMonth);
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

                        DeleteAppointment deleteAppointment = new DeleteAppointment();
                        deleteAppointment.deleteAppointment(appointment , tableView , filteredAppointments);
                        YearMonth currentMonth = YearMonth.now();
                        createCalendarView(currentMonth);


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


        TableColumn<Appointment, Appointment> machineColumn = new TableColumn<>("Machine");
        machineColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        machineColumn.setCellFactory(param -> new TableCell<>() {
            private final Button machineButton = new Button("Book");

            // set on action to delete the appointment

            {
                machineButton.setOnAction(event -> {
                    Appointment appointment = getTableRow().getItem();
                    if (appointment != null) {

                        Dialog<Pair<String, String>> dialog = new Dialog<>();
                        dialog.setTitle("Book a Machine");
                        dialog.setHeaderText("");

                        // Set the button types
                        ButtonType saveButtonType = new ButtonType("Book", ButtonBar.ButtonData.OK_DONE);
                        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

                        // Create the GridPane for the input fields
                        GridPane gridPane = new GridPane();
                        gridPane.setHgap(10);
                        gridPane.setVgap(10);
                        gridPane.setPadding(new Insets(20, 150, 10, 10));

                        // Create the input fields

                        ComboBox<String> machineNames = new ComboBox<>();
                        machineNames.getItems().addAll("MRI", "ECG" , "ECH0" , "BP", "Blood Test");

                        DatePicker datePicker = new DatePicker(LocalDate.now());

                        // Add the input fields to the gridPane
                        gridPane.add(new Label("Choose :"), 0, 0);
                        gridPane.add(machineNames, 1, 0);
                        gridPane.add(new Label("Choose Date"), 0, 2);
                        gridPane.add(datePicker, 1, 2);


                        Button saveButton = (Button) dialog.getDialogPane().lookupButton(saveButtonType);
                        saveButton.setDisable(false);
                        dialog.getDialogPane().setContent(gridPane);
                        saveButton.setOnAction(e -> {
                            if(machineNames.getValue()!= null && datePicker.getValue() !=null){
                                MachineBooking machineBooking = new MachineBooking(appointment.getPatientName() , appointment.getDoctorName() , machineNames.getValue().toString(), datePicker.getValue().toString());
                                machineBookingHelper.postBookingToBackend(machineBooking);
                                machineBookingTableView.getItems().add(machineBooking);
                                machineBookingTableView.setItems(machineBookings);
                                machineBookingTableView.refresh();
                                dialog.close();
                            }
                            else {
                                CustomAlert customAlert = new CustomAlert();
                                customAlert.showAlert("Enter Task" , "Enter task and priority level to continue");
                            }

                        });

                        dialog.showAndWait();
                    }
                });
            }





            @Override
            protected void updateItem(Appointment item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    setGraphic(machineButton);
                }
            }
        });

        appointments.addListener((ListChangeListener.Change<? extends Appointment> change) -> {
            filteredAppointments.setPredicate(appointment -> true); // Refresh the predicate
        });



        // Add the columns to the table view
        tableView.getColumns().addAll(patientNameColumn, dateColumn, timeColumn, locationCol, descriptionColumn, editColumn , deleteColumn , machineColumn);
        tableView.setItems(filteredAppointments);

    }

    public void machineTableCreation() {
        machineBookings = FXCollections.observableArrayList();

        TableColumn<MachineBooking, String> patientNameCol = new TableColumn<>("Patient Name");
        patientNameCol.setCellValueFactory(new PropertyValueFactory<>("patientName"));

        TableColumn<MachineBooking, String> machineNameCol = new TableColumn<>("Machine Name");
        machineNameCol.setCellValueFactory(new PropertyValueFactory<>("machineName"));

        TableColumn<MachineBooking, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<MachineBooking, MachineBooking> deleteColumn = new TableColumn<>("Delete");
        deleteColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        ObservableList<MachineBooking> finalBookings = machineBookings;
        deleteColumn.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Delete");

            {
                deleteButton.setOnAction(event -> {
                    MachineBooking booking = getTableRow().getItem();
                    if (booking != null) {
                        machineBookingHelper.deleteMachineFromBackend(booking);
                        finalBookings.remove(booking);
                        machineBookingTableView.refresh();
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
        machineBookingTableView.getColumns().addAll(patientNameCol, machineNameCol, deleteColumn);
    }




    public void  searchingAppointments(){
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






    public static void setDoctor(Worker Doctor) {
        doctor = Doctor;
    }
}
