package com.example.op_sch.Features;

import com.example.op_sch.Appointment;
import com.example.op_sch.Worker;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

public class EditAppointment {

    public void showEditModal(Appointment appointment) {
        // Create a modal dialog
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Edit Worker");
        dialog.setHeaderText("Editing worker: " + appointment.getPatientName());

        // Set the button types
        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        // Create the GridPane for the input fields
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));

        // Create the input fields
        TextField textField1 = new TextField(appointment.getPatientName());
        TextField textField2 = new TextField();
        TextField textField3 = new TextField("Initial value 3");
        TextField textField4 = new TextField("Initial value 4");
        TextField textField5 = new TextField("Initial value 5");

        // Add the input fields to the gridPane
        gridPane.add(new Label("Field 1:"), 0, 0);
        gridPane.add(textField1, 1, 0);
        gridPane.add(new Label("Field 2:"), 0, 1);
        gridPane.add(textField2, 1, 1);
        gridPane.add(new Label("Field 3:"), 0, 2);
        gridPane.add(textField3, 1, 2);
        gridPane.add(new Label("Field 4:"), 0, 3);
        gridPane.add(textField4, 1, 3);
        gridPane.add(new Label("Field 5:"), 0, 4);
        gridPane.add(textField5, 1, 4);

        // Enable/Disable save button depending on the input fields' values
        Node saveButton = dialog.getDialogPane().lookupButton(saveButtonType);
        saveButton.setDisable(true);
        dialog.getDialogPane().contentProperty().set(gridPane);

        // Validate the input fields
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                return new Pair<>(textField1.getText(), textField2.getText());
            }
            return null;
        });

        // Show the dialog and wait for it to close
        dialog.showAndWait().ifPresent(result -> {
            String fieldValue1 = result.getKey();
            String fieldValue2 = result.getValue();
            // Handle the edited field values
            // Implement the desired functionality here
        });
    }

}
