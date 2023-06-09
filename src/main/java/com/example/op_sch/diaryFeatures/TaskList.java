package com.example.op_sch.diaryFeatures;


import com.example.op_sch.HibernateUtil;
import com.example.op_sch.customComponents.CustomAlert;
import com.example.op_sch.professionals.Worker;
import jakarta.persistence.*;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.*;

@Entity
@Table(name = "tasks")
public class TaskList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "description")
    private String description;

    @Column(name = "priority")
    private String priority;

    @Column(name = "doctor_name")
    private String doctor_name;


    public TaskList() {


    }

    public TaskList(String description, String priority, String doctor_name) {
        this.description = description;
        this.priority = priority;
        this.doctor_name = doctor_name;
    }

    public static void main(String[] args) {
        TaskList taskList = new TaskList("1", "test", "Khush");

        System.out.println(Arrays.toString(taskList.getTasksByDoctorName("Khush").toArray()));


    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public void postTaskToBackend(TaskList task) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            // save the student objects
            session.persist(task);
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

    public void deleteTaskFromBackend(TaskList taskList) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Start a transaction
            transaction = session.beginTransaction();
            // Delete the appointment object
            session.delete(taskList);
            // Commit the transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void taskTableCreation(TableView taskTable, ObservableList<TaskList> tasks, Set<TaskList> taskSet) {
        TaskList taskListHelper = new TaskList();
        tasks = FXCollections.observableArrayList(taskSet);
        TableColumn<TaskList, String> taskNameColumn = new TableColumn<>("Task Name");
        taskNameColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        TableColumn<TaskList, String> priorityColumn = new TableColumn<>("Priority Level");
        priorityColumn.setCellValueFactory(new PropertyValueFactory<>("priority"));
        TableColumn<TaskList, TaskList> deleteColumn = new TableColumn<>("Delete");
        deleteColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        ObservableList<TaskList> finalTasks = tasks;
        deleteColumn.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Delete");

            {
                deleteButton.setOnAction(event -> {
                    TaskList task = getTableRow().getItem();
                    if (task != null) {
                        // Perform delete action here
                        taskListHelper.deleteTaskFromBackend(task);
                        finalTasks.remove(task);
                        taskTable.refresh();
                        // Call method to delete task from backend or update the data
                        // ...
                    }
                });
            }

            @Override
            protected void updateItem(TaskList item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });

        Comparator<TaskList> priorityComparator = Comparator.comparing(TaskList::getPriority);
        tasks.sort(priorityComparator);

        // Set the data to the TableView

        taskTable.setItems(tasks);
        taskTable.getColumns().addAll(taskNameColumn, priorityColumn, deleteColumn);
    }

    public void addTasks(Worker worker, TableView taskTable, ObservableList<TaskList> tasks) {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Add a Task");
        dialog.setHeaderText("");

        // Set the button types
        ButtonType saveButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        // Create the GridPane for the input fields
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));

        // Create the input fields
        TextField textField1 = new TextField();
        ComboBox<String> priorityBox = new ComboBox<>();
        priorityBox.getItems().addAll("High", "Low");
        // Add the input fields to the gridPane
        gridPane.add(new Label("Title:"), 0, 0);
        gridPane.add(textField1, 1, 0);
        gridPane.add(new Label("Priority Level:"), 0, 2);
        gridPane.add(priorityBox, 1, 2);


        Button saveButton = (Button) dialog.getDialogPane().lookupButton(saveButtonType);
        saveButton.setDisable(false);
        dialog.getDialogPane().setContent(gridPane);

        saveButton.setOnAction(event -> {
            if (priorityBox.getValue() != null && !textField1.getText().isEmpty()) {
                TaskList taskList = new TaskList(textField1.getText(), priorityBox.getValue().toString(), worker.getName());
                postTaskToBackend(taskList);
                taskTable.getItems().add(taskList);
                Comparator<TaskList> priorityComparator = Comparator.comparing(TaskList::getPriority);
                tasks.sort(priorityComparator);

                // Set the data to the TableView

                taskTable.setItems(tasks);
                taskTable.refresh();
                dialog.close();
            } else {
                CustomAlert customAlert = new CustomAlert();
                customAlert.showAlert("Enter Task", "Enter task and priority level to continue");
            }

        });

        dialog.showAndWait();
    }

    public HashSet<TaskList> getTasksByDoctorName(String workerName) {
        HashSet<TaskList> set = new HashSet<>();
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<TaskList> tasks = session.createQuery(
                            String.format("from TaskList A where A.doctor_name = \"%s\"", workerName), TaskList.class)
                    .list();
            set = new HashSet<>(tasks);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return set;
    }
}
