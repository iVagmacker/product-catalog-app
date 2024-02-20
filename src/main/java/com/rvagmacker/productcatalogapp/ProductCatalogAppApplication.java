package com.rvagmacker.productcatalogapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication()
@EnableMongoRepositories()
public class ProductCatalogAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductCatalogAppApplication.class, args);
	}

}
