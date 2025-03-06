package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PaymentRepository {
    private final List<Payment> paymentData = new ArrayList<>();

    public Payment save(Payment payment) {
        // Menyimpan Payment ke list
        paymentData.add(payment);
        return payment;
    }

    public Payment findById(String paymentId) {
        // Iterasi sederhana untuk menemukan Payment berdasarkan ID
        for (Payment payment : paymentData) {
            if (payment.getId().equals(paymentId)) {
                return payment;
            }
        }
        return null;
    }

    public List<Payment> findAll() {
        // Mengembalikan list Payment (bisa diubah oleh caller)
        return paymentData;
    }
}
