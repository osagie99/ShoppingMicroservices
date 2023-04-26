package com.surge.OrderService.Service;

import com.surge.OrderService.Dto.BaseResponse;
import com.surge.OrderService.Dto.OrderRequest;
import com.surge.OrderService.Dto.OrderResponse;

public interface OrderService {
    BaseResponse placeOrder(OrderRequest orderRequest);
    BaseResponse getOrder(long id);
}
