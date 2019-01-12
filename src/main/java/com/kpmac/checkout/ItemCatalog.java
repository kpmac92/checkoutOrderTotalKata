package com.kpmac.checkout;

import java.util.HashMap;
import java.util.Map;

public class ItemCatalog {

    private Map<String, Item> itemMap = new HashMap<>();

    public void setPrice(String itemName, double amount, boolean pricedByWeight) {
        itemMap.put(itemName, new Item(itemName, amount, pricedByWeight));
    }

    public double getPrice(String itemName) {
        Item item = itemMap.get(itemName);

        if(item == null) {
            throw new RuntimeException("Item not found: " + itemName);
        } else if(item.isPricedByWeight()) {
            throw new RuntimeException(itemName + " must be weighed to get price.");
        }

        return item.getPrice();
    }

}
