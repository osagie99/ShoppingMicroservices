package com.surge.OrderService.External.Client;

import com.surge.OrderService.Config.FeignConfig;
import com.surge.OrderService.Dto.BaseResponse;
import com.surge.OrderService.Dto.PaymentRequest;
import com.surge.OrderService.Dto.PaymentResponse;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "PAYMENT-SERVICE/payment", configuration = FeignConfig.class,fallback = PaymentServiceFallBack.class)
@Component
public interface PaymentService {

    @PostMapping("/make-payment")
    ResponseEntity<BaseResponse> makePayment(@RequestBody PaymentRequest paymentRequest);
    @GetMapping("/get-payment/{orderId}")
    BaseResponse<PaymentResponse> getPayment(@PathVariable long orderId);

}



