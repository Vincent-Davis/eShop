package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class PaymentRepository {
    private final List<Payment> paymentData = new ArrayList<>();

    public Payment save(Payment payment) {
        paymentData.add(payment);
        return payment;
    }

    public Payment findById(String paymentId) {
        // Menggunakan stream untuk mencari Payment berdasarkan ID
        return paymentData.stream()
                .filter(payment -> payment.getId().equals(paymentId))
                .findFirst()
                .orElse(null);
    }

    public List<Payment> findAll() {
        // Mengembalikan list yang tidak dapat diubah (agar tidak terjadi modifikasi eksternal)
        return Collections.unmodifiableList(new ArrayList<>(paymentData));
    }
}
