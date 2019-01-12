package com.kpmac.checkout;

import java.util.HashMap;
import java.util.Map;

public class ItemCatalog {

    private Map<String, Double> itemPriceMap = new HashMap<>();

    public void setPrice(String itemName, double amount) {
        itemPriceMap.put(itemName, amount);
    }

    public double getPrice(String itemName) {
        Double price = itemPriceMap.get(itemName);

        if(price == null) {
            throw new RuntimeException("Item not found: " + itemName);
        }

        return itemPriceMap.get(itemName);
    }
}
