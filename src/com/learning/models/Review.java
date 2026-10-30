
package com.learning.models;

import java.time.LocalDateTime;

public class Review {

    private int reviewId;
    private Student student;
    private Course course;
    private int rating;
    private String comment;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Review(int reviewId, Student student, Course course,
                  int rating, String comment) {
        this.reviewId = reviewId;
        this.student = student;
        this.course = course;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = createdAt;
    }

    public int getReviewId() {
        return reviewId;
    }

    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void updateReview(int rating, String comment) {
        if (rating >= 1 && rating <= 5) {
            this.rating = rating;
            this.comment = comment;
            this.updatedAt = LocalDateTime.now();
        }
    }
}
