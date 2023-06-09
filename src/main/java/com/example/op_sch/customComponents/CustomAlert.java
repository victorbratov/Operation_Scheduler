package com.example.op_sch.customComponents;

/**
 * The type Custom alert.
 */
public class CustomAlert {

    /**
     * Show alert.
     * <p>
     * this is a custom alert
     * this has been used to alert the users, for errors and misinformation at various instances
     *
     * @param title   the title
     * @param content the content
     */
    public void showAlert(String title, String content) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle("Alert");
        alert.setHeaderText(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
