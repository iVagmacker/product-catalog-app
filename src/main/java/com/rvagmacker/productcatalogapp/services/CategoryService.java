package com.rvagmacker.productcatalogapp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.rvagmacker.productcatalogapp.domain.aws.MessageDTO;
import com.rvagmacker.productcatalogapp.domain.category.Category;
import com.rvagmacker.productcatalogapp.domain.category.CategoryDTO;
import com.rvagmacker.productcatalogapp.exceptions.CategoryNotFoundException;
import com.rvagmacker.productcatalogapp.repositories.CategoryRepository;

@Service
public class CategoryService {

  private CategoryRepository repository;

  private AwsSnsService snsService;

  public CategoryService(CategoryRepository repository, AwsSnsService snsService) {
    this.repository = repository;
    this.snsService = snsService;
  }

  public Category insert(CategoryDTO categoryData) {
    Category newCategory = new Category(categoryData);
    this.repository.save(newCategory);
        
    this.snsService.publish(new MessageDTO(newCategory.toString()));

    return newCategory;
  }

  public List<Category> getAll() {
    return repository.findAll();
  }

  public Optional<Category> getById(String id) {
    return repository.findById(id);
  }

  public Category update(String id, CategoryDTO categoryData) {

    Category category = this.repository.findById(id).orElseThrow(CategoryNotFoundException::new);

    if(!categoryData.title().isEmpty()) {
      category.setTitle(categoryData.title());
    }

    if(!categoryData.description().isEmpty()) {
      category.setDescription(categoryData.description());
    }

    this.repository.save(category);
    this.snsService.publish(new MessageDTO(category.toString()));
    
    return category;
  }

  public void delete(String id) {
    Category category = this.repository.findById(id).orElseThrow(CategoryNotFoundException::new);
    this.repository.delete(category);
  }
}
