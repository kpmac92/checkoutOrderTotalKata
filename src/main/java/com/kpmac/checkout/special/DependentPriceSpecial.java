package com.kpmac.checkout.special;

import com.kpmac.checkout.Item;

import java.math.BigDecimal;

public class DependentPriceSpecial {

    private final BigDecimal priceModifier;
    private final Item primaryItem;
    private final Item dependentItem;

    public DependentPriceSpecial(Item dependentItem, Item primaryItem, BigDecimal priceModifier) {
        this.dependentItem = dependentItem;
        this.primaryItem = primaryItem;
        this.priceModifier = priceModifier;
    }

    public Item getPrimaryItem() {
        return primaryItem;
    }

}
