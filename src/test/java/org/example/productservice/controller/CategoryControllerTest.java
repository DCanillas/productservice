package org.example.productservice.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.modelproject.dto.CategoryDTO;
import org.example.productservice.service.impl.CategoryServiceImpl;
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
@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CategoryServiceImpl categoryService;

    private List<CategoryDTO> listCategories;
    private CategoryDTO category;
    private final String url = "/api/v1/category";
    private final String urlId = "/api/v1/category/1";

    @BeforeEach
    public void setUp() throws Exception {
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

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(url)).andExpect(status().isOk()).andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(listCategories);
        assertThat(actualJsonResponse).isEqualTo(expectedJsonResponse);
    }

    @Test
    public void testCreateCategory() throws Exception {
        log.info("Test - testCreateCategory");
        Mockito.when(categoryService.createCategory(any(CategoryDTO.class))).thenReturn(category);

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(category)))
                .andExpect(status().isOk()).andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(category);
        assertThat(actualJsonResponse).isEqualTo(expectedJsonResponse);
    }

    @Test
    public void testGetCategoryById() throws Exception{
        log.info("Test - testGetCategoryById");
        Mockito.when(categoryService.getCategoryById(anyLong())).thenReturn(category);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(urlId)).andExpect(status().isOk()).andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(category);
        assertThat(actualJsonResponse).isEqualTo(expectedJsonResponse);
    }

    @Test
    public void testUpdateCategory() throws Exception {
        log.info("Test - testUpdateCategory");
        Mockito.when(categoryService.updateCategory(anyLong(), any(CategoryDTO.class))).thenReturn(category);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.put(urlId)
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(category)))
                .andExpect(status().isOk()).andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(category);
        assertThat(actualJsonResponse).isEqualTo(expectedJsonResponse);
    }

    @Test
    public void testDeleteCategory() throws Exception{
        log.info("Test - testDeleteCategory");

        mockMvc.perform(MockMvcRequestBuilders.delete(urlId)).andExpect(status().isOk());
    }
}
