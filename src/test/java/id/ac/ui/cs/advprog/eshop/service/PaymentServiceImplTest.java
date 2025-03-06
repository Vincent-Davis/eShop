package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceImplTest {

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @Mock
    private PaymentRepository paymentRepository;

    private Order order;

    @BeforeEach
    void setUp() {
        // Karena Order harus memiliki setidaknya 1 produk agar tidak dilempar IllegalArgumentException,
        // kita buat produk dummy dan masukkan ke dalam daftar produk.
        List<Product> products = new ArrayList<>();
        Product dummyProduct = new Product();
        dummyProduct.setProductId("dummy-001");
        dummyProduct.setProductName("Dummy Product");
        dummyProduct.setProductQuantity(1);
        products.add(dummyProduct);

        // Buat Order dengan daftar produk yang valid dan status default "WAITING_PAYMENT"
        order = new Order("order1", products, 1708560000L, "Safira");
        order.setStatus("WAITING_PAYMENT");
    }

    // --- addPayment ---
    // Positive: Data valid menghasilkan status SUCCESS dan Order di-update menjadi SUCCESS
    @Test
    void testAddPaymentBankTransfer_Success() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", "REF123456");

        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Payment payment = paymentService.addPayment(order, "Bank Transfer", paymentData);
        assertEquals(PaymentStatus.SUCCESS.name(), payment.getStatus());
        assertEquals("SUCCESS", order.getStatus());
    }

    // Negative: Data tidak valid (misal bankName kosong) menghasilkan REJECTED dan Order menjadi FAILED
    @Test
    void testAddPaymentBankTransfer_Rejected() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", ""); // kosong
        paymentData.put("referenceCode", "REF123456");

        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Payment payment = paymentService.addPayment(order, "Bank Transfer", paymentData);
        assertEquals(PaymentStatus.REJECTED.name(), payment.getStatus());
        assertEquals("FAILED", order.getStatus());
    }

    // --- setStatus ---
    // Positive: Update status Payment menjadi SUCCESS, sehingga Order di-update menjadi SUCCESS
    @Test
    void testSetStatus_Success() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", "REF123456");
        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Payment payment = paymentService.addPayment(order, "Bank Transfer", paymentData);
        Payment updated = paymentService.setStatus(payment, PaymentStatus.SUCCESS.name());
        assertEquals(PaymentStatus.SUCCESS.name(), updated.getStatus());
        assertEquals("SUCCESS", order.getStatus());
    }

    // Negative: Update status Payment ke nilai yang tidak valid (misal "INVALID") tidak mengubah Order status
    @Test
    void testSetStatus_Invalid() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", "REF123456");
        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Payment payment = paymentService.addPayment(order, "Bank Transfer", paymentData);
        // Karena "INVALID" tidak termasuk PaymentStatus, maka akan dilempar IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
            paymentService.setStatus(payment, "INVALID");
        });
        // Order status tetap tidak berubah (tetap "SUCCESS" dari addPayment)
        assertEquals("SUCCESS", order.getStatus());
    }

    // --- getPayment ---
    // Positive: Mengembalikan Payment yang ditemukan berdasarkan ID
    @Test
    void testGetPayment_Found() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", "REF123456");
        Payment payment = new Payment("pay-002", "Bank Transfer", PaymentStatus.SUCCESS.name(), paymentData);
        when(paymentRepository.findById("pay-002")).thenReturn(payment);
        Payment found = paymentService.getPayment("pay-002");
        assertNotNull(found);
        assertEquals("pay-002", found.getId());
    }

    // Negative: Mengembalikan null jika Payment tidak ditemukan
    @Test
    void testGetPayment_NotFound() {
        when(paymentRepository.findById("nonexistent")).thenReturn(null);
        Payment found = paymentService.getPayment("nonexistent");
        assertNull(found);
    }

    // --- getAllPayments ---
    // Positive: Mengembalikan list Payment yang ada
    @Test
    void testGetAllPayments_WithData() {
        List<Payment> payments = new ArrayList<>();
        Map<String, String> data1 = new HashMap<>();
        data1.put("bankName", "BCA");
        data1.put("referenceCode", "REF123456");
        Map<String, String> data2 = new HashMap<>();
        data2.put("bankName", "Mandiri");
        data2.put("referenceCode", "REF654321");

        payments.add(new Payment("pay-001", "Bank Transfer", PaymentStatus.SUCCESS.name(), data1));
        payments.add(new Payment("pay-002", "Bank Transfer", PaymentStatus.REJECTED.name(), data2));

        when(paymentRepository.findAll()).thenReturn(payments);
        List<Payment> allPayments = paymentService.getAllPayments();
        assertNotNull(allPayments);
        assertEquals(2, allPayments.size());
    }

    // Negative: Mengembalikan list kosong jika tidak ada Payment
    @Test
    void testGetAllPayments_Empty() {
        when(paymentRepository.findAll()).thenReturn(new ArrayList<>());
        List<Payment> allPayments = paymentService.getAllPayments();
        assertNotNull(allPayments);
        assertTrue(allPayments.isEmpty());
    }
}
