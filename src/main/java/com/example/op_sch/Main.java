package com.example.op_sch;

public class Main {


    public static void main(String[] args){
        Worker operator = new Worker();
        Worker doc1 = new Worker("@Viktor", "Viktor Bratov", "doc", "pass");

        operator.postWorkerToBackend(doc1);
        System.out.println(operator.getWorkersFromBackend());

    }
}
