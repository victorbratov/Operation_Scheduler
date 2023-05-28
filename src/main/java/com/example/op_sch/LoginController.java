package com.example.op_sch;

import com.example.op_sch.GUI.AllScreens.Dashboard;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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
               response.setText("User Found");
               ((Dashboard) EntryPoint.manager().getScreen("DASHBOARD")).setDoctor(work);
               goTODashBoard();
               break;
           }
           else {
               System.out.println("No Name");
               response.setText("User Does Not Exist!");
           }

        }


    }

    public void goBack(){
        EntryPoint.manager().goTo("HOME_SCREEN");
    }
    public void goTODashBoard(){
        EntryPoint.manager().goTo("DASHBOARD");
    }

    public void printName(){
        System.out.println(getName());
        validation(getName());
    }




}
