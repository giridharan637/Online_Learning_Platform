
package com.learning.service;

import com.learning.models.Course;
import com.learning.models.Enrollment;
import com.learning.models.Lesson;
import com.learning.models.LessonProgress;
import com.learning.models.Review;
import com.learning.models.Section;
import com.learning.models.Student;
import com.learning.repository.EnrollmentRepository;
import com.learning.repository.ProgressRepository;
import com.learning.repository.ReviewRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ReviewService {

    private ReviewRepository reviewRepository;
    private EnrollmentRepository enrollmentRepository;
    private ProgressRepository progressRepository;

    public ReviewService(
            ReviewRepository reviewRepository,
            EnrollmentRepository enrollmentRepository,
            ProgressRepository progressRepository) {

        this.reviewRepository = reviewRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.progressRepository = progressRepository;
    }

    public boolean createReview(
            int reviewId, Enrollment enrollment,
            int rating, String comment) {

        if (enrollment == null || rating < 1 || rating > 5) {
            System.out.println("Valid enrollment and rating are required.");
            return false;
        }

        Student student = enrollment.getStudent();
        Course course = enrollment.getCourse();

        if (!hasEligibleEnrollment(student, course)) {
            System.out.println("An eligible enrollment is required.");
            return false;
        }

        if (calculateProgress(enrollment) < 25.0) {
            System.out.println("Complete at least 25% of the course first.");
            return false;
        }

        for (Review review : reviewRepository.findAll()) {
            if (review.getStudent().getId() == student.getId()
                    && review.getCourse().getCourseId()
                        == course.getCourseId()) {
                System.out.println("You have already reviewed this course.");
                return false;
            }

            if (review.getReviewId() == reviewId) {
                System.out.println("Review ID already exists.");
                return false;
            }
        }

        Review review = new Review(
                reviewId, student, course, rating, comment
        );

        reviewRepository.addReview(review);
        System.out.println("Review added successfully.");
        return true;
    }

    private boolean hasEligibleEnrollment(Student student, Course course) {
        for (Enrollment enrollment : enrollmentRepository.findAll()) {
            if (enrollment.getStudent().getId() == student.getId()
                    && enrollment.getCourse().getCourseId()
                        == course.getCourseId()) {
                return true;
            }
        }

        return false;
    }

    public boolean updateReview(
            int reviewId, Student student, int rating, String comment) {

        if (student == null || rating < 1 || rating > 5) {
            return false;
        }

        for (Review review : reviewRepository.findAll()) {
            if (review.getReviewId() != reviewId) {
                continue;
            }

            if (review.getStudent().getId() != student.getId()) {
                System.out.println("You cannot edit another student's review.");
                return false;
            }

            if (LocalDateTime.now().isAfter(
                    review.getCreatedAt().plusDays(30))) {
                System.out.println("Review editing period has expired.");
                return false;
            }

            review.updateReview(rating, comment);
            System.out.println("Review updated successfully.");
            return true;
        }

        System.out.println("Review not found.");
        return false;
    }

    private double calculateProgress(Enrollment enrollment) {
        int totalLessons = 0;
        int completedLessons = 0;

        for (Section section : enrollment.getCourse().getSections()) {
            for (Lesson lesson : section.getLessons()) {
                totalLessons++;

                for (LessonProgress progress : progressRepository.findAll()) {
                    if (progress.getEnrollment().getEnrollmentId()
                                == enrollment.getEnrollmentId()
                            && progress.getLesson().getLessonId()
                                == lesson.getLessonId()
                            && progress.isCompleted()) {
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

    public double getAverageRating(Course course) {
        int totalRatings = 0;
        int count = 0;

        for (Review review : reviewRepository.findAll()) {
            if (review.getCourse().getCourseId()
                    == course.getCourseId()) {
                totalRatings += review.getRating();
                count++;
            }
        }

        if (count == 0) {
            return 0.0;
        }

        return (double) totalRatings / count;
    }

    public boolean isCourseFlagged(Course course) {
        int count = 0;

        for (Review review : reviewRepository.findAll()) {
            if (review.getCourse().getCourseId()
                    == course.getCourseId()) {
                count++;
            }
        }

        return count >= 20 && getAverageRating(course) < 2.5;
    }

    public ArrayList<Review> getAllReviews() {
        return reviewRepository.findAll();
    }
}
