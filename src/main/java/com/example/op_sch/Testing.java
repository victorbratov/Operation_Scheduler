package com.example.op_sch;

import java.util.Date;

public class Testing {

    public static void main(String[] args) {
        MedicalRecord medicalRecord1 = new MedicalRecord("Malaria");
        Patient patient1 = new Patient("XYZ", 13 , "Male" , medicalRecord1);
        System.out.println(patient1.getId());

        MedicalRecord medicalRecord2 = new MedicalRecord("Malaria");
        Patient patient2 = new Patient("ABC", 16 , "Female" , medicalRecord2);
        System.out.println(patient2.getId());

        Worker doctor1 = new Worker("Ram" , "Senior Doctor");

        Date date1 = new Date();
        doctor1.createAppointment(date1 , date1 , patient1);

    }

}
