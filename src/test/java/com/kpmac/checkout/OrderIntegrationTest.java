package com.kpmac.checkout;

import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderIntegrationTest {

    @Test
    public void scanItemCorrectlyCalculatesTotalWhenItemIsMarkedDown() {
        ItemCatalog itemCatalog = new ItemCatalog(
                new Item("baked beans", BigDecimal.valueOf(1.50), false),
                new Item("americone dream", BigDecimal.valueOf(5.99), false),
                new Item("almonds", BigDecimal.valueOf(6.99), true));
        itemCatalog.setMarkdown("baked beans", BigDecimal.valueOf(.50));
        itemCatalog.setMarkdown("almonds", BigDecimal.valueOf(.99));

        Order subject = new Order(itemCatalog);
        subject.scanItem("americone dream");
        subject.scanItem("baked beans");
        subject.scanItem("almonds", BigDecimal.valueOf(1.17));

        assertThat(subject.getTotal()).isEqualTo(BigDecimal.valueOf(14.01));
    }
}
