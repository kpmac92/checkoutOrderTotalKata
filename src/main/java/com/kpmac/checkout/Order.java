package com.kpmac.checkout;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Order {

    private final ItemCatalog itemCatalog;

    private BigDecimal total = BigDecimal.valueOf(0.0);

    private Map<String, Integer> itemCountMap = new HashMap<>();

    private Map<String, BigDecimal> itemWeightMap = new HashMap<>();

    public Order(ItemCatalog itemCatalog) {
        this.itemCatalog = itemCatalog;
    }

    public void scanItem(String itemName) {
        Integer itemCount = itemCountMap.putIfAbsent(itemName, 1);

        if(itemCount != null) {
            itemCountMap.put(itemName, itemCount + 1);
        }
    }

    public void scanItem(String itemName, BigDecimal itemWeight) {
        BigDecimal totalItemWeight = itemWeightMap.putIfAbsent(itemName, itemWeight);

        if(totalItemWeight != null) {
            itemWeightMap.put(itemName, totalItemWeight.add(itemWeight));
        }
    }

    public BigDecimal getTotal() {
        BigDecimal total = BigDecimal.valueOf(0);

        for(Map.Entry<String, Integer> entry : itemCountMap.entrySet()) {
            total = total.add(itemCatalog.getPrice(entry.getKey(), entry.getValue()));
        }

        for(Map.Entry<String, BigDecimal> entry : itemWeightMap.entrySet()) {
            total = total.add(itemCatalog.getPrice(entry.getKey(), entry.getValue()));
        }

        return total;
    }
}
