package com.surge.OrderService.Dto;

import com.surge.OrderService.Enums.PaymentMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequest {
    private long orderId;
    private long amount;
    private String referenceNumber;
    private PaymentMode paymentMode;
}
