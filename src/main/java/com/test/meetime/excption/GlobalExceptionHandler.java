package com.test.meetime.excption;

import com.test.meetime.model.HubspotErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ExpiredAuthorizationCodeException.class)
    public ResponseEntity<HubspotErrorResponse> handleExpiredAuthorizationCodeException(ExpiredAuthorizationCodeException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Authorization code expired", ex.getMessage());
    }

    @ExceptionHandler(OAuthIntegrationException.class)
    public ResponseEntity<HubspotErrorResponse> handleOAuthIntegrationException(OAuthIntegrationException ex) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "OAuth integration error", ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<HubspotErrorResponse> handleGeneralError(Exception ex) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error", ex.getMessage());
    }

    @ExceptionHandler(ExpiredAccessTokenException.class)
    public ResponseEntity<HubspotErrorResponse> handleExpiredAccessTokenException(Exception ex) {
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, "Access token expired.", ex.getMessage());
    }

    @ExceptionHandler(InvalidAccessTokenException.class)
    public ResponseEntity<HubspotErrorResponse> handleInvalidAccessTokenException(Exception ex) {
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, "Access token invalid.", ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<HubspotErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        HubspotErrorResponse error = new HubspotErrorResponse(400, errors.toString(), null, Instant.now().toString());
        return ResponseEntity.badRequest().body(error);
    }

    private ResponseEntity<HubspotErrorResponse> buildErrorResponse(HttpStatus status, String error, String message) {
        HubspotErrorResponse response = HubspotErrorResponse.builder()
                .status(status.value())
                .error(error)
                .message(message)
                .timestamp(Instant.now().toString())
                .build();

        return new ResponseEntity<>(response, status);
    }
}