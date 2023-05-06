package com.example.op_sch;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class HomeScreenController {
    @FXML
    Button login_button;

    @FXML
    void goToLogin(){
        EntryPoint.manager().goTo("LOGIN_SCREEN");
    }
}
