package com.example.op_sch;

import java.util.Date;

public class Worker {

    public Worker() {
    }

    public Worker(String name, String position) {
        this.name = name;
        this.position = position;
    }

    private String name;
    private String position;

    private int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void getTimeTable(){

    }

    public void getPatientAppointment(){
    }

    public void createAppointment(Date startTime, Date endTime ,Patient patient){

    }
    public void getPatientAppointmentInfo(int appointmentId){



    }

    public void deleteAppointment(int appointmentId){

    }



}
