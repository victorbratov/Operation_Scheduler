package com.example.op_sch.customComponents;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ScreenSwitcher {

    public void goToScreen(ActionEvent actionEvent, String FXMLName) {
        try {
            Stage stage;
            Scene scene;
            Parent root = FXMLLoader.load(getClass().getResource(FXMLName));
            stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }

    }


}
