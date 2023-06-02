package com.example.rendezVous.controllers;

import com.example.rendezVous.models.Categories.Category;
import com.example.rendezVous.services.categoriesService.CategoryService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;


@RestController
@RequestMapping("api/category/{parentid}/sbuCategories")
public class SubCategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity retrieveAllSubcategories(@PathVariable Long parentid) {
        // Getting the requiring category; or throwing exception if not found
        final Category parent = categoryService.getCategoryById(parentid);

        // Getting all categories in application...
        final Set<Category> subcategories = parent.getChildCategories();

        return new  ResponseEntity (subcategories, HttpStatus.OK);
    }

    @SneakyThrows
    @RequestMapping(path = "/{childid}", method = RequestMethod.POST)
    public ResponseEntity addSubcategory(@PathVariable Long parentid, @PathVariable Long childid) {
        // Getting the requiring category; or throwing exception if not found
         Category parent = categoryService.getCategoryById(parentid);


        // Getting the requiring category; or throwing exception if not found
         Category child = categoryService.getCategoryById(childid);


        // Validating if association does not exist...

      if (categoryService.isChildCategory(child, parent)) {
            throw new IllegalArgumentException("category " + parent.getId() + " already contains subcategory " + child.getId());
        }

        // Associating parent with subcategory...
        categoryService.addChildCategory(child, parent);

        return new ResponseEntity (parent,HttpStatus.OK);
    }

    @RequestMapping(path = "/{childid}", method = RequestMethod.DELETE)
    public ResponseEntity<?> removeSubcategory(@PathVariable Long parentid, @PathVariable Long childid) {
        // Getting the requiring category; or throwing exception if not found
        final Category parent = categoryService.getCategoryById(parentid);


        // Getting the requiring category; or throwing exception if not found
        final Category child = categoryService.getCategoryById(childid);


        // Validating if association exists...
        if (!categoryService.isChildCategory(child, parent)) {
            throw new IllegalArgumentException("category " + parent.getId() + " does not contain subcategory " + child.getId());
        }

        // Dis-associating parent with subcategory...
        categoryService.removeChildCategory(child, parent);

        return ResponseEntity.noContent().build();
    }
}
