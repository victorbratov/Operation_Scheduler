package com.example.op_sch.dataStructures;

import com.example.op_sch.generalClasses.Action;
import com.example.op_sch.patients.Appointment;

public class UndoStackNode {
    private UndoStackNode next;
    private Appointment appointment;
    private Action action;

    public UndoStackNode(Appointment appointment, Action action) {
        this.appointment = appointment;
        this.action = action;
    }

    public UndoStackNode getNext() {
        return next;
    }

    public void setNext(UndoStackNode next) {
        this.next = next;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public Action getAction() {
        return action;
    }
}
