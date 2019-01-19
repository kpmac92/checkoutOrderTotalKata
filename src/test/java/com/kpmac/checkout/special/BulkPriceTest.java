package com.kpmac.checkout.special;

import com.kpmac.checkout.Item;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class BulkPriceTest {

    @Test
    public void getPriceThrowsUnsupportedOperationExceptionWhenCalledWithWeight() {
        BulkPrice subject = new BulkPrice(new Item("item", BigDecimal.valueOf(1), true), 0, BigDecimal.valueOf(1));

        try{
            subject.getPrice(BigDecimal.valueOf(1.00));
        } catch (UnsupportedOperationException e) {
            assertThat(e.getMessage()).isEqualTo("Bulk price special not valid for weighed items.");
            return;
        }
        fail("Expected UnsupportedOperationException.");
    }
}
