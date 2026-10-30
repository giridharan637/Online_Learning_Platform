
package com.learning.models;

import java.time.LocalDateTime;

public class Certificate {

    private int certificateId;
    private Enrollment enrollment;
    private String uniqueCode;
    private LocalDateTime issuedAt;

    public Certificate(int certificateId, Enrollment enrollment,
                       String uniqueCode) {
        this.certificateId = certificateId;
        this.enrollment = enrollment;
        this.uniqueCode = uniqueCode;
        this.issuedAt = LocalDateTime.now();
    }

    public int getCertificateId() {
        return certificateId;
    }

    public Enrollment getEnrollment() {
        return enrollment;
    }

    public String getUniqueCode() {
        return uniqueCode;
    }

    public LocalDateTime getIssuedAt() {
        return issuedAt;
    }
}
