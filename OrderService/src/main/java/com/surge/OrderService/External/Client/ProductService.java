package com.surge.OrderService.External.Client;

import com.surge.OrderService.Exception.CustomException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "PRODUCT-SERVICE/products")
@CircuitBreaker(name = "external", fallbackMethod = "fallback")
public interface ProductService {
    @PutMapping("/reduceQuantity/{id}")
    ResponseEntity<Void> reduceQuantity(@PathVariable("id")  String productId, @RequestParam long quantity);

    default void fallback(Exception e) {
        throw new CustomException("Payment Service Unavailable", "UNAVAILABLE", 500);
    }
}
