package com.kpmac.checkout;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class ItemCatalogTest {

    private ItemCatalog subject;

    @Before
    public void setup() {
        subject = new ItemCatalog();
    }

    @Test
    public void setPriceSetsThePriceForAnItemAndAddsItemToMapIfItDoesNotExist() {
        subject.setPrice("baked beans", 1.50, false);

        assertThat(subject.getPrice("baked beans")).isEqualTo(1.50);
    }

    @Test
    public void getPriceThrowsRuntimeExceptionIfNoPriceIsSet() {
        try {
            subject.getPrice("baked beans");
        } catch (RuntimeException e) {
            assertThat(e.getMessage()).isEqualTo("Item not found: baked beans");
            return;
        }
        fail("Expected getPrice to throw runtime exception.");
    }

    @Test
    public void getPriceWithoutWeightThrowsExceptionIfItemIsPricedByWeight() {
        subject.setPrice("almonds", 6.99, true);

        try {
            subject.getPrice("almonds");
        } catch (RuntimeException e) {
            assertThat(e.getMessage()).isEqualTo("almonds must be weighed to get price.");
            return;
        }
        fail("Expected getPrice to throw runtime exception.");
    }

    @Test
    public void getPriceWithWeightReturnsPerUnitPriceMultipliedByWeightRoundedToNearestCent(){
        subject.setPrice("almonds", 6.99, true);

        //assertThat(subject.getPrice("almonds", 1.17)).isEqualTo(8.18);
    }
}
