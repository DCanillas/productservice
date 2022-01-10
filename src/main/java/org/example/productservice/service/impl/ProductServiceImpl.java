package org.example.productservice.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.modelproject.dto.ProductDTO;
import org.example.modelproject.model.Category;
import org.example.modelproject.model.Product;
import org.example.productservice.exception.ResourceNotFoundException;
import org.example.productservice.repository.CategoryRepository;
import org.example.productservice.repository.ProductRepository;
import org.example.productservice.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    // get all products
    @Override
    public List<ProductDTO> getAllProducts(){
        log.info("ProductServiceImpl - Method getAllProducts");
        List<ProductDTO> productsDTO = productRepository.findAll().stream()
                .map(product -> new ModelMapper().map(product, ProductDTO.class))
                .collect(Collectors.toList());
        log.info("ProductServiceImpl - Return getAllProducts: "+productsDTO);
        return productsDTO;
    }

    // create product
    @Override
    public ProductDTO createProduct(ProductDTO product){
        log.info("ProductServiceImpl - Method createProduct: "+product);
        Product productCreated = productRepository
                .save(new ModelMapper().map(product, Product.class));
        log.info("ProductServiceImpl - Created createProduct: "+productCreated);
        return new ModelMapper().map(productCreated, ProductDTO.class);
    }

    // get product by id
    @Override
    public ProductDTO getProductById(long productId) {
        log.info("ProductServiceImpl - Method getProductById: "+productId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found for this id :: " + productId));
        log.info("ProductServiceImpl - Found getProductById: "+product);
        return new ModelMapper().map(product, ProductDTO.class);
    }

    // update product
    @Override
    public ProductDTO updateProduct(long productId, ProductDTO productDetails) {
        log.info("ProductServiceImpl - Method updateProduct: "+productId+"; "+productDetails);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found for this id :: " + productId));
        log.info("ProductServiceImpl - Found updateProduct: "+product);
        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());
        productRepository.save(product);
        return new ModelMapper().map(product, ProductDTO.class);
    }

    // delete product by id
    @Override
    public void deleteProduct(long productId) {
        log.info("ProductServiceImpl - Method deleteProduct: "+productId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found for this id :: " + productId));
        log.info("ProductServiceImpl - Found deleteProduct: "+product);
        productRepository.deleteById(productId);
    }

    // assign category to product
    @Override
    public ProductDTO assignCategoryToProduct(long productId, long categoryId) {
        log.info("ProductServiceImpl - Method assignCategoryToProduct: "+productId+"; "+categoryId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("product not found for this id :: " + productId));
        log.info("ProductServiceImpl - Found assignCategoryToProduct: "+product);
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found for this id :: " + categoryId));
        log.info("ProductServiceImpl - Category assignCategoryToProduct: "+category);
        product.addCategory(category);
        return new ModelMapper().map(productRepository.save(product), ProductDTO.class);
    }

}
