package com.sakuraplanner.backend.exception;

import lombok.Getter;

/**
 * Custom runtime exception used to halt business logic and trigger a coded error response.
 */
@Getter
public class BusinessException extends RuntimeException {
    
    private final String errorCode;

    public BusinessException(String errorCode) {
        super(errorCode); // Sets the code as the default exception message
        this.errorCode = errorCode;
    }
}