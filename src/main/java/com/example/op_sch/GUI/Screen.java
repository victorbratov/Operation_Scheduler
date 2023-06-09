package com.example.op_sch.GUI;

import javafx.scene.Node;

/**
 * The interface Screen.
 */
public interface Screen {
    /**
     * Id string.
     *
     * @return the string
     */
    String id();

    /**
     * Content node.
     *
     * @return the node
     */
    Node content();

    /**
     * Init.
     */
    void init();
}
