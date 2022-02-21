package org.example.productservice.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.modelproject.dto.CategoryDTO;
import org.example.productservice.security.TestSecurityConfig;
import org.example.productservice.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = TestSecurityConfig.class)
public class CategoryControllerTest {
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CategoryServiceImpl categoryService;

    private CategoryController categoryController;

    private List<CategoryDTO> listCategories;
    private CategoryDTO category;

    @BeforeEach
    public void setUp() throws Exception {
        categoryController = new CategoryController(categoryService);
        listCategories = new ObjectMapper().readValue(
                new File("src/test/resource/ListCategoriesDTO.json"),
                new TypeReference<List<CategoryDTO>>() {
                });
        category = listCategories.get(0);
    }

    @Test
    public void testGetAllCategories() throws Exception{
        log.info("Test - testGetAllCategories");
        Mockito.when(categoryService.getAllCategories()).thenReturn(listCategories);

        ResponseEntity<List<CategoryDTO>> response = categoryController.getAllCategories();

        String actualJsonResponse = objectMapper.writeValueAsString(response.getBody());
        String expectedJsonResponse = objectMapper.writeValueAsString(listCategories);
        assertThat(actualJsonResponse).isEqualTo(expectedJsonResponse);
    }

    @Test
    public void testCreateCategory() throws Exception {
        log.info("Test - testCreateCategory");
        Mockito.when(categoryService.createCategory(any(CategoryDTO.class))).thenReturn(category);

        ResponseEntity<CategoryDTO> response = categoryController.createCategory(category);

        String actualJsonResponse = objectMapper.writeValueAsString(response.getBody());
        String expectedJsonResponse = objectMapper.writeValueAsString(category);
        assertThat(actualJsonResponse).isEqualTo(expectedJsonResponse);
    }

    @Test
    public void testGetCategoryById() throws Exception {
        log.info("Test - testGetCategoryById");
        Mockito.when(categoryService.getCategoryById(anyLong())).thenReturn(category);

        ResponseEntity<CategoryDTO> response = categoryController.getCategoryById(category.getId());

        String actualJsonResponse = objectMapper.writeValueAsString(response.getBody());
        String expectedJsonResponse = objectMapper.writeValueAsString(category);
        assertThat(actualJsonResponse).isEqualTo(expectedJsonResponse);
    }

    @Test
    public void testUpdateCategory() throws Exception {
        log.info("Test - testUpdateCategory");
        Mockito.when(categoryService.updateCategory(anyLong(), any(CategoryDTO.class))).thenReturn(category);

        ResponseEntity<CategoryDTO> response = categoryController.updateCategory(category.getId(), category);

        String actualJsonResponse = objectMapper.writeValueAsString(response.getBody());
        String expectedJsonResponse = objectMapper.writeValueAsString(category);
        assertThat(actualJsonResponse).isEqualTo(expectedJsonResponse);
    }

    @Test
    public void testDeleteCategory() throws Exception{
        log.info("Test - testDeleteCategory");

        ResponseEntity<CategoryDTO> response = categoryController.deleteCategory(category.getId());
        assertThat(response.getStatusCode()).isEqualTo(ResponseEntity.ok().build().getStatusCode());
    }
}
