package org.example.productservice.controller;

import org.example.modelproject.dto.ProductDTO;
import org.example.productservice.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.lang.module.ResolutionException;
import java.util.List;

@RestController
@RequestMapping("${api.version}/product")
public class ProductController {
    private final ProductServiceImpl productService;

    @Autowired
    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    // get all products
    @GetMapping("")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<ProductDTO>> getAllProducts(){
        return ResponseEntity.ok().body(productService.getAllProducts());
    }

    // create product
    @PostMapping("")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO product){
        return ResponseEntity.ok().body(productService.createProduct(product));
    }

    // get product by id
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable(value = "id") long productId) throws ResolutionException{
        return ResponseEntity.ok().body(productService.getProductById(productId));
    }

    // update product
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable(value = "id") long productId,
                                                   @RequestBody ProductDTO productDetails) throws ResolutionException{
        return ResponseEntity.ok().body(productService.updateProduct(productId, productDetails));
    }

    // delete product by id
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable(value = "id") long productId) throws ResolutionException{
        productService.deleteProduct(productId);
        return ResponseEntity.ok().build();
    }

    // assign category to product
    @PutMapping("{productId}/category/{categoryId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDTO> assignCategoryToProduct(@PathVariable(value="productId") long productId,
                                                          @PathVariable(value="categoryId") long categoryId) throws ResolutionException{
        return ResponseEntity.ok().body(productService.assignCategoryToProduct(productId, categoryId));
    }

}
