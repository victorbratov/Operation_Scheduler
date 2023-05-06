package com.example.op_sch.GUI;

import com.example.op_sch.GUI.AllScreens.HomeScreen;

public class ScreenManagerFactory {
    private ScreenManager manager;
    //put all your screens as fields here
    private Screen homescreen;
    public ScreenManager newScreenManager(int width, int length){
        //init homescreen and all other screens
        homescreen = new HomeScreen();

        manager = new ScreenManager(width, length, homescreen);

        manager.goTo(homescreen.id());

        return manager;
    }
}
