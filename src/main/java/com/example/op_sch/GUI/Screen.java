package com.example.op_sch.GUI;

import javafx.scene.Node;

public interface Screen {
    String id();
    Node content();
    void init();
}
