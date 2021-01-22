package com.tosuncu.shoppingapp.product.api;

import com.tosuncu.shoppingapp.product.model.ProductResponse;
import com.tosuncu.shoppingapp.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductApi {
    private final ProductService productService;

    @GetMapping
    public Flux<ProductResponse> getAllProducts(){
    return productService.getAll();
    }
}
