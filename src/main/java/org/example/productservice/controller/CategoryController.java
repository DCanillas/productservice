package org.example.productservice.controller;

import org.example.modelproject.dto.CategoryDTO;
import org.example.productservice.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.module.ResolutionException;
import java.util.List;

@RestController
@RequestMapping("${api.version}/category")
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // get all categories
    @GetMapping("")
    public List<CategoryDTO> getAllCategories(){
        return categoryService.getAllCategories();
    }

    @PostMapping("")
    // create category
    public CategoryDTO createCategory(@RequestBody CategoryDTO category){
        return categoryService.createCategory(category);
    }

    // get category by id
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable(value = "id") long categoryId) throws ResolutionException{
        return ResponseEntity.ok().body(categoryService.getCategoryById(categoryId));
    }

    // update category
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable(value = "id") long categoryId,
                                                   @RequestBody CategoryDTO categoryDetails) throws ResolutionException{
        return ResponseEntity.ok().body(categoryService.updateCategory(categoryId, categoryDetails));
    }

    // delete category by id
    @DeleteMapping("/{id}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable(value = "id") long categoryId) throws ResolutionException {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok().build();
    }

}
