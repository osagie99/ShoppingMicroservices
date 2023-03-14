package com.surge.ProductService.Exception;

public class LowerQuantityException extends RuntimeException{
    public LowerQuantityException(String message) {
        super(message);
    }
}
