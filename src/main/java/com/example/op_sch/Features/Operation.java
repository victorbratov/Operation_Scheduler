package com.example.op_sch.Features;

import com.example.op_sch.Appointment;
import com.example.op_sch.Features.OperationType;

public class Operation {
    private OperationType operationType;
    private Appointment originalAppointment;
    private Appointment modifiedAppointment;

    public Operation(OperationType operationType, Appointment originalAppointment, Appointment modifiedAppointment) {
        this.operationType = operationType;
        this.originalAppointment = originalAppointment;
        this.modifiedAppointment = modifiedAppointment;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public Appointment getOriginalAppointment() {
        return originalAppointment;
    }

    public Appointment getModifiedAppointment() {
        return modifiedAppointment;
    }
}
