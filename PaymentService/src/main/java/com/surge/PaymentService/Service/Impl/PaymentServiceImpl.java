package com.surge.PaymentService.Service.Impl;

import com.surge.PaymentService.Entity.PaymentDetailsEntity;
import com.surge.PaymentService.Repository.PaymentDetailsRepository;
import com.surge.PaymentService.Service.PaymentService;
import com.surge.PaymentService.dto.BaseResponse;
import com.surge.PaymentService.dto.PaymentRequest;
import com.surge.PaymentService.dto.PaymentResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentDetailsRepository paymentDetailsRepository;
    @Override
    public BaseResponse makePayment(PaymentRequest paymentRequest) {
        log.info("In the make Payment Service");
        PaymentDetailsEntity paymentDetailsEntity = PaymentDetailsEntity.builder()
                .paymentMode(paymentRequest.getPaymentMode())
                .paymentStatus("SUCCESS")
                .paymentDate(Instant.now())
                .amount(paymentRequest.getAmount())
                .orderId(paymentRequest.getOrderId())
                .referenceNumber(paymentRequest.getReferenceNumber())
                .build();
        PaymentDetailsEntity paymentDetails = paymentDetailsRepository.save(paymentDetailsEntity);

        if(paymentDetails.getPaymentId() == null) {
            throw new RuntimeException("Saving payment issue");
        }

        PaymentResponse paymentResponse = PaymentResponse.builder()
                .paymentId(paymentDetails.getPaymentId())
                .amount(paymentDetails.getAmount())
                .paymentDate(paymentDetails.getPaymentDate())
                .paymentStatus(paymentDetails.getPaymentStatus())
                .referenceNumber(paymentDetails.getReferenceNumber())
                .build();

        return BaseResponse.builder()
                .status("SUCCESSFUL")
                .message("PAY_MADE_SUCCESSFULLY")
                .data(paymentResponse)
                .build();
    }

    @Override
    public BaseResponse getPayment(long orderId) {
        PaymentDetailsEntity paymentDetailsEntity = paymentDetailsRepository.findByOrderId(orderId);
        if(paymentDetailsRepository == null) {
            throw new RuntimeException("Order does not exist");
        }
        PaymentResponse response = PaymentResponse.builder()
                .referenceNumber(paymentDetailsEntity.getReferenceNumber())
                .paymentStatus(paymentDetailsEntity.getPaymentStatus())
                .paymentDate(paymentDetailsEntity.getPaymentDate())
                .amount(paymentDetailsEntity.getAmount())
                .paymentId(paymentDetailsEntity.getPaymentId())
                .build();
        return BaseResponse.builder()
                .status("SUCCESSFUL")
                .message("PAYMENT_GOTTEN_SUCCESSFULLY")
                .data(response)
                .build();
    }
}
