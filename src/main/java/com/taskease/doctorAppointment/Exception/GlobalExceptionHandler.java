package com.taskease.doctorAppointment.Exception;



import com.taskease.doctorAppointment.PayLoad.ApiResponse;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // for the resource not found exception
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundException(ResourceNotFoundException e) {

        String message = e.getMessage();
        ApiResponse apiResponse = new ApiResponse("500",message, false);
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
    }

    // For the Validation Error management
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidation(MethodArgumentNotValidException validException) {
        Map<String, String> resp = new HashMap<>();
        validException.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String Message = error.getDefaultMessage();
            resp.put(fieldName, Message);
        });

        return new ResponseEntity<>(new ApiResponse<>("500","",resp), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ErrorException.class)
    public ResponseEntity<ApiResponse> InvalidCredentials(ErrorException e) {
        String message = e.getMessage();
        ApiResponse apiResponse = new ApiResponse("500",message, false);
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse> InvalidPassword(IllegalArgumentException e) {
        String message = e.getMessage();
        ApiResponse apiResponse = new ApiResponse("500",message, false);
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ApiResponse> InvalidPassword(SQLIntegrityConstraintViolationException e) {
        String message = e.getMessage();
        ApiResponse apiResponse = new ApiResponse("500",message, false);
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ApiResponse> InvalidPassword(ExpiredJwtException e) {
        String message = e.getMessage();
        ApiResponse apiResponse = new ApiResponse("500",message, false);
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse> InvalidPassword(HttpRequestMethodNotSupportedException e) {
        String message = e.getMessage();
        ApiResponse apiResponse = new ApiResponse("500",message, false);
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiResponse> pageNotFound(NoHandlerFoundException e) {
        String message = e.getMessage();
        ApiResponse apiResponse = new ApiResponse("500",message, false);
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
    }



}
