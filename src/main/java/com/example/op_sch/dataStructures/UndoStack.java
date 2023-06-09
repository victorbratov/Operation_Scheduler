package com.example.op_sch.dataStructures;

import com.example.op_sch.generalClasses.Action;
import com.example.op_sch.patients.Appointment;

public class UndoStack {
    private UndoStackNode head;

    public UndoStack() {
        head = null;
    }

    public void push(Appointment appointment, Action action) {
        if (head == null) {
            head = new UndoStackNode(appointment, action);
        } else {
            UndoStackNode node = new UndoStackNode(appointment, action);
            node.setNext(head);
            head = node;
        }
    }

    public UndoStackNode pop() {
        if (head == null) {
            return null;
        }
        UndoStackNode node = head;
        head = head.getNext();
        return node;
    }

    public boolean isEmpty() {
        return head == null;
    }
}
