package com.xishuai.demo.eurekaclient.Error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ResponseStatus(value = HttpStatus.OK)
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseBody
    public RestServiceError noHandlerFoundException(Exception ex) {
        return RestServiceError.build(HttpStatus.NOT_FOUND.value(), "Resource Not Found");
    }

    @ResponseStatus(value = HttpStatus.OK)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public RestServiceError handleException(Exception ex, HttpServletResponse response) {
        response.setHeader("X-Occurred-Error", "1");
        return RestServiceError.build(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
    }
}
