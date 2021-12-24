package org.example.productservice.controller;

import org.example.modelproject.Category;
import org.example.productservice.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.module.ResolutionException;
import java.util.List;

@CrossOrigin(origins = "http://localhost::3306")
@RestController
@RequestMapping("/api/v1")
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;

    // create get all categories api
    @GetMapping("/categories")
    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

    @PostMapping("/category")
    // create category
    public Category createCategory(@RequestBody Category category){
        return categoryRepository.save(category);
    }

    // get category by id
    @GetMapping("/category/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable(value = "id") long categoryId) throws ResolutionException{
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResolutionException("Category not found for this id :: " + categoryId));
        return ResponseEntity.ok().body(category);
    }

    // update category
    @PutMapping("/category/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable(value = "id") long categoryId,
                                                   @RequestBody Category categoryDetails) throws ResolutionException{
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResolutionException("Category not found for this id :: " + categoryId));
        category.setName(categoryDetails.getName());
        category.setDescription(categoryDetails.getDescription());
        categoryRepository.save(category);
        return ResponseEntity.ok().body(category);
    }

    // delete category by id
    @DeleteMapping("/category/{id}")
    public ResponseEntity<Object> deleteCategory(@PathVariable(value = "id") long categoryId) throws ResolutionException{
        categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResolutionException("Category not found for this id :: " + categoryId));
        categoryRepository.deleteById(categoryId);
        return ResponseEntity.ok().build();
    }

}
