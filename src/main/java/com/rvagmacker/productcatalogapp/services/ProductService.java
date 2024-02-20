package com.rvagmacker.productcatalogapp.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rvagmacker.productcatalogapp.domain.aws.MessageDTO;
import com.rvagmacker.productcatalogapp.domain.category.Category;
import com.rvagmacker.productcatalogapp.domain.products.Products;
import com.rvagmacker.productcatalogapp.domain.products.ProductsDTO;
import com.rvagmacker.productcatalogapp.exceptions.CategoryNotFoundException;
import com.rvagmacker.productcatalogapp.exceptions.ProductNotFoundException;
import com.rvagmacker.productcatalogapp.repositories.ProductRepository;

@Service
public class ProductService {

  private ProductRepository productRepository;

  private CategoryService categoryService;

  private AwsSnsService snsService;

  public ProductService(ProductRepository productRepository, CategoryService categoryService, AwsSnsService snsService) {
    this.productRepository = productRepository;
    this.categoryService = categoryService;
    this.snsService = snsService;
  }

  public Products insert(ProductsDTO productData) {
    this.categoryService.getById(productData.categoryId()).orElseThrow(CategoryNotFoundException::new);
    Products newProducts = new Products(productData);
    this.productRepository.save(newProducts);
    this.snsService.publish(new MessageDTO(newProducts.toString()));
    return newProducts;
  }

  public List<Products> getAll() {
    return productRepository.findAll();
  }

  public Products update(String id, ProductsDTO productsData) {
    Products products = this.productRepository.findById(id).orElseThrow(ProductNotFoundException::new);

    if(productsData.categoryId() != null) {
      this.categoryService.getById(productsData.categoryId()).orElseThrow(CategoryNotFoundException::new);

      products.setCategory(productsData.categoryId());
    }

    if(!productsData.title().isEmpty()) {
      products.setTitle(productsData.title());
    }

    if(!productsData.description().isEmpty()) {
      products.setDescription(productsData.description());
    }

    if(productsData.price() != null) {
      products.setPrice(productsData.price());
    }

    this.productRepository.save(products);

    this.snsService.publish(new MessageDTO(products.toString()));
    return products;
  }

  public void delete(String id) {
    Products products = this.productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
    this.productRepository.delete(products);
  }
}
