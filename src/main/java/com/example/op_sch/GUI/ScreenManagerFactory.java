package com.example.op_sch.GUI;

import com.example.op_sch.GUI.AllScreens.HomeScreen;
import com.example.op_sch.GUI.AllScreens.LoginScreen;
import com.example.op_sch.GUI.AllScreens.RegisterScreen;

public class ScreenManagerFactory {
    public ScreenManager newScreenManager(int width, int length){
        Screen homescreen = new HomeScreen();
        Screen login_screen = new LoginScreen();
        Screen register_screen = new RegisterScreen();
        ScreenManager manager = new ScreenManager(width, length, homescreen, login_screen, register_screen);
        manager.goTo(homescreen.id());
        return manager;
    }
}
