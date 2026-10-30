
package com.learning.service;

import com.learning.models.Student;
import com.learning.models.Course;
import com.learning.models.Enrollment;
import com.learning.models.User;
import com.learning.enums.EnrollmentStatus;
import com.learning.repository.EnrollmentRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class EnrollmentService {

    private EnrollmentRepository enrollmentRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    public Enrollment enroll(int enrollmentId, User user, Course course) {

        expirePendingEnrollments();
        if (enrollmentRepository.findById(enrollmentId) != null) {
            System.out.println("Enrollment ID already exists."); return null;
        }

        if (!(user instanceof Student)) {
            System.out.println("Only students can enroll.");
            return null;
        }

        if (course == null || !course.isPublished()) {
            System.out.println("Course is not available for enrollment.");
            return null;
        }

        Student student = (Student) user;

        if (course.getInstructor().getId() == student.getId()) {
            System.out.println("Instructor cannot enroll in own course.");
            return null;
        }

        int activeCount = 0;

        for (Enrollment enrollment : enrollmentRepository.findAll()) {
            if (enrollment.getStudent().getId() == student.getId()) {

                if (enrollment.getCourse().getCourseId()
                        == course.getCourseId()) {
                    System.out.println("Already enrolled in this course.");
                    return null;
                }

                if (enrollment.getStatus() == EnrollmentStatus.ACTIVE) {
                    activeCount++;
                }
            }
        }

        if (activeCount >= 5) {
            System.out.println("Maximum 5 active courses allowed.");
            return null;
        }

        EnrollmentStatus status;

        if (course.getPrice().compareTo(BigDecimal.ZERO) == 0) {
            status = EnrollmentStatus.ACTIVE;
        } else {
            status = EnrollmentStatus.PENDING_PAYMENT;
        }

        Enrollment enrollment = new Enrollment(
                enrollmentId, student, course, status
        );

        enrollmentRepository.addEnrollment(enrollment);

        System.out.println("Enrollment status: " + status);
        return enrollment;
    }

    public void expirePendingEnrollments() {
        LocalDateTime now = LocalDateTime.now();

        for (Enrollment enrollment : enrollmentRepository.findAll()) {
            if (enrollment.getStatus() == EnrollmentStatus.PENDING_PAYMENT
                    && !now.isBefore(
                        enrollment.getEnrolledAt().plusHours(24))) {

                enrollment.setStatus(EnrollmentStatus.EXPIRED);
            }
        }
    }

    public Enrollment findEnrollmentById(int enrollmentId) {
        expirePendingEnrollments();
        return enrollmentRepository.findById(enrollmentId);
    }

    public ArrayList<Enrollment> getAllEnrollments() {
        expirePendingEnrollments();
        return enrollmentRepository.findAll();
    }
}
