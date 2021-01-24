package com.tosuncu.shoppingapp.product.service;

import com.tosuncu.shoppingapp.product.domain.Product;
import com.tosuncu.shoppingapp.product.domain.es.CategoryEs;
import com.tosuncu.shoppingapp.product.domain.es.CompanyEs;
import com.tosuncu.shoppingapp.product.domain.es.ProductEs;
import com.tosuncu.shoppingapp.product.repository.es.ProductEsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductEsService {

    private final ProductEsRepository productEsRepository;


    public Mono<ProductEs> saveNewProduct(Product product) {
        return productEsRepository.save(
                ProductEs.builder().active(product
                        .getActive())
                        .code(product.getCode())
                        .features(product.getFeatures())
                        .description(product.getDescription())
                        .id(product.getId())
                        .name(product.getName())
                        //Todo get name
                        .seller(CompanyEs.builder().id(product.getCompanyId()).name("Test").build())
                        //Todo get name
                        .category(CategoryEs.builder().id(product.getCategoryId()).name("Test").build())
                        .build());

    }

    public Flux<ProductEs> findAll() {

        return productEsRepository.findAll();
    }
}
