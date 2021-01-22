package com.tosuncu.shoppingapp.product.domain;

public enum MoneyType {
    USD("Dollar","$"),
    EUR("Euro","€"),
    TL ("Türk Lirası","₺");

    private String label;
    private String symbol;

    MoneyType (String label,String symbol){
    this.label = label;
    this.symbol = symbol;



    }

}
