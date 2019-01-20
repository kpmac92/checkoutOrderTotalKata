package com.kpmac.checkout;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class ItemCatalogTest {

    private ItemCatalog subject;
    
    @Before
    public void setup() {
        subject = new ItemCatalog();
    }

    @Test
    public void setPriceSetsThePriceForAnItemAndAddsItemToMapIfItDoesNotExist() {
        subject.setPrice("baked beans", getFormattedValue(1.50), false);

        assertThat(subject.getPrice("baked beans", 1)).isEqualTo(getFormattedValue(1.50));
        assertThat(subject.getPrice("baked beans", 2)).isEqualTo(getFormattedValue(3.00));
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
        subject.setPrice("almonds", getFormattedValue(6.99), true);

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
        subject.setPrice("baked beans", getFormattedValue(1.50), false);

        try {
            subject.getPrice("baked beans", getFormattedValue(2.63));
        } catch (RuntimeException e) {
            assertThat(e.getMessage()).isEqualTo("baked beans is not priced by weight.");
            return;
        }
        fail("Expected getPrice to throw runtime exception.");
    }

    @Test
    public void getPriceWithWeightReturnsPerUnitPriceMultipliedByWeightRoundedToNearestCent(){
        subject.setPrice("almonds", getFormattedValue(6.99), true);

        assertThat(subject.getPrice("almonds", BigDecimal.valueOf(1.18329)))
                .isEqualTo(getFormattedValue(8.27));

        assertThat(subject.getPrice("almonds", BigDecimal.valueOf(4.18329)))
                .isEqualTo(getFormattedValue(29.24));
    }

    @Test
    public void getPriceSubtractsMarkdownPriceWhenMarkdownIsSet() {
        subject.setPrice("baked beans", getFormattedValue(1.50), false);
        subject.setMarkdown("baked beans", getFormattedValue(.50));

        assertThat(subject.getPrice("baked beans", 1))
                .isEqualTo(getFormattedValue(1.00));

        subject.setPrice("almonds", getFormattedValue(6.99), true);
        subject.setMarkdown("almonds", getFormattedValue(.99));

        assertThat(subject.getPrice("almonds", getFormattedValue(1.17)))
                .isEqualTo(getFormattedValue(7.02));
    }

    @Test
    public void getPriceReturnsCorrectPriceWhenABogoSpecialIsAdded() {
        subject.setPrice("baked beans", getFormattedValue(1.50), false);
        subject.addBogoSpecial("baked beans", 1, getFormattedValue(0));

        assertThat(subject.getPrice("baked beans", 4)).isEqualTo(getFormattedValue(3.00));

        subject.addBogoSpecial("baked beans", 3, getFormattedValue(0));

        assertThat(subject.getPrice("baked beans", 4)).isEqualTo(getFormattedValue(4.50));

        assertThat(subject.getPrice("baked beans", 5)).isEqualTo(getFormattedValue(6));

        subject.addBogoSpecial("baked beans", 3, getFormattedValue(.50));

        assertThat(subject.getPrice("baked beans", 8)).isEqualTo(getFormattedValue(10.5));

        subject.setMarkdown("baked beans", getFormattedValue(.50));

        assertThat(subject.getPrice("baked beans", 5)).isEqualTo(getFormattedValue(4.50));
    }

    @Test
    public void getPriceReturnsCorrectPriceWhenABulkPriceSpecialIsAdded() {
        subject.setPrice("baked beans", getFormattedValue(1.50), false);

        subject.addBulkSpecial("baked beans", 5, getFormattedValue(1));

        assertThat(subject.getPrice("baked beans", 5)).isEqualTo(getFormattedValue(5));

        assertThat(subject.getPrice("baked beans", 6)).isEqualTo(getFormattedValue(6.50));

        assertThat(subject.getPrice("baked beans", 10)).isEqualTo(getFormattedValue(10));
        
        assertThat(subject.getPrice("baked beans", 17)).isEqualTo(getFormattedValue(18));
        
        subject.setMarkdown("baked beans", getFormattedValue(.25));

        assertThat(subject.getPrice("baked beans", 6)).isEqualTo(getFormattedValue(6.25));
    }

    @Test
    public void getPriceReturnsCorrectPriceWithBogoSpecialAndLimit() {
        subject.setPrice("baked beans", BigDecimal.valueOf(1.50), false);

        subject.addBogoSpecial("baked beans", 5, getFormattedValue(0), 12);

        assertThat(subject.getPrice("baked beans", 18)).isEqualTo(getFormattedValue(24));

        subject.addBogoSpecial("baked beans", 2, getFormattedValue(.50), 6);

        assertThat(subject.getPrice("baked beans", 9)).isEqualTo(getFormattedValue(12));

        subject.addBogoSpecial("baked beans", 1, getFormattedValue(0), 6);

        assertThat(subject.getPrice("baked beans", 8)).isEqualTo(getFormattedValue(7.5));

        subject.addBogoSpecial("baked beans", 1, getFormattedValue(0), 7);

        assertThat(subject.getPrice("baked beans", 8)).isEqualTo(getFormattedValue(7.5));
    }

    @Test
    public void getPriceReturnsCorrectPriceWithBulkSpecialAndLimit() {
        subject.setPrice("baked beans", BigDecimal.valueOf(1.50), false);

        subject.addBulkSpecial("baked beans", 5, getFormattedValue(1), 10);

        assertThat(subject.getPrice("baked beans", 15)).isEqualTo(getFormattedValue(17.5));
        assertThat(subject.getPrice("baked beans", 8)).isEqualTo(getFormattedValue(9.5));
        assertThat(subject.getPrice("baked beans", 13)).isEqualTo(getFormattedValue(14.5));

        subject.addBulkSpecial("baked beans", 3, getFormattedValue(1), 8);

        assertThat(subject.getPrice("baked beans", 6)).isEqualTo(getFormattedValue(6));
        assertThat(subject.getPrice("baked beans", 7)).isEqualTo(getFormattedValue(7.5));
        assertThat(subject.getPrice("baked beans", 9)).isEqualTo(getFormattedValue(10.5));
        assertThat(subject.getPrice("baked beans", 10)).isEqualTo(getFormattedValue(12));
    }

    private BigDecimal getFormattedValue(double value) {
        return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP);
    }
}
