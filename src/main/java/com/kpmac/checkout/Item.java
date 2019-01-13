package com.kpmac.checkout;

import java.math.BigDecimal;

public class Item {

    private final Boolean pricedByWeight;
    private final String name;
    private final BigDecimal price;

    public Item(String name, BigDecimal price, Boolean pricedByWeight) {
        this.name = name;
        this.price = price;
        this.pricedByWeight = pricedByWeight;
    }

    public Boolean isPricedByWeight() {
        return pricedByWeight;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
