package org.example.productservice.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.modelproject.model.Category;
import org.example.modelproject.model.Product;
import org.example.productservice.repository.CategoryRepository;
import org.example.productservice.repository.CustomerRepository;
import org.example.productservice.repository.OrderRepository;
import org.example.productservice.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
public class ServiceInDBTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    @Transactional
    public void testInDB(){
        log.info("Test - testInDB");

        Category category = new Category();
        category.setName("VideoGames");
        category.setDescription("Play on your screen");

        Category categorySaved = categoryRepository.save(category);
        log.info("Category saved: "+categorySaved);

        log.info("Categories: "+categoryRepository.findAll());

        Product product = new Product();
        product.setName("Metroid");
        product.setDescription("Defeat all metroids");
        product.setPrice(49.95);
        List<Category> categories = new ArrayList<>();
        categories.add(category);
        product.setCategories(categories);

        Product productSaved = productRepository.save(product);
        log.info("Product saved: "+productSaved);

        List<Product> productsFound = productRepository.findAll();
        productsFound.forEach(productFor -> log.info("Product: "+productFor));
    }
}
