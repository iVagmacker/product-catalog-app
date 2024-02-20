package com.rvagmacker.productcatalogapp.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rvagmacker.productcatalogapp.domain.products.Products;
import com.rvagmacker.productcatalogapp.domain.products.ProductsDTO;
import com.rvagmacker.productcatalogapp.services.ProductService;


@RestController
@RequestMapping("/api/products")
public class ProductController {
  private ProductService service;

  public ProductController(ProductService service) {
    this.service = service;
  }

  @PostMapping
  public ResponseEntity<Products> insert(@RequestBody ProductsDTO productsData) {
    Products newProducts = this.service.insert(productsData);
    return ResponseEntity.ok().body(newProducts);
  }

  @GetMapping
  public ResponseEntity<List<Products>> getAll() {
    List<Products> categories = this.service.getAll();
    return ResponseEntity.ok().body(categories);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Products> update(@PathVariable("id") String id, @RequestBody ProductsDTO productsData) {
    Products updatedProducts = this.service.update(id, productsData);
    return ResponseEntity.ok().body(updatedProducts);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Products> delete(@PathVariable("id") String id) {
    this.service.delete(id);
    return ResponseEntity.noContent().build();
  }
}
