package com.kpmac.checkout;

import java.math.BigDecimal;

public interface PriceSpecial {

    BigDecimal getPrice(int quantity);

    BigDecimal getPrice(BigDecimal weight);
}
