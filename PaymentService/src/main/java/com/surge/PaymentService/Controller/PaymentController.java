package com.surge.PaymentService.Controller;

import com.surge.PaymentService.Service.PaymentService;
import com.surge.PaymentService.dto.PaymentRequest;
import com.surge.PaymentService.dto.PaymentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/make-payment")
    private ResponseEntity<PaymentResponse> makePayment(@RequestBody PaymentRequest paymentRequest) {
        return new ResponseEntity<>(paymentService.makePayment(paymentRequest), HttpStatus.OK);
    }
    @GetMapping("/get-payment/{orderId}")
    private ResponseEntity<PaymentResponse> getPayment(@PathVariable long orderId) {
        return new ResponseEntity<>(paymentService.getPayment(orderId), HttpStatus.OK);
    }

}
