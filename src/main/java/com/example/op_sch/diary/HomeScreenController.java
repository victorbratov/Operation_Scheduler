package com.example.op_sch.diary;

import com.example.op_sch.EntryPoint;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class HomeScreenController {



    @FXML
    Button login_button;

    @FXML
    void goToLogin(){
        EntryPoint.manager().goTo("LOGIN_SCREEN");
    }
    @FXML
    void goToRegister(){
        EntryPoint.manager().goTo("REGISTER_SCREEN");
    }

    @FXML
    void goToAppointmentBooking(){
        EntryPoint.manager().goTo("APPOINTMENT_BOOKING");
    }


    @FXML
    void exitSystem(){
      System.exit(0);
    }



}
