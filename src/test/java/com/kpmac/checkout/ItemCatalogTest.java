package com.kpmac.checkout;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ItemCatalogTest {

    @Test
    public void setPriceSetsThePriceForAnItemAndAddsItemToMapIfItDoesNotExist() {
        ItemCatalog subject = new ItemCatalog();

        subject.setPrice("baked beans", 1.50);

        assertThat(subject.getPrice("baked beans")).isEqualTo(1.50);
    }

}
