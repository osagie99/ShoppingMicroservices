package com.surge.OrderService.Service.Impl;

import com.netflix.discovery.converters.Auto;
import com.surge.OrderService.Dto.*;
import com.surge.OrderService.Entity.OrderEntity;
import com.surge.OrderService.Enums.PaymentMode;
import com.surge.OrderService.Exception.CustomException;
import com.surge.OrderService.External.Client.PaymentService;
import com.surge.OrderService.External.Client.ProductService;
import com.surge.OrderService.Repository.OrderRepository;
import com.surge.OrderService.Service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.UUID;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private RestTemplate restTemplate;

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

        log.info("Creating order with status Created");

        orderRepository.save(orderEntity);

        log.info("Calling payment service");

        PaymentRequest paymentRequest = PaymentRequest.builder()
                .orderId(orderEntity.getId())
                .referenceNumber(UUID.randomUUID().toString())
                .paymentMode(orderRequest.getPaymentMode())
                .amount(orderEntity.getAmount())
                .build();

        try{
            log.info("Calling make payment service");
            paymentService.makePayment(paymentRequest);
            log.info("Payment Successful");
            orderEntity.setOrderStatus("COMPLETED");
            orderRepository.save(orderEntity);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return OrderResponse.builder()
                .quantity(orderRequest.getQuantity())
                .productId(orderRequest.getProductId())
                .paymentMode(PaymentMode.DEBIT_CARD)
                .totalAmount(orderRequest.getTotalAmount() * orderRequest.getQuantity())
                .build();
    }

    @Override
    public OrderResponse getOrder(long id) {
        OrderEntity orderEntity = orderRepository.findById(id).orElseThrow(() -> new CustomException("NOT FOUND", "NOT_FOUND", 404));
        ProductResponse productResponse = restTemplate.getForObject("http://PRODUCT-SERVICE/products/" + orderEntity.getProductId(), ProductResponse.class);
        PaymentResponse paymentResponse = paymentService.getPayment(id).getBody();
        return OrderResponse.builder()
                .totalAmount(orderEntity.getAmount())
                .quantity(orderEntity.getQuantity())
                .orderDate(orderEntity.getOrderDate())
                .orderStatus(orderEntity.getOrderStatus())
                .productResponse(productResponse)
                .paymentResponse(paymentResponse)
                .build();
    }
}
