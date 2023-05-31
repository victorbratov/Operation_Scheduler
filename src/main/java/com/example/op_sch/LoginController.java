package com.example.op_sch;

import com.example.op_sch.GUI.AllScreens.Dashboard;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    TextField loginTextField, passwordTextField;
    @FXML Label response;


    public String getLogin(){
        return loginTextField.getText();
    }


    public void validation(String name){

        Worker worker = Worker.getWorker(name);

        if(worker!= null && worker.getPassword().equals(passwordTextField.getText())){
            ((Dashboard) EntryPoint.manager().getScreen("DASHBOARD")).setDoctor(worker);
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
