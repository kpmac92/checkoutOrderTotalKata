package com.kpmac.checkout;

import java.math.BigDecimal;

public class Order {

    private final ItemCatalog itemCatalog;

    private BigDecimal total = BigDecimal.valueOf(0.0);

    public Order(ItemCatalog itemCatalog) {
        this.itemCatalog = itemCatalog;
    }

    public void scanItem(String itemName) {
        this.total = this.total.add(itemCatalog.getPrice(itemName));
    }

    public void scanItem(String itemName, BigDecimal itemWeight) {
        this.total = this.total.add(itemCatalog.getPrice(itemName, itemWeight));
    }

    public BigDecimal getTotal() {
        return total;
    }
}
