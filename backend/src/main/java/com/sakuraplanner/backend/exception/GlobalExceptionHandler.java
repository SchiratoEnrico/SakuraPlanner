package com.sakuraplanner.backend.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;

/**
 * Global interceptor that catches exceptions across all controllers 
 * and formats them into clean, localized JSON responses.
 */
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    /**
     * Intercepts BusinessException and resolves the error code using messages.properties.
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex) {
        String resolvedMessage;
        
        try {
            // LocaleContextHolder.getLocale() automatically detects the client's language 
            // from the HTTP "Accept-Language" header sent by the frontend
            resolvedMessage = messageSource.getMessage(ex.getErrorCode(), null, LocaleContextHolder.getLocale());
        } catch (Exception e) {
            // Fallback string if the error code is missing from the properties file
            resolvedMessage = "An unmapped error occurred. Code: " + ex.getErrorCode();
        }

        ErrorResponse errorPayload = new ErrorResponse(
                ex.getErrorCode(),
                resolvedMessage,
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorPayload, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(org.springframework.security.authentication.BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException() {
        String resolvedMessage = messageSource.getMessage("ERR_AUTH_001", null, LocaleContextHolder.getLocale());
        return new ResponseEntity<>(
                new ErrorResponse("ERR_AUTH_001", resolvedMessage, LocalDateTime.now()), 
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(org.springframework.security.authentication.DisabledException.class)
    public ResponseEntity<ErrorResponse> handleDisabledException() {
        String resolvedMessage = messageSource.getMessage("ERR_AUTH_004", null, LocaleContextHolder.getLocale());
        return new ResponseEntity<>(
                new ErrorResponse("ERR_AUTH_004", resolvedMessage, LocalDateTime.now()), 
                HttpStatus.FORBIDDEN
        );
    }
}