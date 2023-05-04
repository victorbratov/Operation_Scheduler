package com.example.op_sch;

import jakarta.persistence.*;

import java.util.Date;


@Entity
@Table(name = "professionals")

public class Worker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "POSITION")
    private String position;

    public Worker() {
    }

    public Worker(String name, String position) {
        this.name = name;
        this.position = position;
    }


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


    @Override
    public String toString() {
        return "Worker{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", position='" + position + '\'' +
                '}';
    }
}
