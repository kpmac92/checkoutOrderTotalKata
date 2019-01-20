package com.kpmac.checkout.special;

import com.kpmac.checkout.Item;

import java.math.BigDecimal;

public class BulkPrice implements PriceSpecial{

    private final Item item;
    private final int specialQuantity;
    private final BigDecimal specialPrice;
    private final Integer limit;

    public BulkPrice(Item item, int specialQuantity, BigDecimal specialPrice, Integer limit) {
        this.item = item;
        this.specialQuantity = specialQuantity;
        this.specialPrice = specialPrice;
        this.limit = limit;
    }

    @Override
    public BigDecimal getPrice(int quantity) {
        int itemsAtBasePrice;
        int itemsAtSpecialPrice;

        if(limit == null || quantity < limit) {
            itemsAtBasePrice = quantity % specialQuantity ;
            itemsAtSpecialPrice = quantity - itemsAtBasePrice;
        } else {
             itemsAtBasePrice = limit % specialQuantity + (quantity - limit);
             itemsAtSpecialPrice = quantity - itemsAtBasePrice;
        }

        BigDecimal specialPriceTotal = specialPrice.multiply(BigDecimal.valueOf(itemsAtSpecialPrice));
        BigDecimal basePriceTotal = item.getPrice().multiply(BigDecimal.valueOf(itemsAtBasePrice));

        return specialPriceTotal.add(basePriceTotal);
    }

    @Override
    public BigDecimal getPrice(BigDecimal weight) {
        throw new UnsupportedOperationException("Bulk price special not valid for weighed items.");
    }
}
