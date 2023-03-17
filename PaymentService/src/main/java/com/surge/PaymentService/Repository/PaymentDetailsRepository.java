package com.surge.PaymentService.Repository;

import com.surge.PaymentService.Entity.PaymentDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentDetailsRepository extends JpaRepository<PaymentDetailsEntity, String> {
    PaymentDetailsEntity findByOrderId(long orderId);
}
