package com.example.rendezVous.controllers;

import com.example.rendezVous.DTOs.request.CategoryDto;
import com.example.rendezVous.models.Categories.Category;
import com.example.rendezVous.models.userModel.Img;
import com.example.rendezVous.repositories.CategoryRepository;
import com.example.rendezVous.services.categoriesService.CategoryService;
import com.example.rendezVous.storage.services.FilesStoregeService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    private FilesStoregeService<Img> imageService;
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity retrieveAllCategories() {
        // Getting all categories in application...
        List<Category> categories = categoryService.getAllCategories();

        return new  ResponseEntity(categories, HttpStatus.OK);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity retrieveCategory(@PathVariable Long id) {
        // Getting the requiring category; or throwing exception if not found
        final Category category = categoryService.getCategoryById(id);


        return new ResponseEntity(category,HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createCategory(@RequestBody @Valid CategoryDto request) {
        // Creating a new category in the application...
        final Category category = categoryService.createCategory(request.getName());

        return new ResponseEntity (category,HttpStatus.CREATED);
    }

    @SneakyThrows
    @RequestMapping(path = "/img/{catId}",method = RequestMethod.POST)
    public ResponseEntity<?> addImgtoCategory(@RequestParam("file") MultipartFile file,@PathVariable Long catId){
        Img img = imageService.store(file);
       Category cat=categoryService.getCategoryById(catId);
        cat.setImage(img);
        return new ResponseEntity<>(categoryRepository.save(cat),HttpStatus.OK);
    }


    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateCategory(@PathVariable Long id, @RequestBody @Valid CategoryDto request) {
        // Getting the requiring category; or throwing exception if not found
        final Category category = categoryService.getCategoryById(id);


        // Updating a category in the application...
        categoryService.updateCategory(category, request.getName());

        return new ResponseEntity (category,HttpStatus.OK);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        // Getting the requiring category; or throwing exception if not found
        final Category category = categoryService.getCategoryById(id);
        // Deleting category from the application...
        categoryService.deleteCategory(category);

        return ResponseEntity.noContent().build();
    }

}
