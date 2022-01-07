package org.example.productservice.service;

import org.example.modelproject.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    List<ProductDTO> getAllProducts();
    ProductDTO createProduct(ProductDTO product);
    ProductDTO getProductById(long productId);
    ProductDTO updateProduct(long productId, ProductDTO productDetails);
    void deleteProduct(long productId);
    ProductDTO assignCategoryToProduct(long productId, long categoryId);
}
