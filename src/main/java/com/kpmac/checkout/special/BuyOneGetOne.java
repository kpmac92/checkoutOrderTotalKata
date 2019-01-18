package com.kpmac.checkout.special;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BuyOneGetOne implements PriceSpecial{

    private final BigDecimal specialPrice;
    private final int specialQuantity;
    private final BigDecimal basePrice;

    public BuyOneGetOne(BigDecimal basePrice, int specialQuantity, BigDecimal priceModifier) {
        this.basePrice = basePrice;
        this.specialQuantity = specialQuantity;
        this.specialPrice = basePrice.multiply(priceModifier).setScale(2, RoundingMode.HALF_UP);
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

        return BigDecimal.valueOf(basePriceCount).multiply(basePrice)
                .add(BigDecimal.valueOf(markDownPriceCount).multiply(specialPrice));
    }

    @Override
    public BigDecimal getPrice(BigDecimal weight) {
        throw new UnsupportedOperationException("Buy One Get One special not valid for weighed items.");
    }

}
