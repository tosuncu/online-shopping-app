package com.tosuncu.shoppingapp.product.service;

import com.tosuncu.shoppingapp.product.domain.MoneyType;
import com.tosuncu.shoppingapp.product.domain.Product;
import com.tosuncu.shoppingapp.product.domain.ProductImage;
import com.tosuncu.shoppingapp.product.domain.es.ProductEs;
import com.tosuncu.shoppingapp.product.model.ProductResponse;
import com.tosuncu.shoppingapp.product.model.ProductSaveRequest;
import com.tosuncu.shoppingapp.product.model.ProductSellerResponse;
import com.tosuncu.shoppingapp.product.repository.es.ProductEsRepository;
import com.tosuncu.shoppingapp.product.repository.mongo.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.awt.print.Pageable;
import java.math.BigDecimal;
import java.util.List;

import static com.tosuncu.shoppingapp.product.domain.ProductImage.ImageType.FEATURE;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class ProductService {


    private final ProductRepository productRepository;
    private final ProductPriceService productPriceService;
    private final ProductDeliveryService productDeliveryService;
    private final ProductAmountService productAmountService;
    private final ProductImageService productImageService;
    private final ProductEsService productEsService;


    public Flux<ProductResponse> getAll() {
        return productEsService.findAll().map(this::mapToDto);

    }


 public   ProductResponse save(ProductSaveRequest productSaveRequest) {
      Product product =  Product.builder()
                .active(Boolean.TRUE)
                .code("PR001")
                .categoryId(productSaveRequest.getCategoryId())
                .companyId(productSaveRequest.getSellerId())
                .description(productSaveRequest.getDescription())

                .features(productSaveRequest.getFeatures())
                .name(productSaveRequest.getName())
                .productImage(productSaveRequest.getImages().stream().map(it -> new ProductImage(FEATURE, it)).collect(toList()))
                .build();

     product = productRepository.save(product).block();

   return this.mapToDto( productEsService.saveNewProduct(product).block());


        // 1 - mongoya yaz sorgula
        // 2 - es güncelle
        // 3 - redis  güncelle
        // 4 - es ile cevap dön
        // 5 - response nesnesine dönüştür

    }


    /**
     * // 2 - Calculated fieldları işle
     * // 3 - redisten ihtiyaç olanları getir
     * // 4 - response nesnesine dönüştğr
     */
    private ProductResponse mapToDto(ProductEs item) {
        if (item == null){
            return null;
        }


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

    public Mono<Long> count() {
       return productRepository.count();
    }
}
