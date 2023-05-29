package com.example.op_sch;

import javafx.fxml.FXML;
import javafx.scene.control.*;

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


    public  void validation() {
        if(patientName.getText() == null || patientName.getText()==""){
            CustomAlert alert = new CustomAlert();
            alert.showAlert("Enter Patient's Name" , "In order to move ahead, you need to complete all details!");
            return;
        }
        if(patientAge.getText() == null || patientAge.getText()==""){
            CustomAlert alert = new CustomAlert();
            alert.showAlert("Enter Patient's Age" , "In order to move ahead, you need to complete all details!");
            return;
        }

        if(doctorName.getText() == null || doctorName.getText()==""){
            CustomAlert alert = new CustomAlert();
            alert.showAlert("Enter Doctor's Name" , "In order to move ahead, you need to complete all details!");
            return;
        }
        if(description.getText() == null || description.getText()==""){
            CustomAlert alert = new CustomAlert();
            alert.showAlert("Enter Description of your visit" , "In order to move ahead, you need to complete all details!");
            return;
        }
        if (datePicker.getValue() == null || datePicker.getValue().toString() ==""){
            CustomAlert alert = new CustomAlert();
            alert.showAlert("Enter Date of the Appointment" , "In order to move ahead, you need to complete all details!");
            return;
        }
    }


    public  void checkAvailableTimes(String doctorName) {

    }


    public void createAppointment(){

        validation();
        Appointment newAppointment = new Appointment(patientName.getText(), doctorName.getText() , description.getText(), choiceBox.getValue().toString() , 19 , datePicker.getValue().toString(), "12:00");
        try {
            newAppointment.postAppointmentToBackend(newAppointment);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Booking Successful");
            alert.setContentText("Your booking has been successfully made, click below to go back");
            ButtonType buttonTypeOne = new ButtonType("Go Back");
            alert.getButtonTypes().setAll(buttonTypeOne);
            alert.showAndWait().ifPresent(event ->{
                if(event == buttonTypeOne){
                    EntryPoint.manager().goTo("HOME_SCREEN");
                }
            });



        }
        catch (Exception exception){
            System.out.println(exception.getMessage());
        }



    }

    public void goBack(){
        EntryPoint.manager().goTo("HOME_SCREEN");
    }





    public static void main(String[] args) {






    }

}
