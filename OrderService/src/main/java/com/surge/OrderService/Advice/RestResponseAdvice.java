package com.surge.OrderService.Advice;

import com.surge.OrderService.Exception.CustomException;
import com.surge.OrderService.External.Response.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(CustomException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage customException(Exception e) {
        return new ErrorMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
