package com.example.order.api;

import com.example.order.domain.exceptions.UnauthorizedException;
import com.example.order.domain.exceptions.UnknownUserException;
import com.example.order.domain.exceptions.WrongPassWordException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.NoSuchElementException;

@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    protected void illegalArgumentException(IllegalArgumentException ex, HttpServletResponse response) throws IOException {

        response.sendError(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    protected void runtimeException(RuntimeException ex, HttpServletResponse response) throws IOException {

        response.sendError(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    @ExceptionHandler(NoSuchElementException.class)
    protected void noSuchElementException(NoSuchElementException ex, HttpServletResponse response) throws IOException {

        response.sendError(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ExceptionHandler(UnauthorizedException.class)
    protected void noSuchElementException(UnauthorizedException ex, HttpServletResponse response) throws IOException {

        response.sendError(HttpStatus.FORBIDDEN.value(), ex.getMessage());
    }
    @ExceptionHandler(UnknownUserException.class)
        protected void noSuchElementException(UnknownUserException ex, HttpServletResponse response) throws IOException {

        response.sendError(HttpStatus.FORBIDDEN.value(), ex.getMessage());
    }

    @ExceptionHandler(WrongPassWordException.class)
    protected void noSuchElementException(WrongPassWordException ex, HttpServletResponse response) throws IOException {

        response.sendError(HttpStatus.FORBIDDEN.value(), ex.getMessage());
    }

}

