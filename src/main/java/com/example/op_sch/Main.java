package com.example.op_sch;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class Main {


    public static void main(String[] args){
        Worker doc1 = new Worker("Brown", "Eye doctor");
        Worker doc2 = new Worker("Green", "Leg doctor");

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            // save the student objects
            session.persist(doc1);
            session.persist(doc2);
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List < Worker > students = session.createQuery("from Worker ", Worker.class).list();
            students.forEach(System.out::println);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
