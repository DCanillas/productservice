package org.example.productservice.service.impl;

import org.example.modelproject.Category;
import org.example.modelproject.Product;
import org.example.productservice.dto.ProductDTO;
import org.example.productservice.repository.CategoryRepository;
import org.example.productservice.repository.ProductRepository;
import org.example.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.ArrayList;
import java.util.List;

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
        List<ProductDTO> productsDTO = mapListProductToDTO(productRepository.findAll());
        return productsDTO;
    }

    // create product
    @Override
    public ProductDTO createProduct(ProductDTO product){
        return mapProductToDTO(productRepository.save(mapDTOToProduct(product)));
    }

    // get product by id
    @Override
    public ProductDTO getProductById(long productId) throws ResolutionException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResolutionException("Product not found for this id :: " + productId));
        return mapProductToDTO(product);
    }

    // update product
    @Override
    public ProductDTO updateProduct(long productId, ProductDTO productDetails) throws ResolutionException{
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResolutionException("Product not found for this id :: " + productId));
        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());
        productRepository.save(product);
        return mapProductToDTO(product);
    }

    // delete product by id
    @Override
    public void deleteProduct(long productId) throws ResolutionException{
        productRepository.findById(productId)
                .orElseThrow(() -> new ResolutionException("Product not found for this id :: " + productId));
        productRepository.deleteById(productId);
    }

    // assign category to product
    @Override
    public ProductDTO assignCategorytoProduct(long productId, long categoryId) throws ResolutionException{
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResolutionException("product not found for this id :: " + productId));
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResolutionException("Category not found for this id :: " + categoryId));
        product.addCategory(category);
        return mapProductToDTO(productRepository.save(product));
    }

    static ProductDTO mapProductToDTO (Product product){
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setCategories(CategoryServiceImpl.mapListCategoryToDTO(product.getCategories()));
        return productDTO;
    }

    static List<ProductDTO> mapListProductToDTO (List<Product> products){
        List<ProductDTO> productsDTO = new ArrayList<>();
        for (Product product : products) {
            productsDTO.add(mapProductToDTO(product));
        }
        return productsDTO;
    }

    static Product mapDTOToProduct (ProductDTO productDTO){
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        return product;
    }
}
