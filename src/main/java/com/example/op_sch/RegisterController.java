package com.example.op_sch;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.Set;

public class RegisterController {


    @FXML
    TextField nameTextField, occupationTextField, loginTextField, passwordTextField;
    @FXML
    Label response;



    public void registerWorker(){
        if(!loginIsValid(loginTextField.getText())) {
            response.setText("Invalid Login");
            return;
        }
        if(!passwordIsValid(passwordTextField.getText())) {
            response.setText("Invalid Password");
            return;
        }
        Worker worker = new Worker(loginTextField.getText(), nameTextField.getText(), occupationTextField.getText(), passwordTextField.getText());

        Set<Worker> allWorkers = worker.getWorkersFromBackend();
        if(allWorkers.contains(worker)){
            response.setText("User Already Exists");

        }
        else {
            worker.postWorkerToBackend(worker);
            response.setText("User Created");

        }

    }


    private boolean loginIsValid(String login){
        if(!login.startsWith("@") || login.length() < 6 || login.contains(" ")) return false;
        return HibernateUtil.getSessionFactory().openSession().createQuery(String.format("from Worker w where w.login = \"%s\"", login), Worker.class).list().isEmpty();
    }

    private boolean passwordIsValid(String password){
        return password.length() >= 6 && !password.contains(" ");
    }
    public void goBack(){
        EntryPoint.manager().goTo("HOME_SCREEN");
    }









}
