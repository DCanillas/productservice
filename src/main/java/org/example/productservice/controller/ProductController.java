package org.example.productservice.controller;

import org.example.modelproject.Product;
import org.example.productservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.module.ResolutionException;
import java.util.List;

@CrossOrigin(origins = "http://localhost::3306")
@RestController
@RequestMapping("/api/v1")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    // create get all products api
    @GetMapping("/products")
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    @PostMapping("/product")
    // create product
    public Product createProduct(@RequestBody Product product){
        return productRepository.save(product);
    }

    // get product by id
    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable(value = "id") long productId) throws ResolutionException{
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResolutionException("Product not found for this id :: " + productId));
        return ResponseEntity.ok().body(product);
    }

    // update product
    @PutMapping("/product/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable(value = "id") long productId,
                                                   @RequestBody Product productDetails) throws ResolutionException{
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResolutionException("Product not found for this id :: " + productId));
        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());
        productRepository.save(product);
        return ResponseEntity.ok().body(product);
    }

    // delete product by id
    @DeleteMapping("/product/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable(value = "id") long productId) throws ResolutionException{
        productRepository.findById(productId)
                .orElseThrow(() -> new ResolutionException("Product not found for this id :: " + productId));
        productRepository.deleteById(productId);
        return ResponseEntity.ok().build();
    }

}
