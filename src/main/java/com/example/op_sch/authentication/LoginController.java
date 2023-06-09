package com.example.op_sch.authentication;

import com.example.op_sch.EntryPoint;
import com.example.op_sch.diary.DashBoardController;
import com.example.op_sch.professionals.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * The type Login controller.
 */
public class LoginController {

    /**
     * The Email text field.
     */
    @FXML
    TextField emailTextField, /**
     * The Password text field.
     */
    passwordTextField;
    /**
     * The Response.
     */
    @FXML
    Label response;


    /**
     * Get login string.
     *
     * @return the string
     */
    public String getLogin() {
        return emailTextField.getText();
    }


    /**
     * Validation.
     * this method validates the details that the user inputs, and gives them essential responses
     *
     * @param email the email
     */
    public void validation(String email) {
        if (emailTextField.getText().isEmpty() || passwordTextField.getText().isEmpty()) {
            response.setText("Enter all credentials to continue");
            return;
        }
        Worker worker = Worker.getWorkerByEmail(email);

        if (worker != null && worker.getPassword().equals(passwordTextField.getText())) {
            DashBoardController.setDoctor(worker);
            goTODashBoard();
        } else {
            response.setText("Wrong Login or Password");
        }
    }

    /**
     * Go back.
     */
    public void goBack() {
        EntryPoint.manager().goTo("HOME_SCREEN");
    }

    /**
     * Go to dash board.
     */
    public void goTODashBoard() {
        EntryPoint.manager().goTo("DASHBOARD");
    }

    /**
     * Login -> this is the main method, which checks in for all the validation and then logs the worker in to the diary dashboard.
     */
    public void login() {
        validation(getLogin());
    }
}
