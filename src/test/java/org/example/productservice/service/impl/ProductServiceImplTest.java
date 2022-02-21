package org.example.productservice.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.modelproject.dto.ProductDTO;
import org.example.modelproject.model.Category;
import org.example.modelproject.model.Product;
import org.example.productservice.repository.CategoryRepository;
import org.example.productservice.repository.ProductRepository;
import org.example.productservice.security.TestSecurityConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest(classes = TestSecurityConfig.class)
public class ProductServiceImplTest {
    @Autowired
    private ModelMapper modelMapper;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    private ProductServiceImpl productService;

    private List<ProductDTO> listProductsDTO;
    private List<Product> listProducts;
    private ProductDTO productDTO;
    private Product product;
    private Category category;

    @BeforeEach
    public void setUp() throws Exception{
        productService = new ProductServiceImpl(productRepository, categoryRepository, modelMapper);
        listProductsDTO = new ObjectMapper().readValue(
                new File("src/test/resource/ListProductsDTO.json"),
                new TypeReference<List<ProductDTO>>() {
                });
        productDTO = listProductsDTO.get(0);

        listProducts = new ObjectMapper().readValue(
                new File("src/test/resource/ListProducts.json"),
                new TypeReference<List<Product>>() {
                });
        product = listProducts.get(0);

        category = (new ObjectMapper().readValue(
                new File("src/test/resource/ListCategories.json"),
                new TypeReference<List<Category>>() {
                })).get(0);
    }

    @Test
    @Transactional
    public void testGetAllProducts(){
        log.info("Test - testGetAllProducts");

        Mockito.when(productRepository.findAll()).thenReturn(listProducts);

        List<ProductDTO> actualListProductsDTO = productService.getAllProducts();
        List<ProductDTO> expectedListProductsDTO = listProducts.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .collect(Collectors.toList());
        assertTrue(actualListProductsDTO.equals(expectedListProductsDTO));
    }

    @Test
    @Transactional
    public void testCreateProduct(){
        log.info("Test - testCreateProduct");
        log.info("Test - product: "+product);
        Mockito.when(productRepository.save(product)).thenReturn(product);

        ProductDTO actualProductDTO = productService.createProduct(productDTO);
        ProductDTO expectedProductDTO =
                modelMapper.map(product, ProductDTO.class);
        assertTrue(actualProductDTO.equals(expectedProductDTO));
    }

    @Test
    @Transactional
    public void testGetProductById(){
        log.info("Test - testGetProductById");
        Mockito.when(productRepository.findById(anyLong())).thenReturn(Optional.ofNullable(product));

        ProductDTO actualProductDTO = productService.getProductById(1);
        ProductDTO expectedProductDTO =
                modelMapper.map(product, ProductDTO.class);
        assertTrue(actualProductDTO.equals(expectedProductDTO));
    }

    @Test
    @Transactional
    public void testUpdateProduct(){
        log.info("Test - testUpdateProduct");
        Mockito.when(productRepository.findById(anyLong())).thenReturn(Optional.ofNullable(product));
        Mockito.when(productRepository.save(product)).thenReturn(product);

        ProductDTO actualProductDTO = productService.updateProduct(1, productDTO);
        ProductDTO expectedProductDTO =
                modelMapper.map(product, ProductDTO.class);
        assertTrue(actualProductDTO.equals(expectedProductDTO));
    }

    @Test
    @Transactional
    public void testDeleteProduct(){
        log.info("Test - testDeleteProduct");
        Mockito.when(productRepository.findById(anyLong())).thenReturn(Optional.ofNullable(product));
        Mockito.doNothing().when(productRepository).deleteById(anyLong());

        productService.deleteProduct(1);

        Mockito.verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    @Transactional
    public void testAssignCategoryToProduct(){
        log.info("Test - testAssignCategoryToProduct");
        Mockito.when(productRepository.findById(anyLong())).thenReturn(Optional.ofNullable(product));
        Mockito.when(categoryRepository.findById(anyLong())).thenReturn(Optional.ofNullable(category));
        Mockito.when(productRepository.save(product)).thenReturn(product);

        ProductDTO actualProductDTO = productService.assignCategoryToProduct(1, 1);
        ProductDTO expectedProductDTO =
                modelMapper.map(product, ProductDTO.class);
        assertTrue(actualProductDTO.equals(expectedProductDTO));
    }
}
