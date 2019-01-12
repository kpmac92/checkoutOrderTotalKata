package com.kpmac.checkout;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

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
        when(mockItemCatalog.getPrice("baked beans")).thenReturn(1.50);
        when(mockItemCatalog.getPrice("coffee beans")).thenReturn(6.99);

        assertThat(subject.getTotal()).isEqualTo(0);

        subject.scanItem("baked beans");
        assertThat(subject.getTotal()).isEqualTo(1.50);

        subject.scanItem("coffee beans");
        assertThat(subject.getTotal()).isEqualTo(8.49);
    }

    @Test
    public void scanningAWeighedItemIncreasesTotal() {

    }
    
}
