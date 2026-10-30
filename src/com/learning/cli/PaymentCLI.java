
package com.learning.cli;

import java.util.Scanner;

import com.learning.models.Enrollment;
import com.learning.models.Payment;
import com.learning.enums.PaymentMethod;
import com.learning.service.EnrollmentService;
import com.learning.service.PaymentService;

public class PaymentCLI {

    private Scanner scanner;
    private PaymentService paymentService;
    private EnrollmentService enrollmentService;

    public PaymentCLI(
            PaymentService paymentService,
            EnrollmentService enrollmentService) {

        this.scanner = Input.SCANNER;
        this.paymentService = paymentService;
        this.enrollmentService = enrollmentService;
    }

    public void showMenu() {
        int choice;

        do {
            System.out.println("\n===== PAYMENT MANAGEMENT =====");
            System.out.println("1. Make Payment");
            System.out.println("2. View All Payments");
            System.out.println("3. Request Refund");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    makePayment();
                    break;

                case 2:
                    viewAllPayments();
                    break;

                case 3:
                    requestRefund();
                    break;

                case 0:
                    System.out.println("Returning to main menu...");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }

        } while (choice != 0);
    }

    private void makePayment() {
        System.out.print("Enter Enrollment ID: ");
        int enrollmentId = scanner.nextInt();
        scanner.nextLine();

        Enrollment enrollment =
                enrollmentService.findEnrollmentById(enrollmentId);

        if (enrollment == null) {
            System.out.println("Enrollment not found.");
            return;
        }

        System.out.println("Payment methods: UPI, CARD, NET_BANKING");
        System.out.print("Enter payment method: ");

        PaymentMethod method;

        try {
            method = PaymentMethod.valueOf(
                    scanner.nextLine().trim().toUpperCase()
            );
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid payment method.");
            return;
        }

        System.out.print("Enter Payment ID: ");
        int paymentId = scanner.nextInt();
        scanner.nextLine();

        Payment payment = paymentService.createPayment(
                paymentId, enrollment, method
        );

        if (payment == null) {
            return;
        }

        System.out.println("Amount: " + payment.getAmount());
        System.out.print("Simulate payment success? (yes/no): ");

        String answer = scanner.nextLine().trim();

        boolean successful = answer.equalsIgnoreCase("yes");

        paymentService.processPayment(payment, successful);
    }

    private void viewAllPayments() {
        if (paymentService.getAllPayments().isEmpty()) {
            System.out.println("No payments available.");
            return;
        }

        for (Payment payment : paymentService.getAllPayments()) {
            System.out.println(
                    "Payment ID: " + payment.getPaymentId()
                    + " | Enrollment ID: "
                    + payment.getEnrollment().getEnrollmentId()
                    + " | Amount: " + payment.getAmount()
                    + " | Method: " + payment.getMethod()
                    + " | Status: " + payment.getStatus()
            );
        }
    }

    private void requestRefund() {
        System.out.print("Enter Payment ID: ");
        int paymentId = scanner.nextInt();
        scanner.nextLine();

        Payment selectedPayment = null;

        for (Payment payment : paymentService.getAllPayments()) {
            if (payment.getPaymentId() == paymentId) {
                selectedPayment = payment;
                break;
            }
        }

        if (selectedPayment == null) {
            System.out.println("Payment not found.");
            return;
        }

        paymentService.refundPayment(selectedPayment);
    }
}
