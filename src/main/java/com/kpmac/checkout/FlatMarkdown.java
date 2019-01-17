package com.kpmac.checkout;

import java.math.BigDecimal;

public class FlatMarkdown implements PriceSpecial {

    private final BigDecimal markDownPrice;

    public FlatMarkdown(BigDecimal basePrice, BigDecimal markDown) {
        this.markDownPrice = basePrice.subtract(markDown);
    }

    @Override
    public BigDecimal getPrice(int quantity) {
        return getPrice(BigDecimal.valueOf(quantity));
    }

    @Override
    public BigDecimal getPrice(BigDecimal weight) {
        return weight.multiply(markDownPrice);
    }
}
