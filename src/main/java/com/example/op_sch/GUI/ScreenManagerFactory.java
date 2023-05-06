package com.example.op_sch.GUI;

import com.example.op_sch.GUI.AllScreens.HomeScreen;
import com.example.op_sch.GUI.AllScreens.LoginScreen;

public class ScreenManagerFactory {
    public ScreenManager newScreenManager(int width, int length){
        //put all your screens as fields here
        //init homescreen and all other screens

        Screen homescreen = new HomeScreen();
        Screen login_screen = new LoginScreen();

        ScreenManager manager = new ScreenManager(width, length, homescreen, login_screen);

        manager.goTo(homescreen.id());

        return manager;
    }
}
