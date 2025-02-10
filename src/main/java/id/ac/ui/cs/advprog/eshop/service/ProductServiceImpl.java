package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product create(Product product) {
        productRepository.create(product);
        return product;
    }

    @Override
    public List<Product> findAll() {
        Iterator<Product> productIterator = productRepository.findAll();
        List<Product> allProduct = new ArrayList<>();
        productIterator.forEachRemaining(allProduct::add);
        return allProduct;
    }

    @Override
    public Product findById(String id) {
        Iterator<Product> productIterator = productRepository.findAll(); // Dapatkan iterator

        while (productIterator.hasNext()) { // Looping melalui iterator
            Product product = productIterator.next();
            if (product.getProductId().equals(id)) {
                return product; // Jika ketemu, langsung return
            }
        }

        return null; // Jika tidak ditemukan, return null
    }


    @Override
    public void update(Product product) {
        List<Product> allProducts = new ArrayList<>();
        productRepository.findAll().forEachRemaining(allProducts::add);

        for (Product p : allProducts) {
            if (p.getProductId().equals(product.getProductId())) {
                p.setProductName(product.getProductName());
                p.setProductQuantity(product.getProductQuantity());
            }
        }
    }
}
