package com.taskease.doctorAppointment.Exception;

public class ErrorException extends RuntimeException{

    public ErrorException(String message) {
        super(message);
    }

    public ErrorException() {
        super();
    }
}
