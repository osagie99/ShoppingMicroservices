package com.surge.OrderService.External.Decoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.surge.OrderService.Exception.CustomException;
import com.surge.OrderService.Exception.ServiceUnavailable;
import com.surge.OrderService.External.Response.ErrorMessage;
import feign.Response;
import feign.codec.ErrorDecoder;
import jakarta.ws.rs.BadRequestException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String s, Response response) {
        return switch (response.status()) {
            case 500 -> new CustomException("UNAVAILBLE", "UNAVAILBLE", 500);
            case 400 -> new BadRequestException("Bad request");
            case 503 -> throw new ServiceUnavailable();
            default -> new Exception("Unavailable");
        };
    }
}
