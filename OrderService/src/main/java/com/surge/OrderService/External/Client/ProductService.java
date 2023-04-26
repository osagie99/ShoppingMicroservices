package com.surge.OrderService.External.Client;

import com.surge.OrderService.Dto.BaseResponse;
import com.surge.OrderService.Dto.ProductResponse;
import com.surge.OrderService.Exception.CustomException;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;

@FeignClient(name = "PRODUCT-SERVICE/products")
@CircuitBreaker(name = "external")
public interface ProductService {
    @PutMapping("/reduceQuantity/{id}")
    ResponseEntity<Void> reduceQuantity(@PathVariable("id")  String productId, @RequestParam long quantity);

    @GetMapping("/{id}")
    public BaseResponse<ProductResponse> getProduct(@PathVariable String id);
}
