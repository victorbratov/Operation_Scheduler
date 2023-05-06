package com.example.op_sch.GUI;

import com.example.op_sch.GUI.AllScreens.HomeScreen;
import com.example.op_sch.GUI.AllScreens.LoginScreen;

public class ScreenManagerFactory {
    private ScreenManager manager;
    //put all your screens as fields here
    private Screen homescreen;
    private Screen login_screen;
    public ScreenManager newScreenManager(int width, int length){

        Runnable goToLogin = () ->
        {
            manager.goTo(login_screen.id());
        };
        //init homescreen and all other screens
        homescreen = new HomeScreen(goToLogin);
        login_screen = new LoginScreen();

        manager = new ScreenManager(width, length, homescreen);

        manager.goTo(homescreen.id());

        return manager;
    }
}
