package com.radmilo.taskmanager.handler;

import com.radmilo.taskmanager.error.Errors;
import com.radmilo.taskmanager.exception.TaskNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class TaskExceptionHandler {

    @ExceptionHandler({TaskNotFoundException.class})
    public ResponseEntity<String> handleTaskNotFoundException(TaskNotFoundException taskNotFoundException) {
        return new ResponseEntity<>(taskNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<Errors> taskErrors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            taskErrors.add(new Errors(error.getField(), error.getDefaultMessage()));
        }
        Map<String, List<Errors>> taskErrorsResponse = new HashMap<>();
        taskErrorsResponse.put("Errors", taskErrors);
        return new ResponseEntity<>(taskErrorsResponse, HttpStatus.BAD_REQUEST);
    }

}
