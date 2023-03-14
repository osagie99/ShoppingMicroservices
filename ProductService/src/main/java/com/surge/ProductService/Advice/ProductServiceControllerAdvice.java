package com.surge.ProductService.Advice;

import com.surge.ProductService.Exception.LowerQuantityException;
import com.surge.ProductService.Exception.ProductNotFoundException;
import com.surge.ProductService.Model.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ProductServiceControllerAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorMessage productNotFound(Exception e) {
        return new ErrorMessage(e.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(LowerQuantityException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessage lowerQuantity(Exception e) {
        return new ErrorMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
