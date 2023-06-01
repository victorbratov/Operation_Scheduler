package com.example.op_sch;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.regex.Pattern;

public class RegisterController {


    @FXML
    TextField nameTextField, occupationTextField, emailTextField, passwordTextField;
    @FXML
    Label response;



    public void registerWorker(){
        if(!EmailIsValid(emailTextField.getText())) {
            return;
        }
        if(!passwordIsValid(passwordTextField.getText())) {
            response.setText("Invalid Password");
            return;
        }
        if (nameTextField.getText().isEmpty()) {
            response.setText("Please Enter Name");
            return;
        }
        if(occupationTextField.getText().isEmpty()) {
            response.setText("Please Enter Occupation");
            return;
        }

        Worker worker = new Worker(emailTextField.getText(), nameTextField.getText(), occupationTextField.getText(), passwordTextField.getText());

        response.setText("User Registered Successfully");
        worker.postWorkerToBackend(worker);
    }


    private boolean EmailIsValid(String email){
        String regex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        if(!Pattern.compile(regex).matcher(email).matches()) {
            response.setText("Invalid Email");
            return false;
        }
        if (!HibernateUtil.getSessionFactory().openSession().createQuery(String.format("from Worker w where w.email = \"%s\"", email), Worker.class).list().isEmpty()){
            response.setText("Email Already Exists");
            return false;
        }
        return true;
    }

    private boolean passwordIsValid(String password){
        return Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,}$").matcher(password).matches();
    }
    public void goBack(){
        EntryPoint.manager().goTo("HOME_SCREEN");
    }









}
