package com.example.op_sch;

import com.example.op_sch.GUI.ScreenManager;
import com.example.op_sch.GUI.ScreenManagerFactory;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class EntryPoint extends Application {
    private final static int WIDTH = 900;
    private final static int LENGTH = 1440;
    private static ScreenManager manager;

    public static void main(String[] args){
        launch();
    }

    @Override
    public void start(Stage stage){
        var factory = new ScreenManagerFactory();
        manager = factory.newScreenManager(WIDTH, LENGTH);
        var root = manager.root();
        var scene = new Scene(root, 1311 , 749);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("Styles.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public static ScreenManager manager() {
        return manager;
    }
}
