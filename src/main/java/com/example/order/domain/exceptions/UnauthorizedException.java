package com.example.order.domain.exceptions;

public class UnauthorizedException extends RuntimeException{
    public UnauthorizedException() {super("Unauthorized");}
}
