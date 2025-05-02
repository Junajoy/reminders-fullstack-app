package com.example.reminders_api.exceptions;

import com.example.reminders_api.dto.ReminderResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class GlobalHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ReminderResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        HashMap<String, String> errors = new HashMap<>();
        ex.getFieldErrors().stream()
                .forEach(fieldError -> errors.put(fieldError.getField(), fieldError.getDefaultMessage()));
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST) // Typically this should be 400 Bad Request
                .body(new ReminderResponse(HttpStatus.BAD_REQUEST, errors));
    }

    @ExceptionHandler(ReminderNotFoundException.class)
    public ResponseEntity<ReminderResponse> handleReminderNotFoundException(ReminderNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND) // 404 Not Found for non-existing reminders
                .body(new ReminderResponse(HttpStatus.NOT_FOUND, ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ReminderResponse> handleException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR) // 500 Internal Server Error for other exceptions
                .body(new ReminderResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()));
    }

    /**
     * Custom exception to be used when a reminder is not found by ID.
     */
    public static class ReminderNotFoundException extends RuntimeException {
        public ReminderNotFoundException(String message) {
            super(message);
        }
    }
}
