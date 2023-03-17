package com.surge.PaymentService.Service;

import com.surge.PaymentService.dto.PaymentRequest;
import com.surge.PaymentService.dto.PaymentResponse;

public interface PaymentService {
    PaymentResponse makePayment(PaymentRequest paymentRequest);

    PaymentResponse getPayment(long orderId);
}
