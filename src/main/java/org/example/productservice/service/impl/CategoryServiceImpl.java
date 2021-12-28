package org.example.productservice.service.impl;

import org.example.modelproject.Category;
import org.example.productservice.dto.CategoryDTO;
import org.example.productservice.repository.CategoryRepository;
import org.example.productservice.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.ArrayList;
import java.util.List;

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
        List<CategoryDTO> categoriesDTO = mapListCategoryToDTO(categoryRepository.findAll());
        return categoriesDTO;
    }

    // create category
    @Override
    public CategoryDTO createCategory(CategoryDTO category) {
        return mapCategoryToDTO(categoryRepository.save(mapDTOToCategory(category)));
    }

    // get category by id
    @Override
    public CategoryDTO getCategoryById(long categoryId) throws ResolutionException {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResolutionException("Category not found for this id :: " + categoryId));
        return mapCategoryToDTO(category);
    }

    // update category
    @Override
    public CategoryDTO updateCategory(long categoryId, CategoryDTO categoryDetails) throws ResolutionException {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResolutionException("Category not found for this id :: " + categoryId));
        category.setName(categoryDetails.getName());
        category.setDescription(categoryDetails.getDescription());
        categoryRepository.save(category);
        return mapCategoryToDTO(category);
    }

    // delete category by id
    @Override
    public void deleteCategory(long categoryId) throws ResolutionException {
        categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResolutionException("Category not found for this id :: " + categoryId));
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
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        return category;
    }
}
