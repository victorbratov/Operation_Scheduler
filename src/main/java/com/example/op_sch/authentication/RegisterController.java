package com.example.op_sch.authentication;

import com.example.op_sch.EntryPoint;
import com.example.op_sch.HibernateUtil;
import com.example.op_sch.diary.DashBoardController;
import com.example.op_sch.professionals.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.regex.Pattern;

/**
 * The type Register controller.
 */
public class RegisterController {


    /**
     * The Name text field.
     */
    @FXML
    TextField nameTextField, /**
     * The Occupation text field.
     */
    occupationTextField, /**
     * The Email text field.
     */
    emailTextField, /**
     * The Password text field.
     */
    passwordTextField, /**
     * The Work location.
     */
    workLocation;
    /**
     * The Response.
     */
    @FXML
    Label response;


    /**
     * Register worker  .
     * it takes all the parameters to register a worker
     * it has all the required validations and user responses
     * it checks id the worker already exists or not
     */
    public void registerWorker(){
        Worker checker = Worker.getWorkerByEmail(emailTextField.getText());
        if(!nameTextField.getText().isEmpty() && !occupationTextField.getText().isEmpty() &&!passwordTextField.getText().isEmpty() &&!emailTextField.getText().isEmpty()  && !workLocation.getText().isEmpty()  && checker == null ){

            if (passwordTextField.getText().length() > 6 ){
                try {
                    Worker worker = new Worker(emailTextField.getText(), nameTextField.getText(), occupationTextField.getText(), passwordTextField.getText(), workLocation.getText());
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
        else if (nameTextField.getText().isEmpty() || occupationTextField.getText().isEmpty() || passwordTextField.getText().isEmpty() || emailTextField.getText().isEmpty() || workLocation.getText().isEmpty() ){
            response.setText("Enter all credentials to continue");
        }


    }

    /**
     * Go back  - gp back to the homescreen .
     */
    public void goBack(){
        EntryPoint.manager().goTo("HOME_SCREEN");
    }


    /**
     * Go to dash board -> if register is successful it takes the user to the dashboard.
     */
    public void goTODashBoard(){
        EntryPoint.manager().goTo("DASHBOARD");
    }








}
