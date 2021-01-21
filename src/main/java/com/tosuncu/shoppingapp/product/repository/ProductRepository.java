package com.tosuncu.shoppingapp.product.repository;

import com.tosuncu.shoppingapp.product.domain.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
