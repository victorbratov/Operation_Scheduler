package com.example.op_sch.GUI.AllScreens;

import com.example.op_sch.EntryPoint;
import com.example.op_sch.GUI.Screen;
import javafx.fxml.FXML;
import com.example.op_sch.Worker;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class Dashboard implements Screen {

    private  Node root;
    private Worker doctor;



    @Override
    public String id() {
        return "DASHBOARD";
    }

    @Override
    public Node content() {
        return root;
    }

    @Override
    public void init() {
        FXMLLoader fxmlLoader = new FXMLLoader(EntryPoint.class.getResource("worker_dashboard.fxml"));
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setDoctor(Worker doctor){
        this.doctor= doctor;
    }
}
