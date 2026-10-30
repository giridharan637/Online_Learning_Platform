
package com.learning.cli;

import java.util.Scanner;

public class MainCLI {

    private Scanner scanner;

    private CourseCLI courseCLI;
    private EnrollmentCLI enrollmentCLI;
    private ProgressCLI progressCLI;
    private PaymentCLI paymentCLI;
    private ReviewCLI reviewCLI;
    private CertificateCLI certificateCLI;

    public MainCLI(
            CourseCLI courseCLI,
            EnrollmentCLI enrollmentCLI,
            ProgressCLI progressCLI,
            PaymentCLI paymentCLI,
            ReviewCLI reviewCLI,
            CertificateCLI certificateCLI) {

        this.scanner = Input.SCANNER;

        this.courseCLI = courseCLI;
        this.enrollmentCLI = enrollmentCLI;
        this.progressCLI = progressCLI;
        this.paymentCLI = paymentCLI;
        this.reviewCLI = reviewCLI;
        this.certificateCLI = certificateCLI;
    }

    public void showMenu() {
        int choice = -1;

        do {
            System.out.println("\n===== ONLINE LEARNING PLATFORM =====");
            System.out.println("1. Course Management");
            System.out.println("2. Enrollment Management");
            System.out.println("3. Learning Progress");
            System.out.println("4. Payment Management");
            System.out.println("5. Reviews");
            System.out.println("6. Certificates");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Please enter a valid number.");
                scanner.nextLine();
                continue;
            }

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    courseCLI.showMenu();
                    break;

                case 2:
                    enrollmentCLI.showMenu();
                    break;

                case 3:
                    progressCLI.showMenu();
                    break;

                case 4:
                    paymentCLI.showMenu();
                    break;

                case 5:
                    reviewCLI.showMenu();
                    break;

                case 6:
                    certificateCLI.showMenu();
                    break;

                case 0:
                    System.out.println(
                            "Thank you for using Online Learning Platform!"
                    );
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }

        } while (choice != 0);
    }
}
