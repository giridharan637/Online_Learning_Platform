
package com.learning.repository;

import com.learning.models.Enrollment;
import java.util.ArrayList;

public class EnrollmentRepository {

    private ArrayList<Enrollment> enrollments;

    public EnrollmentRepository() {
        enrollments = new ArrayList<>();
    }

    public void addEnrollment(Enrollment enrollment) {
        if (enrollment != null) {
            enrollments.add(enrollment);
        }
    }

    public Enrollment findById(int enrollmentId) {
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getEnrollmentId() == enrollmentId) {
                return enrollment;
            }
        }
        return null;
    }

    public ArrayList<Enrollment> findAll() {
        return new ArrayList<>(enrollments);
    }
}
