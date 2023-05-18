package com.example.op_sch;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class AppointmentController {


    @FXML
    TextField patientName;
    @FXML
    TextField doctorName;
    @FXML
    TextField patientAge;
    @FXML
    TextField description;
    @FXML DatePicker datePicker;

    @FXML
    ChoiceBox choiceBox ;


    public void printData(){
        System.out.println(patientName.getText());
        System.out.println(patientAge.getText());
        System.out.println(doctorName.getText());
        System.out.println(description.getText());
        System.out.println(datePicker.getValue().toString());
    }


    public void createAppointment(){
        Appointment newAppointment = new Appointment(patientName.getText(), doctorName.getText() , description.getText(), choiceBox.getValue().toString() , 19);

        try {
            newAppointment.postAppointmentToBackend(newAppointment);
        }
        catch (Exception exception){
            System.out.println(exception.getMessage());
        }



    }





    public static void main(String[] args) {






    }

}
