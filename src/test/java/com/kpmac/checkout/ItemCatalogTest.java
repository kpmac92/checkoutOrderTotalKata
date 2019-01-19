package com.kpmac.checkout;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class ItemCatalogTest {

    private ItemCatalog subject;

    private RoundingMode roundingMode = RoundingMode.HALF_UP;

    @Before
    public void setup() {
        subject = new ItemCatalog();
    }

    @Test
    public void setPriceSetsThePriceForAnItemAndAddsItemToMapIfItDoesNotExist() {
        subject.setPrice("baked beans", BigDecimal.valueOf(1.50), false);

        assertThat(subject.getPrice("baked beans", 1))
                .isEqualTo(BigDecimal.valueOf(1.50).setScale(2, roundingMode));
        assertThat(subject.getPrice("baked beans", 2))
                .isEqualTo(BigDecimal.valueOf(3.00).setScale(2, roundingMode));
    }

    @Test
    public void getPriceThrowsRuntimeExceptionIfNoPriceIsSet() {
        try {
            subject.getPrice("baked beans", 1);
        } catch (RuntimeException e) {
            assertThat(e.getMessage()).isEqualTo("Item not found: baked beans");
            return;
        }
        fail("Expected getPrice to throw runtime exception.");
    }

    @Test
    public void getPriceWithoutWeightThrowsExceptionIfItemIsPricedByWeight() {
        subject.setPrice("almonds", BigDecimal.valueOf(6.99), true);

        try {
            subject.getPrice("almonds", 1);
        } catch (RuntimeException e) {
            assertThat(e.getMessage()).isEqualTo("almonds must be weighed to get price.");
            return;
        }
        fail("Expected getPrice to throw runtime exception.");
    }

    @Test
    public void getPriceWithWeightThrowsExceptionIfItemIsNotPricedByWeight() {
        subject.setPrice("baked beans", BigDecimal.valueOf(1.50), false);

        try {
            subject.getPrice("baked beans", BigDecimal.valueOf(2.63));
        } catch (RuntimeException e) {
            assertThat(e.getMessage()).isEqualTo("baked beans is not priced by weight.");
            return;
        }
        fail("Expected getPrice to throw runtime exception.");
    }

    @Test
    public void getPriceWithWeightReturnsPerUnitPriceMultipliedByWeightRoundedToNearestCent(){
        subject.setPrice("almonds", BigDecimal.valueOf(6.99), true);

        assertThat(subject.getPrice("almonds", BigDecimal.valueOf(1.18329)))
                .isEqualTo(BigDecimal.valueOf(8.27));

        assertThat(subject.getPrice("almonds", BigDecimal.valueOf(4.18329)))
                .isEqualTo(BigDecimal.valueOf(29.24));
    }

    @Test
    public void getPriceSubtractsMarkdownPriceWhenMarkdownIsSet() {
        subject.setPrice("baked beans", BigDecimal.valueOf(1.50), false);
        subject.setMarkdown("baked beans", BigDecimal.valueOf(.50));

        assertThat(subject.getPrice("baked beans", 1))
                .isEqualTo(BigDecimal.valueOf(1.00).setScale(2, roundingMode));

        subject.setPrice("almonds", BigDecimal.valueOf(6.99), true);
        subject.setMarkdown("almonds", BigDecimal.valueOf(.99));

        assertThat(subject.getPrice("almonds", BigDecimal.valueOf(1.17)))
                .isEqualTo(BigDecimal.valueOf(7.02));
    }

    @Test
    public void getPriceReturnsCorrectPriceWhenABogoSpecialIsAdded() {
        subject.setPrice("baked beans", BigDecimal.valueOf(1.50), false);
        subject.addBogoSpecial("baked beans", 1, BigDecimal.valueOf(0));

        assertThat(subject.getPrice("baked beans", 4))
                .isEqualTo(BigDecimal.valueOf(3.00).setScale(2, roundingMode));

        subject.addBogoSpecial("baked beans", 3, BigDecimal.valueOf(0));

        assertThat(subject.getPrice("baked beans", 4))
                .isEqualTo(BigDecimal.valueOf(4.50).setScale(2, roundingMode));

        assertThat(subject.getPrice("baked beans", 5))
                .isEqualTo(BigDecimal.valueOf(6).setScale(2, roundingMode));

        subject.addBogoSpecial("baked beans", 3, BigDecimal.valueOf(.50));

        assertThat(subject.getPrice("baked beans", 8))
                .isEqualTo(BigDecimal.valueOf(10.5).setScale(2, roundingMode));

        subject.setMarkdown("baked beans", BigDecimal.valueOf(.50));

        assertThat(subject.getPrice("baked beans", 5))
                .isEqualTo(BigDecimal.valueOf(4.50).setScale(2, roundingMode));
    }
}
