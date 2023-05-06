package com.example.op_sch;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class HomeScreenController {
    @FXML
    private Label welcomeText;

    public void goToLoginScreen(ActionEvent actionEvent){
        try{
           ScreenSwitcher screenSwitcher = new ScreenSwitcher();
           screenSwitcher.goToScreen(actionEvent , "login_screen.fxml");
        }
        catch (Exception e){
            System.out.println(e.getLocalizedMessage());
        }

    }


    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}