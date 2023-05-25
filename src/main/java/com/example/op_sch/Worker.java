package com.example.op_sch;

import jakarta.persistence.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.jdbc.Work;

import java.util.*;


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
    public Set getWorkersFromBackend(){
        Set<Worker> workerSet = new HashSet<>();
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List< Worker > students = session.createQuery("from Worker ", Worker.class).list();
            students.forEach(student ->{
                workerSet.add(student);
            });
            System.out.println( "Worker Size : " + workerSet.size());
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }


        return  workerSet;

    }

    public void postWorkerToBackend(Worker worker){
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            // save the student objects
            session.persist(worker);
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Worker worker = new Worker("khush", "testing");
        worker.getWorkersFromBackend().toString();
        System.out.println(Arrays.toString(worker.getWorkersFromBackend().toArray()));
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
