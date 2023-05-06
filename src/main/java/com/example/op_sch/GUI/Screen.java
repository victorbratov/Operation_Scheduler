package com.example.op_sch.GUI;

import javafx.scene.Node;

import java.io.IOException;

public interface Screen {
    String id();
    Node content();
    void init();
}
