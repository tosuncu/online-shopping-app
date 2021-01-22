package com.tosuncu.shoppingapp.product.repository.mongo;

import com.tosuncu.shoppingapp.product.domain.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ProductRepository extends ReactiveMongoRepository<Product, String> {
}
