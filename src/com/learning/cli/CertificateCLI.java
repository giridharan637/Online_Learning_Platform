
package com.learning.cli;

import java.util.Scanner;

import com.learning.models.Enrollment;
import com.learning.models.Certificate;
import com.learning.service.CertificateService;
import com.learning.service.EnrollmentService;

public class CertificateCLI {

    private Scanner scanner;
    private CertificateService certificateService;
    private EnrollmentService enrollmentService;

    public CertificateCLI(
            CertificateService certificateService,
            EnrollmentService enrollmentService) {

        this.scanner = Input.SCANNER;
        this.certificateService = certificateService;
        this.enrollmentService = enrollmentService;
    }

    public void showMenu() {
        int choice;

        do {
            System.out.println("\n===== CERTIFICATE MANAGEMENT =====");
            System.out.println("1. Request Certificate");
            System.out.println("2. View All Certificates");
            System.out.println("3. Search Certificate by ID");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    requestCertificate();
                    break;

                case 2:
                    viewAllCertificates();
                    break;

                case 3:
                    searchCertificate();
                    break;

                case 0:
                    System.out.println("Returning to main menu...");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }

        } while (choice != 0);
    }

    private void requestCertificate() {
        System.out.print("Enter Enrollment ID: ");
        int enrollmentId = scanner.nextInt();
        scanner.nextLine();

        Enrollment enrollment =
                enrollmentService.findEnrollmentById(enrollmentId);

        if (enrollment == null) {
            System.out.println("Enrollment not found.");
            return;
        }

        certificateService.issueCertificate(enrollment);
    }

    private void viewAllCertificates() {
        if (certificateService.getAllCertificates().isEmpty()) {
            System.out.println("No certificates issued yet.");
            return;
        }

        for (Certificate certificate :
                certificateService.getAllCertificates()) {

            System.out.println(
                    "Certificate ID: " + certificate.getCertificateId()
                    + " | Student: "
                    + certificate.getEnrollment().getStudent().getName()
                    + " | Course: "
                    + certificate.getEnrollment().getCourse().getTitle()
                    + " | Unique Code: " + certificate.getUniqueCode()
                    + " | Issued At: " + certificate.getIssuedAt()
            );
        }
    }

    private void searchCertificate() {
        System.out.print("Enter Certificate ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Certificate certificate =
                certificateService.findCertificateById(id);

        if (certificate == null) {
            System.out.println("Certificate not found.");
        } else {
            System.out.println("Certificate ID: "
                    + certificate.getCertificateId());
            System.out.println("Student: "
                    + certificate.getEnrollment().getStudent().getName());
            System.out.println("Course: "
                    + certificate.getEnrollment().getCourse().getTitle());
            System.out.println("Unique Code: "
                    + certificate.getUniqueCode());
            System.out.println("Issued At: "
                    + certificate.getIssuedAt());
        }
    }
}
