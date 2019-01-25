package com.kpmac.checkout.special;

import com.kpmac.checkout.Item;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DependentPriceSpecial {

    private final BigDecimal priceModifier;
    private final Item primaryItem;
    private final Item dependentItem;
    private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;

    public DependentPriceSpecial(Item dependentItem, Item primaryItem, BigDecimal priceModifier) {
        this.dependentItem = dependentItem;
        this.primaryItem = primaryItem;
        this.priceModifier = priceModifier;
    }

    public Item getPrimaryItem() {
        return primaryItem;
    }

    public BigDecimal getPrice(BigDecimal weight, BigDecimal primaryItemPrice) {
        BigDecimal weightLimit = primaryItemPrice.divide(dependentItem.getPrice(),10, ROUNDING_MODE);

        BigDecimal specialPriceQuantity;
        BigDecimal basePriceQuantity;
        if(weight.compareTo(weightLimit) <= 0 ) {
            specialPriceQuantity = weight;
            basePriceQuantity = BigDecimal.ZERO;
        } else {
            basePriceQuantity = weight.subtract(weightLimit);
            specialPriceQuantity = weightLimit;
        }

        BigDecimal basePriceTotal = basePriceQuantity.multiply(dependentItem.getPrice());
        BigDecimal specialPriceTotal = specialPriceQuantity.multiply(getSpecialPrice());

        return basePriceTotal.add(specialPriceTotal);
    }

    private BigDecimal getSpecialPrice() {
        return dependentItem.getPrice().multiply(priceModifier);
    }
}
