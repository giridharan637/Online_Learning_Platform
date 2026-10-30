
package com.learning.repository;

import com.learning.models.Payment;
import java.util.ArrayList;

public class PaymentRepository {

    private ArrayList<Payment> payments;

    public PaymentRepository() {
        payments = new ArrayList<>();
    }

    public void addPayment(Payment payment) {
        if (payment != null) {
            payments.add(payment);
        }
    }

    public Payment findById(int paymentId) {
        for (Payment payment : payments) {
            if (payment.getPaymentId() == paymentId) {
                return payment;
            }
        }
        return null;
    }

    public ArrayList<Payment> findAll() {
        return new ArrayList<>(payments);
    }
}
