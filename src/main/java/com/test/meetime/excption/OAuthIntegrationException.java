package com.test.meetime.excption;

public class OAuthIntegrationException extends RuntimeException {
    public OAuthIntegrationException(String message, Throwable cause) {
        super(message, cause); // Send to global error
    }
}
