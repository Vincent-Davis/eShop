package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaymentRepositoryTest {

    private PaymentRepository paymentRepository;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();
    }

    // Test 1: Positive - Simpan satu Payment dan pastikan dapat ditemukan menggunakan findById
    @Test
    void testSavePayment_Positive() {
        Map<String, String> data = new HashMap<>();
        data.put("bankName", "BCA");
        data.put("referenceCode", "REF123456");
        Payment payment = new Payment("pay-001", "Bank Transfer", PaymentStatus.SUCCESS.name(), data);
        Payment savedPayment = paymentRepository.save(payment);
        assertNotNull(savedPayment);
        assertEquals("pay-001", savedPayment.getId());
    }

    // Test 2: Positive - Simpan beberapa Payment dan pastikan findAll mengembalikan daftar yang benar
    @Test
    void testSaveMultiplePayments_Positive() {
        Map<String, String> data = new HashMap<>();
        data.put("bankName", "BCA");
        data.put("referenceCode", "REF123456");
        Payment payment1 = new Payment("pay-001", "Bank Transfer", PaymentStatus.SUCCESS.name(), data);
        Payment payment2 = new Payment("pay-002", "Bank Transfer", PaymentStatus.REJECTED.name(), data);
        paymentRepository.save(payment1);
        paymentRepository.save(payment2);
        List<Payment> allPayments = paymentRepository.findAll();
        assertEquals(2, allPayments.size());
    }

    // Test 3: Positive - findById mengembalikan Payment yang sesuai jika ditemukan
    @Test
    void testFindById_Positive() {
        Map<String, String> data = new HashMap<>();
        data.put("bankName", "BCA");
        data.put("referenceCode", "REF123456");
        Payment payment = new Payment("pay-001", "Bank Transfer", PaymentStatus.SUCCESS.name(), data);
        paymentRepository.save(payment);
        Payment found = paymentRepository.findById("pay-001");
        assertNotNull(found);
        assertEquals("pay-001", found.getId());
    }

    // Test 4: Negative - findById mengembalikan null jika Payment tidak ditemukan
    @Test
    void testFindById_Negative() {
        Payment found = paymentRepository.findById("non-existent");
        assertNull(found);
    }

    // Test 5: Positive - findAll mengembalikan daftar Payment jika repository tidak kosong
    @Test
    void testFindAll_Positive() {
        Map<String, String> data = new HashMap<>();
        data.put("bankName", "BCA");
        data.put("referenceCode", "REF123456");
        Payment payment1 = new Payment("pay-001", "Bank Transfer", PaymentStatus.SUCCESS.name(), data);
        Payment payment2 = new Payment("pay-002", "Bank Transfer", PaymentStatus.REJECTED.name(), data);
        paymentRepository.save(payment1);
        paymentRepository.save(payment2);
        List<Payment> allPayments = paymentRepository.findAll();
        assertNotNull(allPayments);
        assertFalse(allPayments.isEmpty());
        assertEquals(2, allPayments.size());
    }

    // Test 6: Negative - findAll mengembalikan list kosong jika tidak ada Payment yang disimpan
    @Test
    void testFindAll_Negative() {
        List<Payment> allPayments = paymentRepository.findAll();
        assertNotNull(allPayments);
        assertTrue(allPayments.isEmpty());
    }

    // Test 7: Positive - Simpan duplicate Payment (dengan ID yang sama) dan pastikan repository menyimpan keduanya
    @Test
    void testSave_DuplicatePayment_Positive() {
        Map<String, String> data = new HashMap<>();
        data.put("bankName", "BCA");
        data.put("referenceCode", "REF123456");
        Payment payment1 = new Payment("pay-001", "Bank Transfer", PaymentStatus.SUCCESS.name(), data);
        Payment payment2 = new Payment("pay-001", "Bank Transfer", PaymentStatus.SUCCESS.name(), data);
        paymentRepository.save(payment1);
        paymentRepository.save(payment2);
        List<Payment> allPayments = paymentRepository.findAll();
        // Karena repository sederhana tidak mengecek duplicate, seharusnya terdapat 2 entri.
        assertEquals(2, allPayments.size());
    }

    // Test 8: Positive - Jika terdapat duplicate Payment, findById mengembalikan occurrence pertama
    @Test
    void testFindById_AfterDuplicate() {
        Map<String, String> data = new HashMap<>();
        data.put("bankName", "BCA");
        data.put("referenceCode", "REF123456");
        Payment payment1 = new Payment("pay-001", "Bank Transfer", PaymentStatus.SUCCESS.name(), data);
        Payment payment2 = new Payment("pay-001", "Bank Transfer", PaymentStatus.REJECTED.name(), data);
        paymentRepository.save(payment1);
        paymentRepository.save(payment2);
        Payment found = paymentRepository.findById("pay-001");
        assertNotNull(found);
        // Berdasarkan implementasi, findById mengembalikan occurrence pertama
        assertEquals(PaymentStatus.SUCCESS.name(), found.getStatus());
    }
}
