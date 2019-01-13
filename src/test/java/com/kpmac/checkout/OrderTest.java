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
public class OrderTest {

    @Mock
    private ItemCatalog mockItemCatalog;

    private Order subject;

    @Before
    public void setup() {
        subject = new Order(mockItemCatalog);
    }

    @Test
    public void scanningAnItemIncreasesTotal() {
        when(mockItemCatalog.getPrice("baked beans")).thenReturn(BigDecimal.valueOf(1.50));
        when(mockItemCatalog.getPrice("coffee beans")).thenReturn(BigDecimal.valueOf(6.99));

        assertThat(subject.getTotal()).isEqualTo(BigDecimal.valueOf(0));

        subject.scanItem("baked beans");
        assertThat(subject.getTotal()).isEqualTo(BigDecimal.valueOf(1.50));

        subject.scanItem("coffee beans");
        assertThat(subject.getTotal()).isEqualTo(BigDecimal.valueOf(8.49));
    }

    @Test
    public void scanningAWeighedItemIncreasesTotal() {
        when(mockItemCatalog.getPrice("almonds", BigDecimal.valueOf(1.17))).thenReturn(BigDecimal.valueOf(7.23));
        when(mockItemCatalog.getPrice("romaine", BigDecimal.valueOf(2.21))).thenReturn(BigDecimal.valueOf(2.75));

        subject.scanItem("almonds", BigDecimal.valueOf(1.17));
        assertThat(subject.getTotal()).isEqualTo(BigDecimal.valueOf(7.23));

        subject.scanItem("romaine", BigDecimal.valueOf(2.21));
        assertThat(subject.getTotal()).isEqualTo(BigDecimal.valueOf(9.98));
    }
    
}
