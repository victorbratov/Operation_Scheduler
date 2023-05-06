package com.example.op_sch.GUI.AllScreens;

import com.example.op_sch.EntryPoint;
import com.example.op_sch.GUI.Screen;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class HomeScreen implements Screen {
    private Node root;
    private Runnable goToLogin;

    public HomeScreen(Runnable goToLogin) {
        this.goToLogin = goToLogin;
    }

    @Override
    public String id() {
        return "HOME_SCREEN";
    }

    @Override
    public Node content() {
        return root;
    }

    @Override
    public void init(){
        FXMLLoader fxmlLoader = new FXMLLoader(EntryPoint.class.getResource("homescreen.fxml"));
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Button login_button = (Button) root.lookup("login_button");
        login_button.setOnAction(e -> goToLogin.run());
    }
}