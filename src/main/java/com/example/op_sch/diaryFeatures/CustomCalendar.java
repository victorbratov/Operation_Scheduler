package com.example.op_sch.diaryFeatures;

import com.example.op_sch.patients.Appointment;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Set;

public class CustomCalendar {

    private boolean hasAppointmentsOnDate(LocalDate date ,ObservableList<Appointment>appointments) {


        for (Appointment appointment : appointments){
            if(date.toString().equals(appointment.getDate()) ){
                return true;
            }
        }

        // Implement your logic to check if the date has appointments
        // You can iterate through your appointments and compare the date with the appointment dates
        // Return true if there are appointments on the specified date, otherwise return false
        return false;
    }

    public void createCalendarView(YearMonth yearMonth, GridPane calendarGrid, ObservableList<Appointment> appointments) {
        // Clear the existing calendar view
        calendarGrid.getChildren().clear();

        // Get the number of days in the month
        int daysInMonth = yearMonth.lengthOfMonth();

        // Create labels for the days of the week
        String[] daysOfWeek = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
        for (int i = 0; i < daysOfWeek.length; i++) {
            Label dayLabel = new Label(daysOfWeek[i]);
            dayLabel.setAlignment(Pos.CENTER);
            dayLabel.setStyle("-fx-font-weight: bold;");
            calendarGrid.add(dayLabel, i, 0);
        }

        // Get the first day of the month
        LocalDate firstDayOfMonth = yearMonth.atDay(1);
        int startDayOfWeek = firstDayOfMonth.getDayOfWeek().getValue();

        // Create rectangles to represent the calendar days
        for (int day = 1; day <= daysInMonth; day++) {
            Rectangle dayRectangle = new Rectangle(30, 30, Color.TRANSPARENT);
            dayRectangle.setStroke(Color.BLACK);

            // Check if the day has appointments
            LocalDate currentDate = LocalDate.of(yearMonth.getYear(), yearMonth.getMonthValue(), day);
            boolean hasAppointments = hasAppointmentsOnDate(currentDate, appointments);
            if (hasAppointments) {
                dayRectangle.setFill(Color.RED);
            }

            // Create label for the date
            Label dateLabel = new Label(Integer.toString(day));
            dateLabel.setAlignment(Pos.CENTER);
            dateLabel.setStyle("-fx-font-weight: bold;");

            // Set the alignment of the dateLabel within the dayRectangle
            StackPane.setAlignment(dateLabel, Pos.CENTER);

            // Add the rectangle and date label to the calendar grid
            int column = (startDayOfWeek + day - 2) % 7;
            int row = (startDayOfWeek + day - 2) / 7 + 1; // Shift the row by 1 to accommodate the day labels
            calendarGrid.add(dayRectangle, column, row);
            calendarGrid.add(dateLabel, column, row);
        }
    }

}
