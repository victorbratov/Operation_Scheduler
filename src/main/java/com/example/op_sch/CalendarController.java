package com.example.op_sch;

import com.example.op_sch.GUI.Screen;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

public class CalendarController implements Screen {
    @FXML
    private Label day1Label;
    @FXML
    private Label day2Label;
    @FXML
    private Label day3Label;
    @FXML
    private Label day4Label;
    @FXML
    private Label day5Label;
    @FXML
    private Label day6Label;
    @FXML
    private Label day7Label;
    @FXML
    private Button prevButton;
    @FXML
    private Button nextButton;

    private LocalDate currentDate;

    private Node root;


    @Override
    public String id() {
        return "CALENDAR_VIEW";
    }

    @Override
    public Node content() {
        return root;
    }

    @Override
    public void init() {
        FXMLLoader fxmlLoader = new FXMLLoader(EntryPoint.class.getResource("calender.fxml"));
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentDate = LocalDate.now();

        // Update the calendar view
        updateCalendar();

        // Add event handlers for previous and next month buttons
        prevButton.setOnAction(event -> previousMonth());
        nextButton.setOnAction(event -> nextMonth());
    }

    private void updateCalendar() {

        // Calculate the first day of the current month
        LocalDate firstDayOfMonth = currentDate.with(TemporalAdjusters.firstDayOfMonth());

        // Display the calendar days
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d");
        day1Label.setText(formatter.format(firstDayOfMonth));
        day2Label.setText(formatter.format(firstDayOfMonth.plusDays(1)));
        day3Label.setText(formatter.format(firstDayOfMonth.plusDays(2)));
        day4Label.setText(formatter.format(firstDayOfMonth.plusDays(3)));
        day5Label.setText(formatter.format(firstDayOfMonth.plusDays(4)));
        day6Label.setText(formatter.format(firstDayOfMonth.plusDays(5)));
        day7Label.setText(formatter.format(firstDayOfMonth.plusDays(6)));
    }

    private void previousMonth() {
        currentDate = currentDate.minusMonths(1);
        updateCalendar();
    }

    private void nextMonth() {
        currentDate = currentDate.plusMonths(1);
        updateCalendar();
    }
}
