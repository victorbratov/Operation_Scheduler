package com.example.op_sch;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class Main {


    public static void main(String[] args){
        Worker operator = new Worker();
        Worker doc1 = new Worker("khushaal", "testing");

        operator.postWorkerToBackend(doc1);
        System.out.println(operator.getWorkersFromBackend());

    }
}
