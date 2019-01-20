package com.kpmac.checkout;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderTest extends CheckoutBaseTest{

    @Mock
    private ItemCatalog mockItemCatalog;

    private Order subject;

    @Before
    public void setup() {
        subject = new Order(mockItemCatalog);
    }

    @Test
    public void scanningAnItemIncreasesTotal() {
        when(mockItemCatalog.getPrice("baked beans", 1)).thenReturn(getFormattedValue(1.50));
        when(mockItemCatalog.getPrice("coffee beans", 1)).thenReturn(getFormattedValue(6.99));

        assertThat(subject.getTotal()).isEqualTo(getFormattedValue(0));

        subject.scanItem("baked beans");
        assertThat(subject.getTotal()).isEqualTo(getFormattedValue(1.50));

        subject.scanItem("coffee beans");
        assertThat(subject.getTotal()).isEqualTo(getFormattedValue(8.49));
    }

    @Test
    public void scanningAWeighedItemIncreasesTotal() {
        when(mockItemCatalog.getPrice("almonds", BigDecimal.valueOf(1.17))).thenReturn(getFormattedValue(7.23));
        when(mockItemCatalog.getPrice("romaine", BigDecimal.valueOf(2.21))).thenReturn(getFormattedValue(2.75));

        subject.scanItem("almonds", BigDecimal.valueOf(1.17));
        assertThat(subject.getTotal()).isEqualTo(getFormattedValue(7.23));

        subject.scanItem("romaine", BigDecimal.valueOf(2.21));
        assertThat(subject.getTotal()).isEqualTo(getFormattedValue(9.98));
    }

    @Test
    public void removingScannedItemReducesTotal() {
        when(mockItemCatalog.getPrice("baked beans", 1)).thenReturn(getFormattedValue(1.50));
        when(mockItemCatalog.getPrice("baked beans", 2)).thenReturn(getFormattedValue(3));
        when(mockItemCatalog.getPrice("almonds", BigDecimal.valueOf(1.17))).thenReturn(getFormattedValue(7.23));

        subject.scanItem("baked beans");
        assertThat(subject.getTotal()).isEqualTo(getFormattedValue(1.50));

        subject.scanItem("baked beans");
        assertThat(subject.getTotal()).isEqualTo(getFormattedValue(3));

        subject.scanItem("almonds", BigDecimal.valueOf(1.17));
        assertThat(subject.getTotal()).isEqualTo(getFormattedValue(10.23));

        subject.removeItem("baked beans");
        assertThat(subject.getTotal()).isEqualTo(getFormattedValue(8.73));

        subject.removeItem("almonds");
        assertThat(subject.getTotal()).isEqualTo(getFormattedValue(1.50));

        subject.removeItem("baked beans");
        assertThat(subject.getTotal()).isEqualTo(getFormattedValue(0));

        subject.scanItem("baked beans");
        assertThat(subject.getTotal()).isEqualTo(getFormattedValue(1.50));
    }
}
