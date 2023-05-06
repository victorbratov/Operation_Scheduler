package com.example.op_sch;

import com.example.op_sch.GUI.ScreenManagerFactory;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

import static javafx.application.Application.launch;

public class EntryPoint extends Application {
    private final static int WIDTH = 900;
    private final static int LENGTH = 1440;

    public static void main(String[] args){
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        var factory = new ScreenManagerFactory();
        var manager = factory.newScreenManager(WIDTH, LENGTH);
        var root = manager.root();
        var scene = new Scene(root, WIDTH, LENGTH);
        try{
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("com/example/op_sch/Styles.css")).toExternalForm());
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        stage.setScene(scene);
        stage.show();
    }
}
