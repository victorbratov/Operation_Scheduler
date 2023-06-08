package com.example.op_sch.diary;


import com.example.op_sch.*;
import com.example.op_sch.dataStructures.UndoStack;
import com.example.op_sch.dataStructures.UndoStackNode;
import com.example.op_sch.diaryFeatures.*;
import com.example.op_sch.generalClasses.Action;
import com.example.op_sch.patients.Appointment;
import com.example.op_sch.patients.MachineBooking;
import com.example.op_sch.professionals.Worker;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
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

    @FXML
    Button undoButton;


    private static Worker doctor;
    Appointment appointmentHelper = new Appointment();
    private ObservableList<Appointment> appointments = FXCollections.observableArrayList();

    private ObservableList<TaskList> tasks = FXCollections.observableArrayList();


    MachineBooking machineBookingHelper = new MachineBooking();
    private ObservableList<MachineBooking> machineBookings = FXCollections.observableArrayList();

    private FilteredList<Appointment> filteredAppointments;
    private UndoStack undoStack;

    public DashBoardController() {

    }




    TaskList taskListHelper = new TaskList();
    Set<TaskList> taskSet = taskListHelper.getTasksByDoctorName(doctor.getName());
    Set<MachineBooking> machineBookingSet = machineBookingHelper.getMachinesByDoctorName(doctor.getName());




    public void tableRefresh(){
        tableView.refresh();
    }

    public void initialize() {
        appointmentTableCreation();
        taskTableCreation();
        machineBookingTableCreation();
        YearMonth currentMonth = YearMonth.now();
        createCalendarView(currentMonth);
        undoStack = new UndoStack();
    }

    public void addAppointment() {
        AddAppointment appointment = new AddAppointment();
        appointment.addAppointmentModal(tableView , filteredAppointments , appointments , doctor);
        undoStack.push(appointment.getAppointment(), Action.ADD);
        YearMonth currentMonth = YearMonth.now();
        createCalendarView(currentMonth);
    }

    public void undo() {
        if(!undoStack.isEmpty()){
            UndoStackNode node = undoStack.pop();
            Appointment appointment = node.getAppointment();
            switch (node.getAction()) {
                case ADD -> appointment.deleteAppointmentFromBackend(appointment);
                case EDIT -> appointment.updateAppointmentInBackend(appointment);
                case DELETE -> {
                    Appointment n_appointment = new Appointment(appointment);
                    appointment.postAppointmentToBackend(n_appointment);
                }
            }
            YearMonth currentMonth = YearMonth.now();
            createCalendarView(currentMonth);
            appointmentTableCreation();
            tableRefresh();
        }
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

    public void machineBookingTableCreation() {
        machineBookingHelper.machineBookingTableCreation(machineBookings , machineBookingSet , machineBookingTableView);
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

        if(!appointments.isEmpty()){
            appointments.clear();
        }
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
                        undoStack.push(appointment.fullCopy(), Action.EDIT);
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
                        undoStack.push(appointment, Action.DELETE);
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
                        machineBookingHelper.addMachineBooking(appointment , machineBookings , machineBookingSet , machineBookingTableView);
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
        tableView.getColumns().clear();
        tableView.getColumns().addAll(patientNameColumn, dateColumn, timeColumn, endTimeColumn, descriptionColumn, editColumn , deleteColumn , machineColumn);
        tableView.setItems(filteredAppointments);

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
