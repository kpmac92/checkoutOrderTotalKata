package com.kpmac.checkout;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ItemCatalog {

    private Map<String, Item> itemMap = new HashMap<>();
    private final RoundingMode roundingMode = RoundingMode.HALF_UP;

    public ItemCatalog(){

    }

    public ItemCatalog(Item... items) {
        Arrays.stream(items).forEach(item -> itemMap.put(item.getName(), item));
    }

    public void setPrice(String itemName, BigDecimal amount, boolean pricedByWeight) {
        itemMap.put(itemName, new Item(itemName, amount, pricedByWeight));
    }

    public BigDecimal getPrice(String itemName, Integer itemCount) {
        Item item = getItemFromMap(itemName);

        if(item.isPricedByWeight()) {
            throw new RuntimeException(itemName + " must be weighed to get price.");
        }

        return getItemPrice(item, itemCount).setScale(2, roundingMode);
    }

    public BigDecimal getPrice(String itemName, BigDecimal weight) {
        Item item = getItemFromMap(itemName);

        if(!item.isPricedByWeight()) {
            throw new RuntimeException(itemName + " is not priced by weight.");
        }

        return getItemPrice(item, weight).setScale(2, roundingMode);
    }

    public void setMarkdown(String itemName, BigDecimal markdownValue) {
        Item item = getItemFromMap(itemName);

        item.setPriceSpecial(new FlatMarkdown(item.getPrice(), markdownValue));
    }

    public void addBogoSpecial(String itemName, int itemsAtFullPrice, int itemAtSpecialPrice, BigDecimal discount) {

    }

    private Item getItemFromMap(String itemName) {
        Item item = itemMap.get(itemName);

        if(item == null) {
            throw new RuntimeException("Item not found: " + itemName);
        }

        return item;
    }

    private BigDecimal getItemPriceWithMarkDown(Item item) {
        return item.getMarkDown() == null ? item.getPrice() : item.getPrice().subtract(item.getMarkDown());
    }

    private BigDecimal getItemPrice(Item item, int itemCount) {
        return item.getPriceSpecial() == null ? calculatePriceWithoutSpecial(item, itemCount)
                : item.getPriceSpecial().getPrice(itemCount);
    }

    private BigDecimal getItemPrice(Item item, BigDecimal weight) {
        return item.getPriceSpecial() == null ? calculatePriceWithoutSpecial(item, weight)
                : item.getPriceSpecial().getPrice(weight);
    }

    private BigDecimal calculatePriceWithoutSpecial(Item item, int itemCount) {
        return item.getPrice().multiply(BigDecimal.valueOf(itemCount));
    }

    private BigDecimal calculatePriceWithoutSpecial(Item item, BigDecimal weight) {
        return item.getPrice().multiply(weight);
    }
}
