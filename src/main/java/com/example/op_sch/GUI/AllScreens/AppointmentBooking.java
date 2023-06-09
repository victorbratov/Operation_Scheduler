package com.example.op_sch.GUI.AllScreens;

import com.example.op_sch.EntryPoint;
import com.example.op_sch.GUI.Screen;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;

public class AppointmentBooking implements Screen {
    private Node root;

    @Override
    public String id() {
        return "APPOINTMENT_BOOKING";
    }

    @Override
    public Node content() {
        return root;
    }

    @Override
    public void init() {
        FXMLLoader fxmlLoader = new FXMLLoader(EntryPoint.class.getResource("appointments-booking.fxml"));
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
