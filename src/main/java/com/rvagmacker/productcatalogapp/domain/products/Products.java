package com.rvagmacker.productcatalogapp.domain.products;

import org.json.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "products")
@Getter
@Setter
@NoArgsConstructor
public class Products {
  @Id
  private String id;
  private String title;
  private String description;
  private String ownerId;
  private Integer price;
  private String category;

  public Products(ProductsDTO productsDTO) {
    this.title = productsDTO.title();
    this.description = productsDTO.description();
    this.ownerId = productsDTO.ownerId();
    this.price = productsDTO.price();
    this.category = productsDTO.categoryId();
  }

  @Override
  public String toString() {
    JSONObject json = new JSONObject();
    json.put("title", this.title);
    json.put("description", this.description);
    json.put("ownerId", this.ownerId);
    json.put("id", this.id);
    json.put("category", this.category);
    json.put("price", this.price);
    json.put("type", "product");

    return json.toString();
  }
}

