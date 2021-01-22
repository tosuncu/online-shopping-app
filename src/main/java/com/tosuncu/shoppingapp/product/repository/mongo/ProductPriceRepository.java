package com.tosuncu.shoppingapp.product.repository.mongo;

import com.tosuncu.shoppingapp.product.domain.ProductPrice;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ProductPriceRepository extends ReactiveMongoRepository<ProductPrice, String> {
}
