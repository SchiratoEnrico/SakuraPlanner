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
}