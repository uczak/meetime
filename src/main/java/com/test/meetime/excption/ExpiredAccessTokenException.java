package com.test.meetime.excption;

public class ExpiredAccessTokenException extends RuntimeException {
    public ExpiredAccessTokenException(String message) {
        super(message);
    }
}
