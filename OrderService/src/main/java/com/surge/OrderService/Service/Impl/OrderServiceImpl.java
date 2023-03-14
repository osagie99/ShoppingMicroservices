package com.surge.OrderService.Service.Impl;

import com.netflix.discovery.converters.Auto;
import com.surge.OrderService.Dto.OrderRequest;
import com.surge.OrderService.Dto.OrderResponse;
import com.surge.OrderService.Entity.OrderEntity;
import com.surge.OrderService.Enums.PaymentMode;
import com.surge.OrderService.External.Client.ProductService;
import com.surge.OrderService.Repository.OrderRepository;
import com.surge.OrderService.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductService productService;
    @Override
    public OrderResponse placeOrder(OrderRequest orderRequest) {
        OrderEntity orderEntity = OrderEntity.builder()
                .orderDate(Instant.now())
                .orderStatus("CREATED")
                .amount(orderRequest.getTotalAmount())
                .productId(orderRequest.getProductId())
                .quantity(orderRequest.getQuantity())
                .build();

        productService.reduceQuantity(orderRequest.getProductId(), orderRequest.getQuantity());

        orderRepository.save(orderEntity);

        return OrderResponse.builder()
                .quantity(orderRequest.getQuantity())
                .productId(orderRequest.getProductId())
                .paymentMode(PaymentMode.DEBIT_CARD)
                .totalAmount(orderRequest.getTotalAmount() * orderRequest.getQuantity())
                .build();
    }
}
