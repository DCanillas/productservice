package org.example.productservice.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.modelproject.model.Category;
import org.example.modelproject.dto.CategoryDTO;
import org.example.productservice.exception.ResourceNotFoundException;
import org.example.productservice.repository.CategoryRepository;
import org.example.productservice.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.ArrayList;
import java.util.List;

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
        List<CategoryDTO> categoriesDTO = mapListCategoryToDTO(categoryRepository.findAll());
        log.info("CategoryServiceImpl - Return getAllCategories: "+categoriesDTO);
        return categoriesDTO;
    }

    // create category
    @Override
    public CategoryDTO createCategory(CategoryDTO category) {
        log.info("CategoryServiceImpl - Method createCategory: "+category);
        Category categoryCreated = categoryRepository.save(mapDTOToCategory(category));
        log.info("CategoryServiceImpl - Created createCategory: "+categoryCreated);
        return mapCategoryToDTO(categoryCreated);
    }

    // get category by id
    @Override
    public CategoryDTO getCategoryById(long categoryId) {
        log.info("CategoryServiceImpl - Method getCategoryById: "+categoryId);
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found for this id :: " + categoryId));
        log.info("CategoryServiceImpl - Found getCategoryById: "+category);
        return mapCategoryToDTO(category);
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
        return mapCategoryToDTO(category);
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

    static CategoryDTO mapCategoryToDTO (Category category){
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        categoryDTO.setDescription(category.getDescription());
        return categoryDTO;
    }

    static List<CategoryDTO> mapListCategoryToDTO (List<Category> categories){
        List<CategoryDTO> categoriesDTO = new ArrayList<>();
        for (Category category : categories) {
            categoriesDTO.add(mapCategoryToDTO(category));
        }
        return categoriesDTO;
    }

    static Category mapDTOToCategory (CategoryDTO categoryDTO){
        Category category = new Category();
        category.setId(categoryDTO.getId());
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        return category;
    }
}
