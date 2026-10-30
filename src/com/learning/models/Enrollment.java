
package com.learning.models;

import com.learning.enums.EnrollmentStatus;

import java.time.LocalDateTime;

public class Enrollment {

    private int enrollmentId;
    private Student student;
    private Course course;
    private EnrollmentStatus status;
    private LocalDateTime enrolledAt;

    public Enrollment(int enrollmentId, Student student, Course course,
                      EnrollmentStatus status) {
        this.enrollmentId = enrollmentId;
        this.student = student;
        this.course = course;
        this.status = status;
        this.enrolledAt = LocalDateTime.now();
    }

    public int getEnrollmentId() {
        return enrollmentId;
    }

    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }

    public EnrollmentStatus getStatus() {
        return status;
    }

    public LocalDateTime getEnrolledAt() {
        return enrolledAt;
    }

    public void setStatus(EnrollmentStatus status) {
        this.status = status;
    }
}
