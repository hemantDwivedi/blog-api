package com.blog.me.blogging.exceptions;


public class ResourceNotFoundException extends RuntimeException{
    long fieldValue;
    String stringFieldValue;

    public ResourceNotFoundException(String message, long fieldValue) {
        super(message);
        this.fieldValue = fieldValue;
    }

    public ResourceNotFoundException(String message, String stringFieldValue) {
        super(message);
        this.stringFieldValue = stringFieldValue;
    }
}
