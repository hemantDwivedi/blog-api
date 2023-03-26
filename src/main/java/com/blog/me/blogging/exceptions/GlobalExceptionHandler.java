package com.blog.me.blogging.exceptions;

import com.blog.me.blogging.payloads.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException resourceNotFoundException){
        String message = resourceNotFoundException.getMessage();
        APIResponse apiResponse = new APIResponse(message);
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> argsNotValidExceptionHandler(MethodArgumentNotValidException methodArgumentNotValidException){
        Map<String, String> res = new HashMap<>();

        methodArgumentNotValidException.getBindingResult().getAllErrors().forEach((error) -> {
            String field = ((FieldError)error).getField();
            String errorMessage = error.getDefaultMessage();
            res.put(field, errorMessage);
        });
        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(APIException.class)
    public ResponseEntity<APIResponse> handleAPIException(APIException e){
        String message = e.getMessage();
        APIResponse response = new APIResponse(message);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
