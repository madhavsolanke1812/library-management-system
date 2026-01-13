package com.booktrack.librarymanagementsystem.exception;


public class NotFoundException extends RuntimeException {

    public NotFoundException() {
        super("Resource not Found");
    }

    public NotFoundException(String msg) {
        super(msg);
    }
}