
package com.learning.cli;

import java.util.Scanner;

import com.learning.models.User;
import com.learning.models.Course;
import com.learning.models.Enrollment;
import com.learning.service.EnrollmentService;
import com.learning.service.UserService;
import com.learning.service.CourseService;

public class EnrollmentCLI {

    private Scanner scanner;
    private EnrollmentService enrollmentService;
    private UserService userService;
    private CourseService courseService;

    public EnrollmentCLI(
            EnrollmentService enrollmentService,
            UserService userService,
            CourseService courseService) {

        this.scanner = Input.SCANNER;
        this.enrollmentService = enrollmentService;
        this.userService = userService;
        this.courseService = courseService;
    }

    public void showMenu() {
        int choice;

        do {
            System.out.println("\n===== ENROLLMENT MANAGEMENT =====");
            System.out.println("1. Enroll in Course");
            System.out.println("2. View All Enrollments");
            System.out.println("3. Search Enrollment by ID");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    enrollStudent();
                    break;

                case 2:
                    viewAllEnrollments();
                    break;

                case 3:
                    searchEnrollment();
                    break;

                case 0:
                    System.out.println("Returning to main menu...");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }

        } while (choice != 0);
    }

    private void enrollStudent() {
        System.out.print("Enter Student ID: ");
        int studentId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter Course ID: ");
        int courseId = scanner.nextInt();
        scanner.nextLine();

        User student = userService.findUserById(studentId);
        Course course = courseService.findCourseById(courseId);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        System.out.print("Enter Enrollment ID: ");
        int enrollmentId = scanner.nextInt();
        scanner.nextLine();

        Enrollment enrollment = enrollmentService.enroll(
                enrollmentId, student, course
        );

        if (enrollment != null) {
            System.out.println("Enrollment ID: " + enrollment.getEnrollmentId());
            System.out.println("Status: " + enrollment.getStatus());
        }
    }

    private void viewAllEnrollments() {
        if (enrollmentService.getAllEnrollments().isEmpty()) {
            System.out.println("No enrollments available.");
            return;
        }

        for (Enrollment enrollment : enrollmentService.getAllEnrollments()) {
            System.out.println(
                    "Enrollment ID: " + enrollment.getEnrollmentId()
                    + " | Student: " + enrollment.getStudent().getName()
                    + " | Course: " + enrollment.getCourse().getTitle()
                    + " | Status: " + enrollment.getStatus()
            );
        }
    }

    private void searchEnrollment() {
        System.out.print("Enter Enrollment ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Enrollment enrollment = enrollmentService.findEnrollmentById(id);

        if (enrollment == null) {
            System.out.println("Enrollment not found.");
        } else {
            System.out.println("Enrollment ID: " + enrollment.getEnrollmentId());
            System.out.println("Student: " + enrollment.getStudent().getName());
            System.out.println("Course: " + enrollment.getCourse().getTitle());
            System.out.println("Status: " + enrollment.getStatus());
            System.out.println("Enrolled At: " + enrollment.getEnrolledAt());
        }
    }
}
