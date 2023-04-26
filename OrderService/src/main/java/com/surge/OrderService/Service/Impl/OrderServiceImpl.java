package com.surge.OrderService.Service.Impl;

import com.netflix.discovery.converters.Auto;
import com.surge.OrderService.Dto.*;
import com.surge.OrderService.Entity.OrderEntity;
import com.surge.OrderService.Enums.PaymentMode;
import com.surge.OrderService.Exception.CustomException;
import com.surge.OrderService.Exception.ServiceUnavailable;
import com.surge.OrderService.External.Client.PaymentService;
import com.surge.OrderService.External.Client.ProductService;
import com.surge.OrderService.Repository.OrderRepository;
import com.surge.OrderService.Service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.UUID;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Qualifier("com.surge.OrderService.External.Client.ProductService")
    @Autowired
    private ProductService productService;
    @Qualifier("com.surge.OrderService.External.Client.PaymentService")
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private RestTemplate restTemplate;

    @Override
        public BaseResponse placeOrder(OrderRequest orderRequest) {
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

        OrderResponse response = OrderResponse.builder()
                .quantity(orderRequest.getQuantity())
                .productId(orderRequest.getProductId())
                .paymentMode(PaymentMode.DEBIT_CARD)
                .totalAmount(orderRequest.getTotalAmount() * orderRequest.getQuantity())
                .build();
        return BaseResponse.builder()
                .status("SUCCESSFUL")
                .message("ORDER_PLACED_SUCCESSFULLY")
                .data(response)
                .build();
    }

    @Override
    public BaseResponse getOrder(long id) {
        OrderEntity orderEntity = orderRepository.findById(id).orElseThrow(() -> new CustomException("NOT FOUND", "NOT_FOUND", 404));
//        BaseResponse<ProductResponse> productResponse =
//                restTemplate.exchange("http://PRODUCT-SERVICE/products/" + orderEntity.getProductId(), HttpMethod.GET, null, BaseResponse.class).getBody();
        BaseResponse<PaymentResponse> paymentResponse = paymentService.getPayment(id);
        if(paymentResponse.getStatus().equals("UNAVAILABLE")) {
            throw new ServiceUnavailable("PAYMENT_SERVICE_UNAVAILABLE", "UNAVAILABLE11", 503);
        }
        BaseResponse<ProductResponse> productResponseBaseResponse = productService.getProduct(orderEntity.getProductId());
        PaymentResponse paymentResponse2 = paymentResponse.getData();
        ProductResponse productResponse = productResponseBaseResponse.getData();

        PaymentResponse paymentResponse1 = PaymentResponse.builder()
                .paymentId(paymentResponse2.getPaymentId())
                .referenceNumber(paymentResponse2.getReferenceNumber())
                .amount(paymentResponse2.getAmount())
                .paymentDate(paymentResponse2.getPaymentDate())
                .paymentStatus(paymentResponse2.getPaymentStatus())
                .build();

        ProductResponse productResponse1 = ProductResponse.builder()
                .productName(productResponse.getProductName())
                .price(productResponse.getPrice())
                .quantity(productResponse.getQuantity())
                .productId(productResponse.getProductId())
                .build();
        OrderResponse response = OrderResponse.builder()
                .totalAmount(orderEntity.getAmount())
                .quantity(orderEntity.getQuantity())
                .orderDate(orderEntity.getOrderDate())
                .orderStatus(orderEntity.getOrderStatus())
                .paymentMode(PaymentMode.DEBIT_CARD)
                .productResponse(productResponse1)
                .paymentResponse(paymentResponse1)
                .productId(productResponse.getProductId())
//                .id(id)
                .build();
        return BaseResponse.builder()
                .status("SUCCESSFUL")
                .message("ORDER_GOTTEN_SUCCESSFULLY")
                .data(response)
                .build();
    }
}
