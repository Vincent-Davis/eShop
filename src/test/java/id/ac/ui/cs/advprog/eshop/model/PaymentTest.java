package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

public class PaymentTest {

    // Test 1: Positive - Menguji getter getId() dengan input valid
    @Test
    public void testGetId_Positive() {
        Map<String, String> data = new HashMap<>();
        data.put("bankName", "BCA");
        data.put("referenceCode", "REF123456");
        Payment payment = new Payment("pay-001", "Bank Transfer", PaymentStatus.SUCCESS.name(), data);
        assertEquals("pay-001", payment.getId());
    }

    // Test 2: Positive - Menguji getter getMethod() dengan input valid
    @Test
    public void testGetMethod_Positive() {
        Map<String, String> data = new HashMap<>();
        data.put("bankName", "BCA");
        data.put("referenceCode", "REF123456");
        Payment payment = new Payment("pay-001", "Bank Transfer", PaymentStatus.SUCCESS.name(), data);
        assertEquals("Bank Transfer", payment.getMethod());
    }

    // Test 3: Positive - Menguji getter getStatus() dengan input valid
    @Test
    public void testGetStatus_Positive() {
        Map<String, String> data = new HashMap<>();
        data.put("bankName", "BCA");
        data.put("referenceCode", "REF123456");
        Payment payment = new Payment("pay-001", "Bank Transfer", PaymentStatus.SUCCESS.name(), data);
        assertEquals(PaymentStatus.SUCCESS.name(), payment.getStatus());
    }

    // Test 4: Positive - Menguji getter getPaymentData() dengan input valid
    @Test
    public void testGetPaymentData_Positive() {
        Map<String, String> data = new HashMap<>();
        data.put("bankName", "BCA");
        data.put("referenceCode", "REF123456");
        Payment payment = new Payment("pay-001", "Bank Transfer", PaymentStatus.SUCCESS.name(), data);
        assertNotNull(payment.getPaymentData());
        assertEquals("BCA", payment.getPaymentData().get("bankName"));
        assertEquals("REF123456", payment.getPaymentData().get("referenceCode"));
    }

    // Test 5: Negative - Mencoba membuat Payment dengan method kosong harus melempar exception
    @Test
    public void testPaymentCreation_EmptyMethod_ShouldThrowException() {
        Map<String, String> data = new HashMap<>();
        data.put("bankName", "BCA");
        data.put("referenceCode", "REF123456");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Payment("pay-002", "", PaymentStatus.REJECTED.name(), data);
        });
        String expectedMessage = "Method cannot be empty or null";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    // Test 6: Negative - Mencoba membuat Payment dengan method null harus melempar exception
    @Test
    public void testPaymentCreation_NullMethod_ShouldThrowException() {
        Map<String, String> data = new HashMap<>();
        data.put("bankName", "BCA");
        data.put("referenceCode", "REF123456");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Payment("pay-003", null, PaymentStatus.REJECTED.name(), data);
        });
        String expectedMessage = "Method cannot be empty or null";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    // Test 7: Negative - Jika paymentData adalah null, getter getPaymentData() harus mengembalikan null
    @Test
    public void testGetPaymentData_NullPaymentData() {
        Payment payment = new Payment("pay-004", "Bank Transfer", PaymentStatus.SUCCESS.name(), null);
        assertNull(payment.getPaymentData());
    }

    // Test 8: Comprehensive - Uji semua getter sekaligus dengan input valid
    @Test
    public void testPayment_AllFields() {
        Map<String, String> data = new HashMap<>();
        data.put("bankName", "Mandiri");
        data.put("referenceCode", "REF987654");
        Payment payment = new Payment("pay-005", "Bank Transfer", PaymentStatus.SUCCESS.name(), data);

        assertEquals("pay-005", payment.getId());
        assertEquals("Bank Transfer", payment.getMethod());
        assertEquals(PaymentStatus.SUCCESS.name(), payment.getStatus());
        assertNotNull(payment.getPaymentData());
        assertEquals("Mandiri", payment.getPaymentData().get("bankName"));
        assertEquals("REF987654", payment.getPaymentData().get("referenceCode"));
    }
}
