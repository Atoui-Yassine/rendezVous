package com.example.rendezVous.services.categoriesService;

import com.example.rendezVous.exceptions.EntityNotFoundException;
import com.example.rendezVous.models.Categories.Category;
import com.example.rendezVous.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Transactional
    public Category getCategoryById(Long id) {
        Category category= categoryRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("category Not Found with id : " + id));
        return category;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    public Category createCategory(String name) {
        Category category = new Category();
        category.setName(name);

        return categoryRepository.save(category);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    public void updateCategory(Category category, String name) {
        category.setName(name);
        categoryRepository.save(category);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    public void deleteCategory(Category category) {
        categoryRepository.delete(category);
    }


    @Transactional
    public boolean isChildCategory(Category category, Category parent) {
       if(category.getParent() !=null && category.getParent().getId() == parent.getId())
        return true;
       else {
           return false;
       }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')  OR hasRole('ROLE_PRO')")
    @Transactional
    public void addChildCategory(Category category, Category parent) {
        category.setParent(parent);
        categoryRepository.save(category);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    public void removeChildCategory(Category category, Category parent) {
        category.setParent(null);
        categoryRepository.save(category);
    }
    @PreAuthorize("hasRole('ROLE_USER')")
    @Transactional
    public Category getCategoryByChild(Long id) {
        Category category= categoryRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("category Not Found with id : " + id));
        return category;
    }
}
