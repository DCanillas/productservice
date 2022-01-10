package org.example.productservice.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.modelproject.dto.CategoryDTO;
import org.example.modelproject.model.Category;
import org.example.productservice.exception.ResourceNotFoundException;
import org.example.productservice.repository.CategoryRepository;
import org.example.productservice.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // get all categories
    @Override
    public List<CategoryDTO> getAllCategories() {
        log.info("CategoryServiceImpl - Method getAllCategories");
        List<CategoryDTO> categoriesDTO = categoryRepository.findAll().stream()
                .map(category -> new ModelMapper().map(category, CategoryDTO.class))
                .collect(Collectors.toList());
        log.info("CategoryServiceImpl - Return getAllCategories: "+categoriesDTO);
        return categoriesDTO;
    }

    // create category
    @Override
    public CategoryDTO createCategory(CategoryDTO category) {
        log.info("CategoryServiceImpl - Method createCategory: "+category);
        Category categoryCreated = categoryRepository
                .save(new ModelMapper().map(category, Category.class));
        log.info("CategoryServiceImpl - Created createCategory: "+categoryCreated);
        return new ModelMapper().map(categoryCreated, CategoryDTO.class);
    }

    // get category by id
    @Override
    public CategoryDTO getCategoryById(long categoryId) {
        log.info("CategoryServiceImpl - Method getCategoryById: "+categoryId);
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found for this id :: " + categoryId));
        log.info("CategoryServiceImpl - Found getCategoryById: "+category);
        return new ModelMapper().map(category, CategoryDTO.class);
    }

    // update category
    @Override
    public CategoryDTO updateCategory(long categoryId, CategoryDTO categoryDetails) {
        log.info("CategoryServiceImpl - Method updateCategory: "+categoryId+"; "+categoryDetails);
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found for this id :: " + categoryId));
        log.info("CategoryServiceImpl - Found updateCategory: "+category);
        category.setName(categoryDetails.getName());
        category.setDescription(categoryDetails.getDescription());
        categoryRepository.save(category);
        return new ModelMapper().map(category, CategoryDTO.class);
    }

    // delete category by id
    @Override
    public void deleteCategory(long categoryId) {
        log.info("CategoryServiceImpl - Method deleteCategory: "+categoryId);
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found for this id :: " + categoryId));
        log.info("CategoryServiceImpl - Found deleteCategory: "+category);
        categoryRepository.deleteById(categoryId);
    }

}
