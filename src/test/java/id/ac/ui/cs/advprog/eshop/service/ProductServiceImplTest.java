package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl service;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreate() {
        Product product = new Product();
        product.setProductId("123");
        product.setProductName("Test Product");
        product.setProductQuantity(10);

        when(productRepository.create(product)).thenReturn(product);

        Product result = service.create(product);
        assertEquals(product, result);
        verify(productRepository, times(1)).create(product);
    }

    @Test
    public void testFindAll() {
        Product product1 = new Product();
        product1.setProductId("123");
        product1.setProductName("Test Product 1");
        product1.setProductQuantity(10);

        Product product2 = new Product();
        product2.setProductId("456");
        product2.setProductName("Test Product 2");
        product2.setProductQuantity(20);

        List<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);

        Iterator<Product> iterator = products.iterator();
        when(productRepository.findAll()).thenReturn(iterator);

        List<Product> result = service.findAll();
        assertEquals(2, result.size());
        assertTrue(result.contains(product1));
        assertTrue(result.contains(product2));
        verify(productRepository, times(1)).findAll();
    }

    @Test
    public void testFindById() {
        Product product = new Product();
        product.setProductId("123");
        product.setProductName("Test Product");
        product.setProductQuantity(10);

        when(productRepository.findById("123")).thenReturn(product);

        Product result = service.findById("123");
        assertEquals(product, result);
        verify(productRepository, times(1)).findById("123");
    }

    @Test
    public void testUpdate() {
        Product product = new Product();
        product.setProductId("123");
        product.setProductName("Test Product");
        product.setProductQuantity(10);

        doNothing().when(productRepository).update(product);
        service.update(product);

        verify(productRepository, times(1)).update(product);
    }

    @Test
    public void testDeleteById() {
        doNothing().when(productRepository).deleteById("123");
        service.deleteById("123");
        verify(productRepository, times(1)).deleteById("123");
    }
}
