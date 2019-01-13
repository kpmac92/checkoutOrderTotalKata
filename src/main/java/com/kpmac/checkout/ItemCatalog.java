package com.kpmac.checkout;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class ItemCatalog {

    private Map<String, Item> itemMap = new HashMap<>();
    private final RoundingMode roundingMode = RoundingMode.HALF_UP;
    private final MathContext mathContext = new MathContext(6, roundingMode);

    public void setPrice(String itemName, BigDecimal amount, boolean pricedByWeight) {
        itemMap.put(itemName, new Item(itemName, amount, pricedByWeight));
    }

    public BigDecimal getPrice(String itemName) {
        Item item = getItemFromMap(itemName);

        if(item.isPricedByWeight()) {
            throw new RuntimeException(itemName + " must be weighed to get price.");
        }

        return item.getPrice().subtract(item.getMarkDown());
    }

    public BigDecimal getPrice(String itemName, BigDecimal weight) {
        Item item = getItemFromMap(itemName);

        if(!item.isPricedByWeight()) {
            throw new RuntimeException(itemName + " is not priced by weight.");
        }

        BigDecimal price = item.getPrice().subtract(item.getMarkDown());
        return price.multiply(weight, mathContext).setScale(2, roundingMode);
    }

    public void setMarkdown(String itemName, BigDecimal markdownValue) {
        Item item = getItemFromMap(itemName);

        item.setMarkDown(markdownValue);
    }

    private Item getItemFromMap(String itemName) {
        Item item = itemMap.get(itemName);

        if(item == null) {
            throw new RuntimeException("Item not found: " + itemName);
        }

        return item;
    }

}
