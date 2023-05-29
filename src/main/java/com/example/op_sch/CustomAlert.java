package com.example.op_sch;

public class CustomAlert {

    public void showAlert(String title , String content) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle("Alert");
        alert.setHeaderText(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
