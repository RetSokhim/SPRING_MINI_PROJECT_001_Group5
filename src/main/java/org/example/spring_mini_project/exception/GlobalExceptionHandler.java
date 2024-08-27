package org.example.spring_mini_project.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.example.spring_mini_project.model.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.time.LocalDateTime;
import java.util.HashMap;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(
                e.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> handleConflictException(ConflictException e) {
        ErrorResponse errorResponse = new ErrorResponse(
                e.getMessage(),
                HttpStatus.CONFLICT.value(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponse> handleForbiddenException(ForbiddenException e) {
        ErrorResponse errorResponse = new ErrorResponse(
                e.getMessage(),
                HttpStatus.FORBIDDEN.value(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException e) {
        ErrorResponse errorResponse = new ErrorResponse(
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail methodArgumentNotValidException (MethodArgumentNotValidException e){
        HashMap<String,String> errors = new HashMap<>();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,null);
        problemDetail.setTitle("BAD_REQUEST");
        problemDetail.setProperty("Time Stamp", LocalDateTime.now());
        problemDetail.setProperty("Errors",errors);
        return problemDetail;
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail constraintViolationException (ConstraintViolationException e){
        HashMap<String,String> errors = new HashMap<>();
        for(ConstraintViolation<?> error : e.getConstraintViolations()){
            errors.put(error.getPropertyPath().toString().substring(error.
                                    getPropertyPath().
                                    toString().
                                    indexOf(".")+1).
                            replaceFirst("\\.",""),
                    error.getMessage());
        }
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                "Invalid Input");
        problemDetail.setTitle("BAD_REQUEST");
        problemDetail.setProperty("Time Stamp",LocalDateTime.now());
        problemDetail.setProperty("Errors",errors);
        return problemDetail;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ErrorResponse jsonRequestException (HttpMessageNotReadableException e){
        return new ErrorResponse(
                "Error Json Input format",
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(PasswordException.class)
    public ResponseEntity<ErrorResponse> handlePasswordException(PasswordException e) {
        ErrorResponse errorResponse = new ErrorResponse(
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
