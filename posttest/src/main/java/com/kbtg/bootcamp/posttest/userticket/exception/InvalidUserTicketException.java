package com.kbtg.bootcamp.posttest.userticket.exception;

public class InvalidUserTicketException extends RuntimeException {
    public InvalidUserTicketException(String errorMessage) {
        super(errorMessage);
    }
}
