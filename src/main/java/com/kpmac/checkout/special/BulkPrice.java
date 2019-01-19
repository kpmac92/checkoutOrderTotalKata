package com.kpmac.checkout.special;

import com.kpmac.checkout.Item;

import java.math.BigDecimal;

public class BulkPrice implements PriceSpecial{

    private final Item item;
    private final int specialQuantity;
    private final BigDecimal specialPrice;

    public BulkPrice(Item item, int specialQuantity, BigDecimal specialPrice) {
        this.item = item;
        this.specialQuantity = specialQuantity;
        this.specialPrice = specialPrice;
    }

    @Override
    public BigDecimal getPrice(int quantity) {
        int itemsAtBasePrice = quantity % specialQuantity ;
        int itemsAtSpecialPrice = quantity - itemsAtBasePrice;

        return specialPrice.multiply(BigDecimal.valueOf(itemsAtSpecialPrice)
                .add(item.getPrice().multiply(BigDecimal.valueOf(itemsAtBasePrice))));
    }

    @Override
    public BigDecimal getPrice(BigDecimal weight) {
        throw new UnsupportedOperationException("Bulk price special not valid for weighed items.");
    }
}
