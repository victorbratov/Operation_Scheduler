package com.example.op_sch.GUI;

public class ScreenManagerFactory {
    private ScreenManager manager;
    //put all your screens as fields here
    private Screen homescreen;
    public ScreenManager newScreenManager(int width, int length){
        //init homescreen and all other screens

        manager = new ScreenManager(width, length, homescreen);

        return manager;
    }
}
