
package com.learning.repository;

import com.learning.models.Certificate;
import java.util.ArrayList;

public class CertificateRepository {

    private ArrayList<Certificate> certificates;

    public CertificateRepository() {
        certificates = new ArrayList<>();
    }

    public void addCertificate(Certificate certificate) {
        if (certificate != null) {
            certificates.add(certificate);
        }
    }

    public Certificate findById(int certificateId) {
        for (Certificate certificate : certificates) {
            if (certificate.getCertificateId() == certificateId) {
                return certificate;
            }
        }
        return null;
    }

    public ArrayList<Certificate> findAll() {
        return new ArrayList<>(certificates);
    }
}
