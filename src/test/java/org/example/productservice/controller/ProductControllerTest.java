package org.example.productservice.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.modelproject.dto.ProductDTO;
import org.example.productservice.security.TestSecurityConfig;
import org.example.productservice.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.File;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = TestSecurityConfig.class)
public class ProductControllerTest {
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductServiceImpl productService;

    private ProductController productController;

    private List<ProductDTO> listProducts;
    private ProductDTO product;

    @BeforeEach
    public void setUp() throws Exception {
        productController = new ProductController(productService);
        listProducts = new ObjectMapper().readValue(
                new File("src/test/resource/ListProductsDTO.json"),
                new TypeReference<List<ProductDTO>>() {
                });
        product = listProducts.get(0);
    }

    @Test
    public void testGetAllProducts() throws Exception{
        log.info("Test - testGetAllProducts");
        Mockito.when(productService.getAllProducts()).thenReturn(listProducts);

        ResponseEntity<List<ProductDTO>> response = productController.getAllProducts();

        String actualJsonResponse = objectMapper.writeValueAsString(response.getBody());
        String expectedJsonResponse = objectMapper.writeValueAsString(listProducts);
        assertThat(actualJsonResponse).isEqualTo(expectedJsonResponse);
    }

    @Test
    public void testCreateProduct() throws Exception {
        log.info("Test - testCreateProduct");
        Mockito.when(productService.createProduct(any(ProductDTO.class))).thenReturn(product);

        ResponseEntity<ProductDTO> response = productController.createProduct(product);

        String actualJsonResponse = objectMapper.writeValueAsString(response.getBody());
        String expectedJsonResponse = objectMapper.writeValueAsString(product);
        assertThat(actualJsonResponse).isEqualTo(expectedJsonResponse);
    }

    @Test
    public void testGetProductById() throws Exception{
        log.info("Test - testGetProductById");
        Mockito.when(productService.getProductById(anyLong())).thenReturn(product);

        ResponseEntity<ProductDTO> response = productController.getProductById(product.getId());

        String actualJsonResponse = objectMapper.writeValueAsString(response.getBody());
        String expectedJsonResponse = objectMapper.writeValueAsString(product);
        assertThat(actualJsonResponse).isEqualTo(expectedJsonResponse);
    }

    @Test
    public void testUpdateProduct() throws Exception {
        log.info("Test - testUpdateProduct");
        Mockito.when(productService.updateProduct(anyLong(), any(ProductDTO.class))).thenReturn(product);

        ResponseEntity<ProductDTO> response = productController.updateProduct(product.getId(), product);

        String actualJsonResponse = objectMapper.writeValueAsString(response.getBody());
        String expectedJsonResponse = objectMapper.writeValueAsString(product);
        assertThat(actualJsonResponse).isEqualTo(expectedJsonResponse);
    }

    @Test
    public void testAssignCategoryToProduct() throws Exception {
        log.info("Test - testAssignCategoryToProduct");
        Mockito.when(productService.assignCategoryToProduct(anyLong(), anyLong())).thenReturn(product);

        ResponseEntity<ProductDTO> response = productController.assignCategoryToProduct(product.getId(), product.getId());

        String actualJsonResponse = objectMapper.writeValueAsString(response.getBody());
        String expectedJsonResponse = objectMapper.writeValueAsString(product);
        assertThat(actualJsonResponse).isEqualTo(expectedJsonResponse);
    }

    @Test
    public void testDeleteProduct() throws Exception{
        log.info("Test - testDeleteProduct");

        ResponseEntity<ProductDTO> response = productController.deleteProduct(product.getId());
        assertThat(response.getStatusCode()).isEqualTo(ResponseEntity.ok().build().getStatusCode());
    }
}
