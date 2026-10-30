
package com.learning.service;

import com.learning.models.Certificate;
import com.learning.models.Enrollment;
import com.learning.enums.EnrollmentStatus;
import com.learning.repository.CertificateRepository;

import java.util.UUID;
import java.util.ArrayList;

public class CertificateService {

    private CertificateRepository certificateRepository;

    public CertificateService(
            CertificateRepository certificateRepository) {
        this.certificateRepository = certificateRepository;
    }

    public Certificate issueCertificate(Enrollment enrollment) {

        if (enrollment == null) {
            System.out.println("Enrollment not found.");
            return null;
        }

        if (enrollment.getStatus() != EnrollmentStatus.COMPLETED) {
            System.out.println("Complete the course before requesting a certificate.");
            return null;
        }

        for (Certificate certificate : certificateRepository.findAll()) {
            if (certificate.getEnrollment().getEnrollmentId()
                    == enrollment.getEnrollmentId()) {
                System.out.println("Certificate already issued.");
                return certificate;
            }
        }

        int nextId = 1;

        for (Certificate certificate : certificateRepository.findAll()) {
            if (certificate.getCertificateId() >= nextId) {
                nextId = certificate.getCertificateId() + 1;
            }
        }

        String uniqueCode = UUID.randomUUID().toString();

        Certificate certificate = new Certificate(
                nextId, enrollment, uniqueCode
        );

        certificateRepository.addCertificate(certificate);

        System.out.println("Certificate issued successfully.");
        System.out.println("Certificate code: " + uniqueCode);

        return certificate;
    }

    public Certificate findCertificateById(int certificateId) {
        for (Certificate certificate : certificateRepository.findAll()) {
            if (certificate.getCertificateId() == certificateId) {
                return certificate;
            }
        }

        return null;
    }

    public ArrayList<Certificate> getAllCertificates() {
        return certificateRepository.findAll();
    }
}
