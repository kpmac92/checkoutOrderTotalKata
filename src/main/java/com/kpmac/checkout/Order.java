package com.kpmac.checkout;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class Order {

    private final ItemCatalog itemCatalog;

    private BigDecimal total = BigDecimal.valueOf(0.0);

    private Map<Item, Integer> itemCountMap = new HashMap<>();

    private Map<Item, BigDecimal> itemWeightMap = new HashMap<>();

    public Order(ItemCatalog itemCatalog) {
        this.itemCatalog = itemCatalog;
    }

    public void scanItem(String itemName) {
        Item item = itemCatalog.getItem(itemName);
        Integer itemCount = itemCountMap.putIfAbsent(item, 1);

        if(itemCount != null) {
            itemCountMap.put(item, itemCount + 1);
        }
    }

    public void scanItem(String itemName, BigDecimal itemWeight) {
        Item item = itemCatalog.getItem(itemName);
        BigDecimal totalItemWeight = itemWeightMap.putIfAbsent(item, itemWeight);

        if(totalItemWeight != null) {
            itemWeightMap.put(item, totalItemWeight.add(itemWeight));
        }
    }

    public void removeItem(String itemName) {
        Item item = itemCatalog.getItem(itemName);
        Integer itemCount = itemCountMap.get(item);

        if(itemCount != null) {
            removeCountedItem(item, itemCount);
        } else if(itemWeightMap.containsKey(item)){
            itemWeightMap.remove(item);
        } else {
            throw new RuntimeException("Item not found in this order.");
        }
    }

    private void removeCountedItem(Item item, Integer itemCount) {
        itemCount = itemCount - 1;


        if (itemCount == 0) {
            itemCountMap.remove(item);
        } else {
            itemCountMap.put(item, itemCount);
        }
    }

    public BigDecimal getTotal() {
        BigDecimal total = BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_UP);

        for(Map.Entry<Item, Integer> entry : itemCountMap.entrySet()) {
            total = total.add(entry.getKey().getPrice(entry.getValue()));
        }

        for(Map.Entry<Item, BigDecimal> entry : itemWeightMap.entrySet()) {
            total = total.add(entry.getKey().getPrice(entry.getValue()));
        }

        return total;
    }
}
