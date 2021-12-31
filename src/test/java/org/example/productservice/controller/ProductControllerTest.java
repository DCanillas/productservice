package org.example.productservice.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.productservice.dto.ProductDTO;
import org.example.productservice.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.File;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WebMvcTest(ProductController.class)
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductServiceImpl productService;

    private List<ProductDTO> listProducts;
    private ProductDTO product;
    private final String url = "/api/v1/product";
    private final String urlId = "/api/v1/product/1";
    private final String urlIdC = "/api/v1/product/1/category/1";

    @BeforeEach
    public void setUp() throws Exception {
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

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(url)).andExpect(status().isOk()).andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(listProducts);
        assertThat(actualJsonResponse).isEqualTo(expectedJsonResponse);
    }

    @Test
    public void testCreateProduct() throws Exception {
        log.info("Test - testCreateProduct");
        Mockito.when(productService.createProduct(any(ProductDTO.class))).thenReturn(product);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post(url)
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk()).andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(product);
        assertThat(actualJsonResponse).isEqualTo(expectedJsonResponse);
    }

    @Test
    public void testGetProductById() throws Exception{
        log.info("Test - testGetProductById");
        Mockito.when(productService.getProductById(anyLong())).thenReturn(product);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(urlId)).andExpect(status().isOk()).andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(product);
        assertThat(actualJsonResponse).isEqualTo(expectedJsonResponse);
    }

    @Test
    public void testUpdateProduct() throws Exception {
        log.info("Test - testUpdateProduct");
        Mockito.when(productService.updateProduct(anyLong(), any(ProductDTO.class))).thenReturn(product);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(urlId)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk()).andReturn();;

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(product);
        assertThat(actualJsonResponse).isEqualTo(expectedJsonResponse);
    }

    @Test
    public void testAssignCategoryToProduct() throws Exception {
        log.info("Test - testAssignCategoryToProduct");
        Mockito.when(productService.assignCategoryToProduct(anyLong(), anyLong())).thenReturn(product);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(urlIdC)).andExpect(status().isOk()).andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(product);
        assertThat(actualJsonResponse).isEqualTo(expectedJsonResponse);
    }

    @Test
    public void testDeleteProduct() throws Exception{
        log.info("Test - testDeleteProduct");

        mockMvc.perform(MockMvcRequestBuilders.delete(urlId)).andExpect(status().isOk());
    }
}
