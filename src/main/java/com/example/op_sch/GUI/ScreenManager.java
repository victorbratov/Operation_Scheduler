package com.example.op_sch.GUI;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScreenManager {
    List<Screen> screens;
    private final Duration dur = Duration.millis(300);
    private final int WIDTH;
    private final int LENGTH;
    private Screen currentScreen;
    private StackPane root;
    private FadeTransition transition;

    public ScreenManager(int width, int length, Screen ...screens) {
        this.screens = List.of(screens);
        this.WIDTH = width;
        this.LENGTH = length;
        init();
    }

    private void init(){
        root = new StackPane();
        transition = new FadeTransition(dur);
        transition.setNode(root);
        transition.setFromValue(1);
        transition.setToValue(0);
        transition.setAutoReverse(true);
        transition.setCycleCount(2);
        transition.currentTimeProperty().addListener((observable, oldVal, newVal)->{
            if(transition.getNode().getOpacity()<0.01){
                root.getChildren().clear();
                root.getChildren().add(currentScreen.content());
            }
        });
    }

    public void goTo(String id){
        currentScreen = screens.stream()
                .filter(s -> s.id().equals(id)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Screen with ID\"" + id + "\" is not registered"));
        currentScreen.init();
        transition.playFromStart();
    }

    public Screen getCurrentScreen(){return currentScreen;}

    public StackPane root(){return root;}
}
