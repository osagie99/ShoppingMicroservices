package com.surge.OrderService.External.Client;

import com.surge.OrderService.Dto.PaymentRequest;
import com.surge.OrderService.Dto.PaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "PAYMENT-SERVICE/payment")
public interface PaymentService {
    @PostMapping("/make-payment")
    ResponseEntity<PaymentResponse> makePayment(@RequestBody PaymentRequest paymentRequest);
    @GetMapping("/get-payment/{orderId}")
    ResponseEntity<PaymentResponse> getPayment(@PathVariable long orderId);
}
