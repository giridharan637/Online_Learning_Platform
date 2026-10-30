
package com.learning.cli;

import java.util.Scanner;

import com.learning.models.Enrollment;
import com.learning.models.Lesson;
import com.learning.models.Section;
import com.learning.models.VideoLesson;
import com.learning.models.ReadingLesson;
import com.learning.models.QuizLesson;
import com.learning.enums.EnrollmentStatus;
import com.learning.service.EnrollmentService;
import com.learning.service.ProgressService;

public class ProgressCLI {

    private Scanner scanner;
    private ProgressService progressService;
    private EnrollmentService enrollmentService;

    public ProgressCLI(
            ProgressService progressService,
            EnrollmentService enrollmentService) {

        this.scanner = Input.SCANNER;
        this.progressService = progressService;
        this.enrollmentService = enrollmentService;
    }

    public void showMenu() {
        int choice;

        do {
            System.out.println("\n===== LEARNING PROGRESS =====");
            System.out.println("1. View Course Lessons");
            System.out.println("2. Mark Lesson Completed");
            System.out.println("3. View Course Progress");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    viewLessons();
                    break;

                case 2:
                    markLessonCompleted();
                    break;

                case 3:
                    viewProgress();
                    break;

                case 0:
                    System.out.println("Returning to main menu...");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }

        } while (choice != 0);
    }

    private void viewLessons() {
        Enrollment enrollment = findEnrollment();

        if (enrollment == null) {
            return;
        }

        for (Section section : enrollment.getCourse().getSections()) {
            System.out.println("\nSection: " + section.getTitle());

            for (Lesson lesson : section.getLessons()) {
                System.out.println(
                        "Lesson ID: " + lesson.getLessonId()
                        + " | Title: " + lesson.getTitle()
                        + " | Type: " + lesson.getClass().getSimpleName()
                );
            }
        }
    }

    private void markLessonCompleted() {
        Enrollment enrollment = findEnrollment();

        if (enrollment == null) {
            return;
        }

        System.out.print("Enter Lesson ID: ");
        int lessonId = scanner.nextInt();
        scanner.nextLine();

        for (Section section : enrollment.getCourse().getSections()) {
            for (Lesson lesson : section.getLessons()) {
                if (lesson.getLessonId() == lessonId) {
                    if (enrollment.getStatus() != EnrollmentStatus.ACTIVE) {
                        System.out.println("Enrollment must be active."); return;
                    }
                    // Ensure earlier lessons are complete before recording actions.
                    if (lesson instanceof VideoLesson) {
                        System.out.print("Minutes watched: ");
                        double minutes = scanner.nextDouble(); scanner.nextLine();
                        ((VideoLesson) lesson).watch(minutes);
                    } else if (lesson instanceof ReadingLesson) {
                        System.out.print("Finished reading? (yes/no): ");
                        if (scanner.nextLine().trim().equalsIgnoreCase("yes"))
                            ((ReadingLesson) lesson).markAsRead();
                    } else if (lesson instanceof QuizLesson) {
                        System.out.print("Quiz score (0-100): ");
                        double score = scanner.nextDouble(); scanner.nextLine();
                        ((QuizLesson) lesson).submitQuiz(score);
                    }
                    progressService.markLessonCompleted(enrollment, lesson);
                    return;
                }
            }
        }

        System.out.println("Lesson not found in this course.");
    }

    private void viewProgress() {
        Enrollment enrollment = findEnrollment();

        if (enrollment == null) {
            return;
        }

        double percentage =
                progressService.getProgressPercentage(enrollment);

        System.out.printf("Course Progress: %.2f%%%n", percentage);
    }

    private Enrollment findEnrollment() {
        System.out.print("Enter Enrollment ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Enrollment enrollment =
                enrollmentService.findEnrollmentById(id);

        if (enrollment == null) {
            System.out.println("Enrollment not found.");
        }

        return enrollment;
    }
}
