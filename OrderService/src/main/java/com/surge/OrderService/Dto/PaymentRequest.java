package com.surge.PaymentService.dto;

import com.surge.PaymentService.enums.PaymentMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequest {
    private String orderId;
    private long amount;
    private String referenceNumber;
    private PaymentMode paymentMode;
}
