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
        subject.setPrice("baked beans", 1.50);

        assertThat(subject.getPrice("baked beans")).isEqualTo(1.50);
    }

    @Test
    public void getPriceThrowsRuntimeExceptionIfNoPriceIsSet() {
        try {
            subject.getPrice("baked beans");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(RuntimeException.class);
            assertThat(e.getMessage()).isEqualTo("Item not found: baked beans");
            return;
        }
        fail("Expected getPrice to throw exception.");
    }
}
