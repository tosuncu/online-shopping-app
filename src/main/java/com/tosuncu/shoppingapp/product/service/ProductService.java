package com.tosuncu.shoppingapp.product.service;

import com.tosuncu.shoppingapp.product.domain.MoneyType;
import com.tosuncu.shoppingapp.product.domain.es.ProductEs;
import com.tosuncu.shoppingapp.product.model.ProductResponse;
import com.tosuncu.shoppingapp.product.model.ProductSaveRequest;
import com.tosuncu.shoppingapp.product.model.ProductSellerResponse;
import com.tosuncu.shoppingapp.product.repository.es.ProductEsRepository;
import com.tosuncu.shoppingapp.product.repository.mongo.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.awt.print.Pageable;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductEsRepository productEsRepository;
    private final ProductRepository productRepository;
    private final ProductPriceService productPriceService;
    private final ProductDeliveryService productDeliveryService;
    private final ProductAmountService productAmountService;
    private final ProductImageService productImageService;

    public Flux<ProductResponse> getAll() {
        return productEsRepository.findAll().map(this::mapToDo);

    }


    ProductResponse save(ProductSaveRequest productSaveRequest) {
        // 1 - mongoya yaz sorgula
        // 2 - es güncelle
        // 3 - redis  güncelle
        // 4 - es ile cevap dön
        // 5 - response nesnesine dönüştür
        return null;
    }


    /**
     * // 2 - Calculated fieldları işle
     * // 3 - redisten ihtiyaç olanları getir
     * // 4 - response nesnesine dönüştğr
     */
    private ProductResponse mapToDo(ProductEs item) {
        BigDecimal productPrice = productPriceService.getByMoneyType(item.getId(), MoneyType.USD);

       return ProductResponse.builder()
                .price(productPrice)
                .name(item.getName())
                .features(item.getFeatures())
                .id(item.getId())
                .description(item.getDescription())
                .deliveryIn(productDeliveryService.getDeliveryInfo(item.getId()))
                .categoryId(item.getCategory().getId())
                .available(productAmountService.getByProductId(item.getId()))
                .freeDelivery(productDeliveryService.freeDeliveryCheck(item.getId(), productPrice))
                .moneyType(MoneyType.USD)
                .image(productImageService.getProductMainImage(item.getId()))
                .seller(ProductSellerResponse.builder().id(item.getSeller().getId()).name(item.getSeller().getName()).build())
                .build();


    }
}
