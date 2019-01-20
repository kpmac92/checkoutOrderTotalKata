package com.kpmac.checkout;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CheckoutBaseTest {

    protected BigDecimal getFormattedValue(double value) {
        return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP);
    }
}
