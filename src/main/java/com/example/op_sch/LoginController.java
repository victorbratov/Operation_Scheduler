package com.example.op_sch;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Set;

public class LoginController {

    @FXML
    TextField name;
    @FXML Label response;


    public String getName(){
        return name.getText();
    }


    public void validation(String name){
        Worker worker = new Worker();
        Set<Worker> allWorkers = worker.getWorkersFromBackend();
//        System.out.println(allWorkers);
        for (Worker work : allWorkers){
           if(work.getName().equalsIgnoreCase(name)){
               System.out.println("This Name " + work.getName().toLowerCase() );
           }
           else {
               System.out.println("No Name");
               response.setText("User Does Not Exist!");
           }
        }


    }

    public void goToRegisterScreen(){
        EntryPoint.manager().goTo("REGISTER_SCREEN");
    }

    public void printName(){
        System.out.println(getName());
        validation(getName());
    }




}
