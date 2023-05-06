package com.example.op_sch.GUI.AllScreens;

import com.example.op_sch.Main;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeScreen extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("homescreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Operation Scheduler");
        stage.setResizable(false);
        stage.setMaximized(false);
        stage.setScene(scene);
        stage.show();

    }
    public static void main(String[] args) {
        launch();
    }
}