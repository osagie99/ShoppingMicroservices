package com.surge.OrderService.External.Decoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.surge.OrderService.Exception.CustomException;
import com.surge.OrderService.External.Response.ErrorMessage;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String s, Response response) {
        ObjectMapper objectMapper = new ObjectMapper();

        log.info("::{}", response.request().url());
        log.info("::{}", response.request().headers());

        try {
            ErrorMessage errorMessage = objectMapper.readValue(response.body().asInputStream(), ErrorMessage.class);
            return new CustomException(errorMessage.getMessage(), errorMessage.getMessage(), response.status());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
