package com.surge.OrderService.Service.Impl;

import com.surge.OrderService.Dto.BaseResponse;
import com.surge.OrderService.Dto.OrderResponse;
import com.surge.OrderService.Dto.PaymentResponse;
import com.surge.OrderService.Dto.ProductResponse;
import com.surge.OrderService.Entity.OrderEntity;
import com.surge.OrderService.Exception.CustomException;
import com.surge.OrderService.External.Client.PaymentService;
import com.surge.OrderService.External.Client.ProductService;
import com.surge.OrderService.Repository.OrderRepository;
import com.surge.OrderService.Service.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class OrderServiceImplTest {
    @Mock
    private OrderRepository orderRepository;
    @Qualifier("com.surge.OrderService.External.Client.ProductService")
    @Mock
    private ProductService productService;
    @Qualifier("com.surge.OrderService.External.Client.PaymentService")
    @Mock
    private PaymentService paymentService;
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    OrderService orderService = new OrderServiceImpl();

    @Test
    @DisplayName("Get Order - Success Scenario")
    void test_When_Order_Success () {
//        Mocking
        OrderEntity order = getMockOrder();

        when(orderRepository.findById(anyLong()))
                .thenReturn(Optional.of(order));

        when(paymentService.getPayment(anyLong()))
                .thenReturn(getMockPaymentResponse());

        when(productService.getProduct(anyString()))
                .thenReturn(getMockProductResponse());
//        Actual Method call
        BaseResponse<OrderResponse> orderResponse = orderService.getOrder(1);

//        Verification (How many times call made)

        verify(orderRepository, times(1)).findById(anyLong());
        verify(paymentService, times(1)).getPayment(anyLong());
        verify(productService, times(1)).getProduct(anyString());
//        Assert (Expected Output)
        assertNotNull(orderResponse);
        assertEquals(order.getId(), orderResponse.getData().getId());
    }

    @Test
    @DisplayName("Get Orders - Fail Scenario")
    void test_When_Order_Not_Found() {
        when(orderRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(null));

        CustomException exception = assertThrows(CustomException.class, () -> orderService.getOrder(1));
    }

    private BaseResponse<ProductResponse> getMockProductResponse() {
        ProductResponse productResponse = ProductResponse.builder()
                .productId("559d77e8-8bc0-4cc0-9406-3607c80343e1")
                .productName("Macbook")
                .price(1000)
                .quantity(10)
                .build();
        BaseResponse baseResponse = BaseResponse.builder()
                .status("SUCCESSFUL")
                .message("SUCCESSFUL")
                .data(productResponse)
                .build();

        return baseResponse;
    }

    private BaseResponse<PaymentResponse> getMockPaymentResponse() {
        PaymentResponse paymentResponse = PaymentResponse.builder()
                .paymentId("74794d3b-69ec-4b03-82d3-204430c9fdc1")
                .paymentDate(Instant.now())
                .paymentStatus("CREATED")
                .amount(1000)
                .referenceNumber("3d8704c9-3c49-471c-8dee-3278d0131685")
                .build();
        BaseResponse baseResponse = BaseResponse.builder()
                .status("SUCCESSFUL")
                .message("SUCCESSFUL")
                .data(paymentResponse)
                .build();
        return baseResponse;
    }

    private OrderEntity getMockOrder() {
        return OrderEntity.builder()
                .orderStatus("PLACED")
                .orderDate(Instant.now())
                .id(1)
                .amount(100)
                .quantity(20)
                .productId("559d77e8-8bc0-4cc0-9406-3607c80343e1")
                .build();
    }
}