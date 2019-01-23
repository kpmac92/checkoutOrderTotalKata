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

    @Mock
    private Item mockItem1;

    @Mock
    private Item mockItem2;

    private Order subject;

    @Before
    public void setup() {
        subject = new Order(mockItemCatalog);
    }

    @Test
    public void scanningAnItemIncreasesTotal() {
        when(mockItemCatalog.getItem("baked beans")).thenReturn(mockItem1);
        when(mockItem1.getPrice(1)).thenReturn(getFormattedValue(1.50));
        when(mockItemCatalog.getItem("coffee beans")).thenReturn(mockItem2);
        when(mockItem2.getPrice(1)).thenReturn(getFormattedValue(6.99));

        assertThat(subject.getTotal()).isEqualTo(getFormattedValue(0));

        subject.scanItem("baked beans");
        assertThat(subject.getTotal()).isEqualTo(getFormattedValue(1.50));

        subject.scanItem("coffee beans");
        assertThat(subject.getTotal()).isEqualTo(getFormattedValue(8.49));
    }

    @Test
    public void scanningAWeighedItemIncreasesTotal() {
        when(mockItemCatalog.getItem("almonds")).thenReturn(mockItem1);
        when(mockItem1.getPrice(BigDecimal.valueOf(1.17))).thenReturn(getFormattedValue(7.23));
        when(mockItemCatalog.getItem("romaine")).thenReturn(mockItem2);
        when(mockItem2.getPrice(BigDecimal.valueOf(2.21))).thenReturn(getFormattedValue(2.75));

        subject.scanItem("almonds", BigDecimal.valueOf(1.17));
        assertThat(subject.getTotal()).isEqualTo(getFormattedValue(7.23));

        subject.scanItem("romaine", BigDecimal.valueOf(2.21));
        assertThat(subject.getTotal()).isEqualTo(getFormattedValue(9.98));
    }

    @Test
    public void removingScannedItemReducesTotal() {
        when(mockItemCatalog.getItem("baked beans")).thenReturn(mockItem1);
        when(mockItem1.getPrice(1)).thenReturn(getFormattedValue(1.50));
        when(mockItem1.getPrice(2)).thenReturn(getFormattedValue(3));
        when(mockItemCatalog.getItem("almonds")).thenReturn(mockItem2);
        when(mockItem2.getPrice(BigDecimal.valueOf(1.17))).thenReturn(getFormattedValue(7.23));

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
