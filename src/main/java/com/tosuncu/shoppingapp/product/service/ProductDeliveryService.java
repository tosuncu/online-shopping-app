package com.tosuncu.shoppingapp.product.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ProductDeliveryService {

    public String getDeliveryInfo(String productId){
        //TODO
        return "tomorrow";

    }

    public boolean freeDeliveryCheck(String productId, BigDecimal price){
        //TODO

        return price.compareTo(BigDecimal.ONE) >= 0 ;

    }
}
