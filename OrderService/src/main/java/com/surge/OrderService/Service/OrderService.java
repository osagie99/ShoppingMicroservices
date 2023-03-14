package com.surge.OrderService.Service;

import com.surge.OrderService.Dto.OrderRequest;
import com.surge.OrderService.Dto.OrderResponse;

public interface OrderService {
    OrderResponse placeOrder(OrderRequest orderRequest);
}
