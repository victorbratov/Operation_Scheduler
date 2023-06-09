package com.example.op_sch.tests;

import com.example.op_sch.professionals.Worker;
import org.junit.Test;


import static org.junit.Assert.*;

public class WorkerTest {
    @Test
    public void testPostWorkerToBackend() {
        // create a worker instance for testing
        Worker worker = new Worker("new@example.com", "New Worker", "Position", "password");


        worker.postWorkerToBackend(worker);

        // then fetch the posted worker from the backend
          Worker retrievedWorker = Worker.getWorkerByEmail(worker.getEmail());
          assertNotNull(retrievedWorker);
          assertEquals(worker.getEmail(), retrievedWorker.getEmail());
          assertEquals(worker.getName(), retrievedWorker.getName());
          assertEquals(worker.getPosition(), retrievedWorker.getPosition());
    }

    @Test
    public void testGetWorkersFromBackend() {
        // test getting a worker form backend
        try {
          Worker worker = Worker.getWorkerByEmail("k@gmail.com");
          assertNotNull(worker);
          assertEquals(worker.getEmail(),  "k@gmail.com");
      }
      catch (Exception e){
      }
    }



}