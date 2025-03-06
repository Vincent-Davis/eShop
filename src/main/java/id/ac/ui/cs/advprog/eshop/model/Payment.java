package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import java.util.Map;

public class Payment {
    private String id;
    private String method;
    private PaymentStatus status;
    private Map<String, String> paymentData;

    // Constructor yang menerima status dalam bentuk String, lalu dikonversi ke enum
    public Payment(String id, String method, String status, Map<String, String> paymentData) {
        if (method == null || method.trim().isEmpty()) {
            throw new IllegalArgumentException("Method cannot be empty or null");
        }
        this.id = id;
        this.method = method;
        try {
            this.status = PaymentStatus.valueOf(status);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status: " + status);
        }
        this.paymentData = paymentData;
    }

    // Getter tetap mengembalikan nilai String (menggunakan enum.name())
    public String getId() {
        return id;
    }

    public String getMethod() {
        return method;
    }

    public String getStatus() {
        return status.name();
    }

    public Map<String, String> getPaymentData() {
        return paymentData;
    }
}
