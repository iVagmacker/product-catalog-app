package com.rvagmacker.productcatalogapp.domain.products;

public record ProductsDTO(String title, String description, Integer price, String ownerId, String categoryId) {
  
}
