package com.example.op_sch.GUI;

import com.example.op_sch.GUI.AllScreens.*;

public class ScreenManagerFactory {
    public ScreenManager newScreenManager(int width, int length){
        Screen homescreen = new HomeScreen();
        Screen login_screen = new LoginScreen();
        Screen register_screen = new RegisterScreen();
        Screen dashboard_screen = new Dashboard();
        Screen appointment_booking_screen = new AppointmentBooking();

        ScreenManager manager = new ScreenManager(width, length, homescreen, login_screen, register_screen , dashboard_screen, appointment_booking_screen);
        manager.goTo(homescreen.id());
        return manager;
    }
}
