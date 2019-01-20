package com.kpmac.checkout;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

    public void removeItem(String itemName) {
        Integer itemCount = itemCountMap.get(itemName);

        if(itemCount != null) {
            removeCountedItem(itemName, itemCount);
        } else if(itemWeightMap.containsKey(itemName)){
            itemWeightMap.remove(itemName);
        } else {
            throw new RuntimeException("Item not found in this order.");
        }
    }

    private void removeCountedItem(String itemName, Integer itemCount) {
        itemCount = itemCount - 1;

        if (itemCount == 0) {
            itemCountMap.remove(itemName);
        } else {
            itemCountMap.put(itemName, itemCount);
        }
    }

    public BigDecimal getTotal() {
        BigDecimal total = BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_UP);

        for(Map.Entry<String, Integer> entry : itemCountMap.entrySet()) {
            total = total.add(itemCatalog.getPrice(entry.getKey(), entry.getValue()));
        }

        for(Map.Entry<String, BigDecimal> entry : itemWeightMap.entrySet()) {
            total = total.add(itemCatalog.getPrice(entry.getKey(), entry.getValue()));
        }

        return total;
    }
}
