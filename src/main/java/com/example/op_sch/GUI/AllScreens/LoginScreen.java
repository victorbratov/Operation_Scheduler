package com.example.op_sch.GUI.AllScreens;

import com.example.op_sch.EntryPoint;
import com.example.op_sch.GUI.Screen;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;

public class LoginScreen implements Screen {
    private Node root;

    @Override
    public String id() {
        return "LOGIN_SCREEN";
    }

    @Override
    public Node content() {
        return root;
    }

    @Override
    public void init() {
        FXMLLoader fxmlLoader = new FXMLLoader(EntryPoint.class.getResource("login_screen.fxml"));
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
