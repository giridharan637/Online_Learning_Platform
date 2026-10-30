
package com.learning.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.learning.enums.PaymentMethod;
import com.learning.enums.PaymentStatus;

public class Payment {

    private int paymentId;
    private Enrollment enrollment;
    private BigDecimal amount;
    private PaymentMethod method;
    private PaymentStatus status;
    private LocalDateTime paymentDate;

    public Payment(int paymentId, Enrollment enrollment,
                   BigDecimal amount, PaymentMethod method) {
        this.paymentId = paymentId;
        this.enrollment = enrollment;
        this.amount = amount;
        this.method = method;
        this.status = PaymentStatus.FAILED;
        this.paymentDate = null;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public Enrollment getEnrollment() {
        return enrollment;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public PaymentMethod getMethod() {
        return method;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void markPaid() {
        status = PaymentStatus.PAID;
        paymentDate = LocalDateTime.now();
    }

    public void markFailed() {
        status = PaymentStatus.FAILED;
    }

    public void markRefunded() {
        status = PaymentStatus.REFUNDED;
    }
}
