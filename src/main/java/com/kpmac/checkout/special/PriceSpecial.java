package com.kpmac.checkout.special;

import java.math.BigDecimal;

public interface PriceSpecial {

    BigDecimal getPrice(int quantity);

    BigDecimal getPrice(BigDecimal weight);
}
