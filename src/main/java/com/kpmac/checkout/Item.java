package com.kpmac.checkout;

public class Item {

    private final Boolean pricedByWeight;
    private final String name;
    private final Double price;

    public Item(String name, Double price, Boolean pricedByWeight) {
        this.name = name;
        this.price = price;
        this.pricedByWeight = pricedByWeight;
    }

    public Boolean isPricedByWeight() {
        return pricedByWeight;
    }

    public Double getPrice() {
        return price;
    }
}
