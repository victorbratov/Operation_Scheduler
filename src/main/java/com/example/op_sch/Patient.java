package com.example.op_sch;

import java.util.ArrayList;

public class Patient {
     private String name; // patient's name
     private int age; // patient's age
     private int id; // id assigned to the patient
     private String gender; // can be transformed into an Enum

     public int getId() {
          return id;
     }

     ArrayList<MedicalRecord> patientMedicalRecords; // to maintain a patient's medical records

     public Patient(String name, int age, String gender , MedicalRecord medicalRecord) {
          patientMedicalRecords = new ArrayList<MedicalRecord>();
          this.name = name;
          this.age = age;
          this.gender = gender;
          patientMedicalRecords.add(medicalRecord);
     }

     public Patient(){}
     public ArrayList<MedicalRecord> getPatientMedicalRecords(int id){
         return patientMedicalRecords;
     }

     public void addMedicalRecordToPatient(MedicalRecord medicalRecord , int patientId){
          patientMedicalRecords.add(medicalRecord);
     }
     public void deleteMedicalRecord(MedicalRecord medicalRecord , int patientId){
          patientMedicalRecords.remove(medicalRecord);
     }











}
