package com.example.order.domain.exceptions;

public class WrongPassWordException extends RuntimeException{
    public WrongPassWordException() {super("Wrong username or password.");}
}
