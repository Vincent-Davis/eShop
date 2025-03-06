package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    private Map<String, Order> paymentOrderMapping = new HashMap<>();

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {
        PaymentStatus status = determineStatus(paymentData);
        Payment payment = new Payment(UUID.randomUUID().toString(), method, status.name(), paymentData);
        paymentRepository.save(payment);
        paymentOrderMapping.put(payment.getId(), order);
        updateOrderStatus(order, status);
        return payment;
    }

    @Override
    public Payment setStatus(Payment payment, String status) {
        PaymentStatus newStatus = PaymentStatus.valueOf(status);
        payment.setStatus(newStatus);
        Order order = paymentOrderMapping.get(payment.getId());
        if (order != null) {
            updateOrderStatus(order, newStatus);
        }
        return paymentRepository.save(payment);
    }

    @Override
    public Payment getPayment(String paymentId) {
        return paymentRepository.findById(paymentId);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    // Helper method untuk menentukan status berdasarkan paymentData
    private PaymentStatus determineStatus(Map<String, String> paymentData) {
        String bankName = paymentData.get("bankName");
        String referenceCode = paymentData.get("referenceCode");
        if (bankName == null || bankName.trim().isEmpty() ||
                referenceCode == null || referenceCode.trim().isEmpty()) {
            return PaymentStatus.REJECTED;
        }
        return PaymentStatus.SUCCESS;
    }

    // Helper method untuk memperbarui status Order berdasarkan status Payment
    private void updateOrderStatus(Order order, PaymentStatus status) {
        if (status == PaymentStatus.SUCCESS) {
            order.setStatus("SUCCESS");
        } else if (status == PaymentStatus.REJECTED) {
            order.setStatus("FAILED");
        }
        // Untuk status lain (misal, INVALID), Order tidak diupdate
    }
}
