package com.ead.course.service;

public class InvalidSubscriptionException extends RuntimeException {

    private static final long serialVersionUID = 8348481342219848617L;

    public InvalidSubscriptionException(String message) {
        super(message);
    }

}
