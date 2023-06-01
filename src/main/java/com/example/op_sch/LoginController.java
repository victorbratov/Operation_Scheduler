package com.example.op_sch;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    TextField emailTextField, passwordTextField;
    @FXML Label response;


    public String getLogin(){
        return emailTextField.getText();
    }


    public void validation(String email){

        Worker worker = Worker.getWorkerByEmail(email);

        if(worker!= null && worker.getPassword().equals(passwordTextField.getText())){
            DashBoardController.setDoctor(worker);
            goTODashBoard();
        }

        else{
            response.setText("Wrong Login or Password");
        }
    }

    public void goBack(){
        EntryPoint.manager().goTo("HOME_SCREEN");
    }
    public void goTODashBoard(){
        EntryPoint.manager().goTo("DASHBOARD");
    }
    public void printName(){
        System.out.println(getLogin());
        validation(getLogin());
    }
}
