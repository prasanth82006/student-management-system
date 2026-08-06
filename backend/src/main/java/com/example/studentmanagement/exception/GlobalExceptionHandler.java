package com.example.studentmanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Handle specific exceptions (404 Not Found)
    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleStudentNotFoundException(StudentNotFoundException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    // Handle generic custom RuntimeExceptions (like duplicate email) (400 Bad Request)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorDetails> handleRuntimeException(RuntimeException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    // Handle validation errors (e.g., @NotBlank, @Email)
    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> handleValidationExceptions(org.springframework.web.bind.MethodArgumentNotValidException ex, WebRequest request) {
        StringBuilder errors = new StringBuilder();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("; ");
        });
        
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                "Validation Failed",
                errors.toString()
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    // Handle all other global exceptions (500 Internal Server Error)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                "An unexpected error occurred",
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
