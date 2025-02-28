package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService service;

    @Test
    public void testCreateProductPage() throws Exception {
        mockMvc.perform(get("/product/create"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("product"))
                .andExpect(view().name("CreateProduct"));
    }

    @Test
    public void testCreateProductPost() throws Exception {
        // Buat objek dummy untuk dikembalikan oleh service.create()
        Product dummyProduct = new Product();
//        dummyProduct.setProductId("dummy-id");
        dummyProduct.setProductName("Test Product");
        dummyProduct.setProductQuantity(10);

        when(service.create(any(Product.class))).thenReturn(dummyProduct);

        mockMvc.perform(post("/product/create")
                        .param("productName", "Test Product")
                        .param("productQuantity", "10"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("list"));

        verify(service, times(1)).create(any(Product.class));
    }


    @Test
    public void testProductListPage() throws Exception {
        Product product = new Product();
        product.setProductId("123");
        product.setProductName("Test Product");
        product.setProductQuantity(10);

        when(service.findAll()).thenReturn(Arrays.asList(product));

        mockMvc.perform(get("/product/list"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("products"))
                .andExpect(view().name("ProductList"));

        verify(service, times(1)).findAll();
    }

    @Test
    public void testEditProductPage() throws Exception {
        Product product = new Product();
        product.setProductId("123");
        product.setProductName("Test Product");
        product.setProductQuantity(10);

        when(service.findById("123")).thenReturn(product);

        mockMvc.perform(get("/product/edit/123"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("product"))
                .andExpect(view().name("EditProduct"));

        verify(service, times(1)).findById("123");
    }

    @Test
    public void testUpdateProduct() throws Exception {
        doNothing().when(service).update(any(Product.class));

        mockMvc.perform(post("/product/edit")
                        .param("productId", "123")
                        .param("productName", "Updated Product")
                        .param("productQuantity", "20"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/product/list"));

        verify(service, times(1)).update(any(Product.class));
    }

    @Test
    public void testDeleteProduct() throws Exception {
        doNothing().when(service).deleteById("123");

        mockMvc.perform(get("/product/delete/123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/product/list"));

        verify(service, times(1)).deleteById("123");
    }
}
