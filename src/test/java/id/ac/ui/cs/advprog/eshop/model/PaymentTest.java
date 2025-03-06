package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

class PaymentTest {

    @Test
    void testPaymentCreationSuccess() {
        // Positive case: semua field valid
        Map<String, String> data = new HashMap<>();
        data.put("bankName", "BCA");
        data.put("referenceCode", "REF123");

        // Asumsikan Payment seharusnya terbentuk tanpa error
        Payment payment = new Payment("pay-001", "Bank Transfer", "SUCCESS", data);

        assertEquals("pay-001", payment.getId());
        assertEquals("Bank Transfer", payment.getMethod());
        assertEquals("SUCCESS", payment.getStatus());
        assertEquals("BCA", payment.getPaymentData().get("bankName"));
    }

    @Test
    void testPaymentCreationWithEmptyMethod() {
        // Negative case: method kosong → anggap harus lempar IllegalArgumentException
        Map<String, String> data = new HashMap<>();
        data.put("bankName", "BCA");
        data.put("referenceCode", "REF123");

        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("pay-002", "", "REJECTED", data);
        });
    }
}
