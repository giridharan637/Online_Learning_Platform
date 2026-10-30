
package com.learning.service;

import com.learning.models.Course;
import com.learning.models.Enrollment;
import com.learning.models.LessonProgress;
import com.learning.models.Payment;
import com.learning.enums.EnrollmentStatus;
import com.learning.enums.PaymentMethod;
import com.learning.enums.PaymentStatus;
import com.learning.repository.EnrollmentRepository;
import com.learning.repository.PaymentRepository;
import com.learning.repository.ProgressRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class PaymentService {

    private PaymentRepository paymentRepository;
    private EnrollmentRepository enrollmentRepository;
    private ProgressRepository progressRepository;

    public PaymentService(
            PaymentRepository paymentRepository,
            EnrollmentRepository enrollmentRepository,
            ProgressRepository progressRepository) {

        this.paymentRepository = paymentRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.progressRepository = progressRepository;
    }

    public Payment createPayment(
            int paymentId, Enrollment enrollment, PaymentMethod method) {

        if (enrollment == null || method == null) {
            System.out.println("Enrollment and method are required.");
            return null;
        }

        if (enrollment.getStatus() != EnrollmentStatus.PENDING_PAYMENT) {
            System.out.println("Enrollment is not awaiting payment.");
            return null;
        }

        Payment payment = new Payment(
                paymentId,
                enrollment,
                enrollment.getCourse().getPrice(),
                method
        );

        paymentRepository.addPayment(payment);
        return payment;
    }

    public boolean processPayment(Payment payment, boolean successful) {
        if (payment == null) {
            return false;
        }

        Enrollment enrollment = payment.getEnrollment();

        if (enrollment.getStatus() != EnrollmentStatus.PENDING_PAYMENT) {
            System.out.println("Enrollment is no longer awaiting payment.");
            return false;
        }

        if (payment.getStatus() != PaymentStatus.FAILED) {
            System.out.println("This payment cannot be processed again.");
            return false;
        }

        if (LocalDateTime.now().isAfter(
                enrollment.getEnrolledAt().plusHours(24))) {
            enrollment.setStatus(EnrollmentStatus.EXPIRED);
            System.out.println("Enrollment payment window has expired.");
            return false;
        }

        if (successful) {
            payment.markPaid();
            enrollment.setStatus(EnrollmentStatus.ACTIVE);
            System.out.println("Payment successful.");
            return true;
        }

        payment.markFailed();
        System.out.println("Payment failed. You may retry within 24 hours.");
        return false;
    }

    public boolean refundPayment(Payment payment) {
        if (payment == null || payment.getStatus() != PaymentStatus.PAID) {
            System.out.println("No successful payment to refund.");
            return false;
        }

        LocalDateTime paidAt = payment.getPaymentDate();

        if (paidAt == null
                || LocalDateTime.now().isAfter(paidAt.plusDays(7))) {
            System.out.println("Refund period has expired.");
            return false;
        }

        Enrollment enrollment = payment.getEnrollment();
        double progress = calculateProgress(enrollment);

        if (progress >= 20.0) {
            System.out.println("Refund unavailable at 20% progress or above.");
            return false;
        }

        payment.markRefunded();
        enrollment.setStatus(EnrollmentStatus.CANCELLED);
        System.out.println("Full refund processed.");
        return true;
    }

    private double calculateProgress(Enrollment enrollment) {
        int totalLessons = 0;
        int completedLessons = 0;

        for (com.learning.models.Section section
                : enrollment.getCourse().getSections()) {
            for (com.learning.models.Lesson lesson : section.getLessons()) {
                totalLessons++;

                for (LessonProgress record : progressRepository.findAll()) {
                    if (record.getEnrollment().getEnrollmentId()
                                == enrollment.getEnrollmentId()
                            && record.getLesson().getLessonId()
                                == lesson.getLessonId()
                            && record.isCompleted()) {
                        completedLessons++;
                        break;
                    }
                }
            }
        }

        if (totalLessons == 0) {
            return 0.0;
        }

        return completedLessons * 100.0 / totalLessons;
    }

    public void withdrawCourse(Course course) {
        if (course == null) {
            return;
        }

        for (Enrollment enrollment : enrollmentRepository.findAll()) {
            if (enrollment.getCourse().getCourseId()
                    != course.getCourseId()) {
                continue;
            }

            for (Payment payment : paymentRepository.findAll()) {
                if (payment.getEnrollment().getEnrollmentId()
                            == enrollment.getEnrollmentId()
                        && payment.getStatus() == PaymentStatus.PAID) {
                    payment.markRefunded();
                }
            }

            enrollment.setStatus(EnrollmentStatus.CANCELLED);
        }

        System.out.println("Course enrollments cancelled.");
    }

    public BigDecimal getInstructorEarning(Payment payment) {
        if (payment == null || payment.getStatus() != PaymentStatus.PAID
                || payment.getPaymentDate() == null) {
            return BigDecimal.ZERO;
        }

        if (LocalDateTime.now().isBefore(
                payment.getPaymentDate().plusDays(7))) {
            return BigDecimal.ZERO;
        }

        return payment.getAmount()
                .multiply(new BigDecimal("0.70"));
    }

    public BigDecimal getPlatformShare(Payment payment) {
        if (payment == null || payment.getStatus() != PaymentStatus.PAID
                || payment.getPaymentDate() == null
                || LocalDateTime.now().isBefore(
                    payment.getPaymentDate().plusDays(7))) {
            return BigDecimal.ZERO;
        }

        return payment.getAmount()
                .multiply(new BigDecimal("0.30"));
    }

    public ArrayList<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
}
