package com.kpmac.checkout.special;

import com.kpmac.checkout.Item;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BuyOneGetOne implements PriceSpecial{

    private final BigDecimal priceModifier;
    private final int specialQuantity;
    private final Item item;

    public BuyOneGetOne(Item item, int specialQuantity, BigDecimal priceModifier) {
        this.item = item;
        this.specialQuantity = specialQuantity;
        this.priceModifier = priceModifier;
    }

    @Override
    public BigDecimal getPrice(int quantity) {
        int markDownPriceCount;

        if(specialQuantity == 1){
            markDownPriceCount = quantity / 2;
        } else {
            markDownPriceCount = quantity / specialQuantity;
        }
        int basePriceCount = quantity - markDownPriceCount;

        return BigDecimal.valueOf(basePriceCount).multiply(item.getPrice())
                .add(BigDecimal.valueOf(markDownPriceCount).multiply(getSpecialPrice()));
    }

    @Override
    public BigDecimal getPrice(BigDecimal weight) {
        throw new UnsupportedOperationException("Buy One Get One special not valid for weighed items.");
    }

    private BigDecimal getSpecialPrice() {
        return item.getPrice().multiply(priceModifier).setScale(2, RoundingMode.HALF_UP);
    }
}
