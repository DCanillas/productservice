package org.example.productservice.service;

import org.example.modelproject.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> getAllCategories();
    CategoryDTO createCategory(CategoryDTO category);
    CategoryDTO getCategoryById(long categoryId);
    CategoryDTO updateCategory(long categoryId, CategoryDTO categoryDetails);
    void deleteCategory(long categoryId);
}
