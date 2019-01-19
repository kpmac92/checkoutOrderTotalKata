package com.kpmac.checkout.special;

import com.kpmac.checkout.Item;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class BuyOneGetOneTest {

    @Test
    public void getPriceThrowsUnsupportedOperationExceptionWhenCalledWithWeight() {
        BuyOneGetOne subject = new BuyOneGetOne(new Item("item", BigDecimal.valueOf(1)
                , true), 0, BigDecimal.valueOf(1), 1);

        try{
            subject.getPrice(BigDecimal.valueOf(1.00));
        } catch (UnsupportedOperationException e) {
            assertThat(e.getMessage()).isEqualTo("Buy One Get One special not valid for weighed items.");
            return;
        }
        fail("Expected UnsupportedOperationException.");
    }
}
