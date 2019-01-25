package com.kpmac.checkout.special;

import com.kpmac.checkout.Item;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BuyOneGetOne implements PriceSpecial{

    private final BigDecimal priceModifier;
    private final int specialQuantity;
    private final Item item;
    private final Integer limit;

    public BuyOneGetOne(Item item, int specialQuantity, BigDecimal priceModifier, Integer limit) {
        this.item = item;
        this.specialQuantity = specialQuantity;
        this.priceModifier = priceModifier;
        this.limit = limit;
    }

    @Override
    public BigDecimal getPrice(int quantity) {
        int markDownPriceCount;

        if(limit == null || quantity < limit) {
            markDownPriceCount = quantity / (specialQuantity + 1);
        } else {
            markDownPriceCount = limit / (specialQuantity + 1);
        }

        int basePriceCount = quantity - markDownPriceCount;

        BigDecimal basePriceTotal = BigDecimal.valueOf(basePriceCount).multiply(item.getPrice());
        BigDecimal specialPriceTotal = BigDecimal.valueOf(markDownPriceCount).multiply(getSpecialPrice());
        return basePriceTotal.add(specialPriceTotal);
    }

    private BigDecimal getSpecialPrice() {
        return item.getPrice().multiply(priceModifier).setScale(2, RoundingMode.HALF_UP);
    }
}
