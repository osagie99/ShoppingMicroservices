package com.surge.OrderService.Advice;

import com.surge.OrderService.Dto.BaseResponse;
import com.surge.OrderService.Exception.CustomException;
import com.surge.OrderService.Exception.ServiceUnavailable;
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
    public CustomException customException(Exception e) {
        return new CustomException(e.getMessage(),"UNAVAILABLE", 500 );
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ExceptionHandler(ServiceUnavailable.class)
    public BaseResponse serviceUnavailable(Exception e) {
        BaseResponse baseResponse = BaseResponse.builder()
                .data(null)
                .message("SERVICE_UNAVAILABLE")
                .status("UNAVAILABLE")
                .build();
        return baseResponse;
    }
}
