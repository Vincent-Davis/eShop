package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = new ProductRepository();
    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("d09f046-9b1b-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap User");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());

        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());

        assertFalse(productIterator.hasNext());
    }

    @Test
    void testEditProduct_Success() {
        // Arrange: Buat dan simpan produk awal
        Product product = new Product();
        product.setProductId("123");
        product.setProductName("Sampo Lama");
        product.setProductQuantity(100);
        productRepository.create(product);

        // Act: Edit produk
        Product updatedProduct = new Product();
        updatedProduct.setProductId("123");
        updatedProduct.setProductName("Sampo Baru");
        updatedProduct.setProductQuantity(150);
        productRepository.update(updatedProduct);

        // Assert: Pastikan perubahan berhasil
        Product retrievedProduct = productRepository.findById("123");
        assertNotNull(retrievedProduct);
        assertEquals("Sampo Baru", retrievedProduct.getProductName());
        assertEquals(150, retrievedProduct.getProductQuantity());
    }

    @Test
    void testEditProduct_Fail_ProductNotFound() {
        // Arrange: Buat produk yang tidak ada di repository
        Product product = new Product();
        product.setProductId("999");
        product.setProductName("Produk Tidak Ada");
        product.setProductQuantity(200);

        // Act: Coba update produk yang tidak ada
        productRepository.update(product);

        // Assert: Produk tetap tidak ada di repository
        Product retrievedProduct = productRepository.findById("999");
        assertNull(retrievedProduct);
    }

    @Test
    void testDeleteProduct_Success() {
        // Arrange: Buat dan simpan produk
        Product product = new Product();
        product.setProductId("456");
        product.setProductName("Sampo Cap");
        product.setProductQuantity(200);
        productRepository.create(product);

        // Act: Hapus produk
        productRepository.deleteById("456");

        // Assert: Produk harus hilang dari repository
        Product deletedProduct = productRepository.findById("456");
        assertNull(deletedProduct);
    }

    @Test
    void testDeleteProduct_Fail_ProductNotFound() {
        // Arrange: Pastikan repository awalnya kosong
        assertFalse(productRepository.findAll().hasNext());

        // Act: Coba hapus produk yang tidak ada
        productRepository.deleteById("999");

        // Assert: Repository tetap kosong
        assertFalse(productRepository.findAll().hasNext());
    }
}
