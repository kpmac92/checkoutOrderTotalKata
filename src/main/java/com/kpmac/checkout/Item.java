package com.kpmac.checkout;

import com.kpmac.checkout.special.PriceSpecial;

import java.math.BigDecimal;

public class Item {

    private final Boolean pricedByWeight;
    private final String name;
    private final BigDecimal price;
    private BigDecimal markDown;
    private PriceSpecial priceSpecial;

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

    public BigDecimal getMarkDown() {
        return markDown;
    }

    public void setMarkDown(BigDecimal markDown) {
        this.markDown = markDown;
    }

    public String getName() {
        return name;
    }

    public PriceSpecial getPriceSpecial() {
        return priceSpecial;
    }

    public void setPriceSpecial(PriceSpecial priceSpecial) {
        this.priceSpecial = priceSpecial;
    }
}
