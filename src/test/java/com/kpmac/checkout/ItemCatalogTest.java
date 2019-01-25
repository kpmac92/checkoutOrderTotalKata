package com.kpmac.checkout;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class ItemCatalogTest extends CheckoutBaseTest{

    private ItemCatalog subject;
    
    @Before
    public void setup() {
        subject = new ItemCatalog();
    }

    @Test
    public void setPriceSetsThePriceForAnItemAndAddsItemToMapIfItDoesNotExist() {
        subject.setPrice("baked beans", getFormattedValue(1.50), false);
        Item bakedBeans = subject.getItem("baked beans");

        assertThat(bakedBeans.getPrice(1)).isEqualTo(getFormattedValue(1.50));
        assertThat(bakedBeans.getPrice(2)).isEqualTo(getFormattedValue(3.00));
    }

    @Test
    public void getItemThrowsRuntimeExceptionIfNoPriceIsSet() {
        try {
            subject.getItem("baked beans");
        } catch (RuntimeException e) {
            assertThat(e.getMessage()).isEqualTo("Item not found: baked beans");
            return;
        }
        fail("Expected getItem to throw runtime exception.");
    }
}
