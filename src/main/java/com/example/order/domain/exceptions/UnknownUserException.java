package com.example.order.domain.exceptions;

public class UnknownUserException extends RuntimeException{
    public UnknownUserException() {
        super("Wrong username or password.");}
}
