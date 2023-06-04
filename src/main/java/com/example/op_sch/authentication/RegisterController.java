package com.example.op_sch.authentication;

import com.example.op_sch.EntryPoint;
import com.example.op_sch.HibernateUtil;
import com.example.op_sch.diary.DashBoardController;
import com.example.op_sch.professionals.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.regex.Pattern;

public class RegisterController {


    @FXML
    TextField nameTextField, occupationTextField, emailTextField, passwordTextField, workLocation;
    @FXML
    Label response;



    public void registerWorker(){
        Worker checker = Worker.getWorkerByEmail(emailTextField.getText());
        if(!nameTextField.getText().isEmpty() && !occupationTextField.getText().isEmpty() &&!passwordTextField.getText().isEmpty() &&!emailTextField.getText().isEmpty()  && checker == null ){

            if (passwordTextField.getText().length() > 6 ){
                try {
                    Worker worker = new Worker(emailTextField.getText(), nameTextField.getText(), occupationTextField.getText(), passwordTextField.getText());
                    response.setText("User Registered Successfully");
                    worker.postWorkerToBackend(worker);
                    DashBoardController.setDoctor(worker);
                    goTODashBoard();
                }
                catch (Exception exception){
                    response.setText("Error Occurred");
                    System.out.println(exception.getMessage());
                }
            }
            else {
                response.setText("Password should be greater than 6 characters");
            }

        }
        else if(checker!=null) {
            response.setText("Account already exists");

        }
        else if (nameTextField.getText().isEmpty() && occupationTextField.getText().isEmpty() &&passwordTextField.getText().isEmpty() &&emailTextField.getText().isEmpty()){
            response.setText("Enter all credentials to continue");
        }


    }
    public void goBack(){
        EntryPoint.manager().goTo("HOME_SCREEN");
    }






    public void goTODashBoard(){
        EntryPoint.manager().goTo("DASHBOARD");
    }








}
