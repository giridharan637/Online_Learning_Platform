
package com.learning.cli;

import java.util.Scanner;

import com.learning.models.Enrollment;
import com.learning.models.Review;
import com.learning.models.Student;
import com.learning.service.EnrollmentService;
import com.learning.service.ReviewService;
import com.learning.service.UserService;

public class ReviewCLI {

    private Scanner scanner;
    private ReviewService reviewService;
    private EnrollmentService enrollmentService;
    private UserService userService;

    public ReviewCLI(
            ReviewService reviewService,
            EnrollmentService enrollmentService,
            UserService userService) {

        this.scanner = Input.SCANNER;
        this.reviewService = reviewService;
        this.enrollmentService = enrollmentService;
        this.userService = userService;
    }

    public void showMenu() {
        int choice;

        do {
            System.out.println("\n===== REVIEWS =====");
            System.out.println("1. Add Review");
            System.out.println("2. Edit Review");
            System.out.println("3. View All Reviews");
            System.out.println("4. View Course Rating");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addReview();
                    break;

                case 2:
                    editReview();
                    break;

                case 3:
                    viewAllReviews();
                    break;

                case 4:
                    viewCourseRating();
                    break;

                case 0:
                    System.out.println("Returning to main menu...");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }

        } while (choice != 0);
    }

    private void addReview() {
        System.out.print("Enter Enrollment ID: ");
        int enrollmentId = scanner.nextInt();
        scanner.nextLine();

        Enrollment enrollment =
                enrollmentService.findEnrollmentById(enrollmentId);

        if (enrollment == null) {
            System.out.println("Enrollment not found.");
            return;
        }

        System.out.print("Enter Review ID: ");
        int reviewId = scanner.nextInt();

        System.out.print("Enter Rating (1-5): ");
        int rating = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter Comment: ");
        String comment = scanner.nextLine();

        reviewService.createReview(
                reviewId, enrollment, rating, comment
        );
    }

    private void editReview() {
        System.out.print("Enter Review ID: ");
        int reviewId = scanner.nextInt();

        System.out.print("Enter your Student ID: ");
        int studentId = scanner.nextInt();
        scanner.nextLine();

        if (!(userService.findUserById(studentId) instanceof Student)) {
            System.out.println("Student not found.");
            return;
        }

        Student student =
                (Student) userService.findUserById(studentId);

        System.out.print("Enter new Rating (1-5): ");
        int rating = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter new Comment: ");
        String comment = scanner.nextLine();

        reviewService.updateReview(
                reviewId, student, rating, comment
        );
    }

    private void viewAllReviews() {
        if (reviewService.getAllReviews().isEmpty()) {
            System.out.println("No reviews available.");
            return;
        }

        for (Review review : reviewService.getAllReviews()) {
            System.out.println(
                    "Review ID: " + review.getReviewId()
                    + " | Student: " + review.getStudent().getName()
                    + " | Course: " + review.getCourse().getTitle()
                    + " | Rating: " + review.getRating()
                    + " | Comment: " + review.getComment()
            );
        }
    }

    private void viewCourseRating() {
        System.out.print("Enter Enrollment ID: ");
        int enrollmentId = scanner.nextInt();
        scanner.nextLine();

        Enrollment enrollment =
                enrollmentService.findEnrollmentById(enrollmentId);

        if (enrollment == null) {
            System.out.println("Enrollment not found.");
            return;
        }

        double average = reviewService.getAverageRating(
                enrollment.getCourse()
        );

        System.out.printf("Average Course Rating: %.2f / 5%n", average);

        if (reviewService.isCourseFlagged(enrollment.getCourse())) {
            System.out.println(
                    "Warning: This course has a low average rating."
            );
        }
    }
}
