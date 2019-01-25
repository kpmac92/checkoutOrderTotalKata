package com.kpmac.checkout;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class CheckoutIntegrationTest extends CheckoutBaseTest {

    private ItemCatalog itemCatalog;

    private Order order;

    @Before
    public void setup() {
        itemCatalog = new ItemCatalog(
                new Item("baked beans", getFormattedValue(1.50), false),
                new Item("americone dream", getFormattedValue(5.99), false),
                new Item("almonds", getFormattedValue(6.99), true),
                new Item("shampoo", getFormattedValue(7.99), false),
                new Item("yogurt", getFormattedValue(1), false),
                new Item("romaine", getFormattedValue(1.14), true));

        order = new Order(itemCatalog);
    }

    @Test
    public void scanItemCorrectlyCalculatesTotalWhenItemIsMarkedDown() {
        itemCatalog.setMarkdown("baked beans", getFormattedValue(.50));
        itemCatalog.setMarkdown("almonds", getFormattedValue(.99));


        order.scanItem("americone dream");
        scanItem("baked beans", 2);
        order.scanItem("almonds", BigDecimal.valueOf(1.17));
        order.scanItem("almonds", BigDecimal.valueOf(1.29));

        assertThat(order.getTotal()).isEqualTo(getFormattedValue(22.75));
    }

    @Test
    public void scanItemCorrectlyCalculatesTotalWithPriceSpecials() {
        itemCatalog.setMarkdown("yogurt", getFormattedValue(.25));
        itemCatalog.addBulkSpecial("yogurt", 5, getFormattedValue(.50), 11);
        itemCatalog.addBogoSpecial("baked beans", 3, getFormattedValue(0), 9);

        scanItem("yogurt", 10);
        assertThat(order.getTotal()).isEqualTo(getFormattedValue(5));
        order.removeItem("yogurt");
        assertThat(order.getTotal()).isEqualTo(getFormattedValue(5.5));

        scanItem("baked beans", 8);
        assertThat(order.getTotal()).isEqualTo(getFormattedValue(14.5));
        order.removeItem("baked beans");
        assertThat(order.getTotal()).isEqualTo(getFormattedValue(14.5));

        itemCatalog.setMarkdown("almonds", getFormattedValue(.99));
        order.scanItem("almonds", BigDecimal.valueOf(2.13));
        assertThat(order.getTotal()).isEqualTo(getFormattedValue(27.28));

        order.scanItem("romaine", BigDecimal.valueOf(3.2));
        assertThat(order.getTotal()).isEqualTo(getFormattedValue(30.93));

        order.removeItem("almonds");
        assertThat(order.getTotal()).isEqualTo(getFormattedValue(18.15));

        order.removeItem("yogurt");
        assertThat(order.getTotal()).isEqualTo(getFormattedValue(17.4));
    }

    @Test
    public void getTotalCorrectlyCalculatesTotalWithDependentPriceSpecial() {
        itemCatalog.setPrice("peanuts", getFormattedValue(5), true);
        itemCatalog.addDependentPriceSpecial("peanuts", "almonds", getFormattedValue(.50));

        order.scanItem("peanuts", BigDecimal.valueOf(3.19));
        assertThat(order.getTotal()).isEqualTo(getFormattedValue(15.95));

        order.scanItem("almonds", BigDecimal.valueOf(1.183));
        assertThat(order.getTotal()).isEqualTo(getFormattedValue(20.09));

        order.removeItem("almonds");
        assertThat(order.getTotal()).isEqualTo(getFormattedValue(15.95));

    }

    @Test
    public void getTotalCorrectlyCalculatesTotalWithUnlimitedBogoSpecial() {
        itemCatalog.addBogoSpecial("baked beans", 3, getFormattedValue(0));

        scanItem("baked beans", 16);
        assertThat(order.getTotal()).isEqualTo(getFormattedValue(18));
    }

    @Test
    public void getTotalCorrectlyCalculatesTotalWithUnlimitedBulkSpecial() {
        itemCatalog.addBulkSpecial("yogurt", 5, getFormattedValue(.50));

        scanItem("yogurt", 23);
        assertThat(order.getTotal()).isEqualTo(getFormattedValue(13));
    }

    private void scanItem(String itemName, int count) {
        for(int i = 0; i < count; i++) {
            order.scanItem(itemName);
        }
    }
}
