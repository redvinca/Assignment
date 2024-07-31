package com.redvinca.assignment.ecom_backend.exception;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.redvinca.assignment.ecom_backend.response.MessageResponse;

/**
 * Global exception handler to handle exceptions thrown across the application.
 */
@ControllerAdvice
@AllArgsConstructor
public class GlobalExceptionHandler {

    /**
     * Exception handler for ProductNotFoundException.
     * 
     * @param ex the ProductNotFoundException instance.
     * @return ResponseEntity containing the error message.
     */
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<MessageResponse> handleGlobalException(ProductNotFoundException ex) {
        // Create a MessageResponse containing the exception message
        MessageResponse messageResponse = new MessageResponse(ex.getMessage());
        // Return ResponseEntity with HTTP status code 400 (Bad Request)
        return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String errorMessage = error.getDefaultMessage();
            errors.put("errorMessage", errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("errorMessage", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
