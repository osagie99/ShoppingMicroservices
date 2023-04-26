package com.surge.OrderService.External.Client;

import com.surge.OrderService.Dto.BaseResponse;
import com.surge.OrderService.Dto.PaymentRequest;
import com.surge.OrderService.Dto.PaymentResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class PaymentServiceFallBack implements PaymentService{
    @Override
    public ResponseEntity<BaseResponse> makePayment(PaymentRequest paymentRequest) {
        return null;
    }

    @Override
    public BaseResponse<PaymentResponse> getPayment(long orderId) {
        return (BaseResponse) BaseResponse.builder()
                .message("PAYMENT_SERVICE_UNAVAILABLE")
                .status("UNAVAILABLE22")
                .data(null)
                .build();
    }
}
