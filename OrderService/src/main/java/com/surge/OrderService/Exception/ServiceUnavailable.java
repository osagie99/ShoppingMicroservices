package com.surge.OrderService.Exception;

public class ServiceUnavailable extends RuntimeException{

    private String errorCode;
    private int status;
    public ServiceUnavailable(String message, String errorCode, int status) {
        super(message);
        this.errorCode = errorCode;
        this.status = status;
    }

    public ServiceUnavailable() {

    }
}
