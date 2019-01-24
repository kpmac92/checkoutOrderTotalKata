package com.kpmac.checkout;

import com.kpmac.checkout.special.DependentPriceSpecial;
import com.kpmac.checkout.special.PriceSpecial;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Item {

    private final Boolean pricedByWeight;
    private final String name;
    private final BigDecimal price;
    private BigDecimal markDown;
    private PriceSpecial priceSpecial;
    private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;
    private DependentPriceSpecial dependentPriceSpecial;

    public Item(String name, BigDecimal price, Boolean pricedByWeight) {
        this.name = name;
        this.price = price;
        this.pricedByWeight = pricedByWeight;
    }

    public BigDecimal getPrice(int itemCount) {
        if(pricedByWeight) {
            throw new RuntimeException(name + " must be weighed to get price.");
        }

        BigDecimal price = priceSpecial == null ? calculatePriceWithoutSpecial(itemCount)
                : priceSpecial.getPrice(itemCount);

        return price.setScale(2, ROUNDING_MODE);
    }

    public BigDecimal getPrice(BigDecimal weight) {
        if(!pricedByWeight) {
            throw new RuntimeException(name + " is not priced by weight.");
        }

        BigDecimal price = priceSpecial == null ? calculatePriceWithoutSpecial(weight)
                : priceSpecial.getPrice(weight);

        return price.setScale(2, ROUNDING_MODE);
    }

    public BigDecimal getPrice(BigDecimal weight, BigDecimal primaryItemPrice) {
        return null;
    }

    private BigDecimal calculatePriceWithoutSpecial(int itemCount) {
        return getPrice().multiply(BigDecimal.valueOf(itemCount));
    }

    private BigDecimal calculatePriceWithoutSpecial(BigDecimal weight) {
        return getPrice().multiply(weight);
    }

    public BigDecimal getPrice() {
        return markDown == null ? price : price.subtract(markDown);
    }

    public void setMarkDown(BigDecimal markDown) {
        this.markDown = markDown;
    }

    public String getName() {
        return name;
    }

    public void setPriceSpecial(PriceSpecial priceSpecial) {
        this.priceSpecial = priceSpecial;
    }

    public Boolean isPricedByWeight() {
        return pricedByWeight;
    }

    public DependentPriceSpecial getDependentPriceSpecial() {
        return dependentPriceSpecial;
    }
}
