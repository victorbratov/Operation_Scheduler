package com.example.op_sch;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.Set;

public class RegisterController {


    @FXML
    TextField name , designation;
    @FXML
    Label response;



    public void registerWorker(){
        Worker worker = new Worker(name.getText() , designation.getText());

        Set<Worker> allWorkers = worker.getWorkersFromBackend();
        if(allWorkers.contains(worker)){
            response.setText("User Already Exists");

        }
        else {
            worker.postWorkerToBackend(worker);
            response.setText("User Created");

        }

    }

    public void goBack(){
        EntryPoint.manager().goTo("HOME_SCREEN");
    }









}
