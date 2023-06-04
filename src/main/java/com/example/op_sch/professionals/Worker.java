package com.example.op_sch.professionals;

import com.example.op_sch.dataStructures.CustomSet;
import com.example.op_sch.patients.Appointment;
import com.example.op_sch.HibernateUtil;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.*;


@Entity
@Table(name = "professionals")

public class Worker {

    @Id
    @Column(name = "EMAIL")
    private String email;

    @Column(name = "NAME")
    private String name;

    @Column(name = "POSITION")
    private String position;

    @Column(name = "PASSWORD")
    private String password;

    public Worker() {
    }

    public Worker(String email, String name, String position, String password) {
        this.email = email;
        this.name = name;
        this.position = position;
        this.password = password;
    }

    public static void main(String[] args) {
        Worker worker = new Worker("@Brown@gmail.com", "Surgeon", "kjjsa", "jhdchsb");
        worker.postWorkerToBackend(worker);
        worker.getWorkersFromBackend().toString();
        System.out.println(Arrays.toString(worker.getWorkersFromBackend().toArray()));
    }

    public static Worker getWorkerByEmail(String email) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Worker worker = session.createQuery(String.format("from Worker W where W.email = \"%s\"", email), Worker.class).uniqueResult();
            return worker;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getPassword() {
        return password;
    }

    public CustomSet<Worker> getWorkersFromBackend() {
        CustomSet<Worker> workerSet = new CustomSet<>();
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Worker> students = session.createQuery("from Worker ", Worker.class).list();
            students.forEach(student -> {
                workerSet.add(student);
            });
            System.out.println("Worker Size : " + workerSet.size());
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }


        return workerSet;

    }

    public void postWorkerToBackend(Worker worker) {
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

    public Set<Appointment> getAppointments(String workerName) {
        Set<Appointment> set = new HashSet<>();
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Appointment> appointments = session.createQuery(String.format("from Appointment A where A.name = %s", this.name), Appointment.class).list();
            set = Set.copyOf(appointments);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return set;
    }

    @Override
    public String toString() {
        return "Worker{" +
                "id=" + email +
                ", name='" + name + '\'' +
                ", position='" + position + '\'' +
                '}';
    }
}
