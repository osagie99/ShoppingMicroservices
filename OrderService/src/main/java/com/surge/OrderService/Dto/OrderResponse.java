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
public class OrderResponse {
    private long quantity;
    private long totalAmount;
    private PaymentMode paymentMode;
    private String productId;
}
