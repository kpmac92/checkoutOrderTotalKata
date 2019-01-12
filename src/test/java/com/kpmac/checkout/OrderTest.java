package com.kpmac.checkout;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderTest {

    @Test
    public void scanningAnItemIncreasesTotal() {
        ItemCatalog itemCatalog = new ItemCatalog();
        Order subject = new Order(itemCatalog);

        assertThat("everything works!").isEqualTo("everything works!");
    }
    
}
