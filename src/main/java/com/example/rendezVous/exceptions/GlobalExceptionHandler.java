package com.example.rendezVous.exceptions;

import org.apache.tomcat.util.buf.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(Exception ex){

        ApiError apiError = new ApiError.
                Builder()
                .withMessage(ex.getMessage())
                .withHttpStatus(HttpStatus.NOT_FOUND)
                .withCreatedAt()
                .build();

        return new ResponseEntity<>(apiError,apiError.getHttpStatus());
    }

  /*  @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception exception){
        ApiError apiError = new ApiError.
                Builder()
                .withMessage("Some Error Occurred")
                .withHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .withCreatedAt()
                .build();

        return new ResponseEntity<>(apiError,apiError.getHttpStatus());
    }*/

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        ApiError apiError = new ApiError.
                Builder()
                .withMessage(e.getMessage())
                .withHttpStatus(HttpStatus.UNPROCESSABLE_ENTITY)
                .withCreatedAt()
                .build();

        return new ResponseEntity<>(apiError,apiError.getHttpStatus());
    }
}
