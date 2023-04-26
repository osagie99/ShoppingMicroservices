package com.surge.PaymentService.Service;

import com.surge.PaymentService.dto.BaseResponse;
import com.surge.PaymentService.dto.PaymentRequest;

public interface PaymentService {
    BaseResponse makePayment(PaymentRequest paymentRequest);

    BaseResponse getPayment(long orderId);
}
