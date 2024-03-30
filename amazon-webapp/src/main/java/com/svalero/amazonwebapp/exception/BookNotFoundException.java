package com.svalero.amazonwebapp.exception;

public class BookNotFoundException extends Exception {

    public BookNotFoundException(String message) {
        super(message);
    }

    public BookNotFoundException() {
        super("El libro no existe");
    }
}
