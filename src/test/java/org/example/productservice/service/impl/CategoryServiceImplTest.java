package org.example.productservice.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.modelproject.dto.CategoryDTO;
import org.example.modelproject.model.Category;
import org.example.productservice.repository.CategoryRepository;
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
@SpringBootTest
public class CategoryServiceImplTest {

    @Autowired
    private ModelMapper modelMapper;

    @Mock
    private CategoryRepository categoryRepository;

    private CategoryServiceImpl categoryService;

    private List<CategoryDTO> listCategoriesDTO;
    private List<Category> listCategories;
    private CategoryDTO categoryDTO;
    private Category category;

    @BeforeEach
    public void setUp() throws Exception{
        categoryService = new CategoryServiceImpl(categoryRepository, modelMapper);
        listCategoriesDTO = new ObjectMapper().readValue(
                new File("src/test/resource/ListCategoriesDTO.json"),
                new TypeReference<List<CategoryDTO>>() {
                });
        categoryDTO = listCategoriesDTO.get(0);

        listCategories = new ObjectMapper().readValue(
                new File("src/test/resource/ListCategories.json"),
                new TypeReference<List<Category>>() {
                });
        category = listCategories.get(0);
    }

    @Test
    @Transactional
    public void testGetAllCategories(){
        log.info("Test - testGetAllCategories");

        Mockito.when(categoryRepository.findAll()).thenReturn(listCategories);

        List<CategoryDTO> actualListCategoriesDTO = categoryService.getAllCategories();
        List<CategoryDTO> expectedListCategoriesDTO = listCategories.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .collect(Collectors.toList());
        assertTrue(actualListCategoriesDTO.equals(expectedListCategoriesDTO));
    }

    @Test
    @Transactional
    public void testCreateCategory(){
        log.info("Test - testCreateCategory");
        Mockito.when(categoryRepository.save(category)).thenReturn(category);

        CategoryDTO actualCategoryDTO = categoryService.createCategory(categoryDTO);
        CategoryDTO expectedCategoryDTO = modelMapper.map(category, CategoryDTO.class);
        assertTrue(actualCategoryDTO.equals(expectedCategoryDTO));
    }

    @Test
    @Transactional
    public void testGetCategoryById(){
        log.info("Test - testGetCategoryById");
        Mockito.when(categoryRepository.findById(anyLong())).thenReturn(Optional.ofNullable(category));

        CategoryDTO actualCategoryDTO = categoryService.getCategoryById(1);
        CategoryDTO expectedCategoryDTO = modelMapper.map(category, CategoryDTO.class);
        assertTrue(actualCategoryDTO.equals(expectedCategoryDTO));
    }

    @Test
    @Transactional
    public void testUpdateCategory(){
        log.info("Test - testUpdateCategory");
        Mockito.when(categoryRepository.findById(anyLong())).thenReturn(Optional.ofNullable(category));
        Mockito.when(categoryRepository.save(category)).thenReturn(category);

        CategoryDTO actualCategoryDTO = categoryService.updateCategory(1, categoryDTO);
        CategoryDTO expectedCategoryDTO = modelMapper.map(category, CategoryDTO.class);
        assertTrue(actualCategoryDTO.equals(expectedCategoryDTO));
    }

    @Test
    @Transactional
    public void testDeleteCategory(){
        log.info("Test - testDeleteCategory");
        Mockito.when(categoryRepository.findById(anyLong())).thenReturn(Optional.ofNullable(category));
        Mockito.doNothing().when(categoryRepository).deleteById(anyLong());

        categoryService.deleteCategory(1);

        Mockito.verify(categoryRepository, times(1)).deleteById(1L);
    }

}
