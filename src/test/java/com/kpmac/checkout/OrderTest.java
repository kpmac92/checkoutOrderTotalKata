package com.kpmac.checkout;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderTest {

    @Test
    public void scanningAnItemIncreasesTotal() {
        ItemCatalog itemCatalog = new ItemCatalog();
        itemCatalog.setPrice("baked beans", 1.50);
        itemCatalog.setPrice("coffee beans", 6.99);

        Order subject = new Order(itemCatalog);
        assertThat(subject.getTotal()).isEqualTo(0);

        subject.scanItem("baked beans");
        assertThat(subject.getTotal()).isEqualTo(1.50);

        subject.scanItem("coffee beans");
        assertThat(subject.getTotal()).isEqualTo(8.49);
    }
    
}
