package com.kpmac.checkout;

public class Order {

    private final ItemCatalog itemCatalog;

    private double total = 0.0;

    public Order(ItemCatalog itemCatalog) {
        this.itemCatalog = itemCatalog;
    }

    public void scanItem(String itemName) {
        this.total += itemCatalog.getPrice(itemName);
    }

    public void scanItem(String itemName, double itemWeight) {

    }

    public double getTotal() {
        return total;
    }
}
