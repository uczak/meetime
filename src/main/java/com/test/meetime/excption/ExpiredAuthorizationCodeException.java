package com.test.meetime.excption;

public class ExpiredAuthorizationCodeException extends RuntimeException {
    public ExpiredAuthorizationCodeException(String message) {
        super(message); // Send to global error
    }
}
