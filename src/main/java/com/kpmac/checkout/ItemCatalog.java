package com.kpmac.checkout;

import com.kpmac.checkout.special.BulkPrice;
import com.kpmac.checkout.special.BuyOneGetOne;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ItemCatalog {

    private Map<String, Item> itemMap = new HashMap<>();

    public ItemCatalog(){

    }

    public ItemCatalog(Item... items) {
        Arrays.stream(items).forEach(item -> itemMap.put(item.getName(), item));
    }

    public void setPrice(String itemName, BigDecimal amount, boolean pricedByWeight) {
        itemMap.put(itemName, new Item(itemName, amount, pricedByWeight));
    }

    public Item getItem(String itemName) {
        return getItemFromMap(itemName);
    }

    public void setMarkdown(String itemName, BigDecimal markdownValue) {
        Item item = getItemFromMap(itemName);

        item.setMarkDown(markdownValue);
    }

    public void addBogoSpecial(String itemName, int specialQuantity, BigDecimal priceModifier) {
        addBogoSpecial(itemName, specialQuantity, priceModifier, null);
    }

    public void addBogoSpecial(String itemName, int specialQuantity, BigDecimal priceModifier, Integer limit) {
        Item item = getItemFromMap(itemName);

        item.setPriceSpecial(new BuyOneGetOne(item, specialQuantity, priceModifier, limit));
    }

    public void addBulkSpecial(String itemName, int specialQuantity, BigDecimal specialPrice) {
        addBulkSpecial(itemName, specialQuantity, specialPrice, null);
    }

    public void addBulkSpecial(String itemName, int specialQuantity, BigDecimal specialPrice, Integer limit) {
        Item item = getItemFromMap(itemName);

        item.setPriceSpecial(new BulkPrice(item, specialQuantity, specialPrice, limit));
    }

    private Item getItemFromMap(String itemName) {
        Item item = itemMap.get(itemName);

        if(item == null) {
            throw new RuntimeException("Item not found: " + itemName);
        }

        return item;
    }
}
