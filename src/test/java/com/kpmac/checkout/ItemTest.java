package com.kpmac.checkout;

import com.kpmac.checkout.special.BulkPrice;
import com.kpmac.checkout.special.BuyOneGetOne;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class ItemTest extends CheckoutBaseTest{

    @Test
    public void getPriceWithoutWeightThrowsExceptionIfItemIsPricedByWeight() {
        Item subject = new Item("almonds", getFormattedValue(6.99), true);

        try {
            subject.getPrice(1);
        } catch (RuntimeException e) {
            assertThat(e.getMessage()).isEqualTo("almonds must be weighed to get price.");
            return;
        }
        fail("Expected getPrice to throw runtime exception.");
    }

    @Test
    public void getPriceWithWeightThrowsExceptionIfItemIsNotPricedByWeight() {
        Item subject = new Item("baked beans", getFormattedValue(1.50), false);

        try {
            subject.getPrice(getFormattedValue(2.63));
        } catch (RuntimeException e) {
            assertThat(e.getMessage()).isEqualTo("baked beans is not priced by weight.");
            return;
        }
        fail("Expected getPrice to throw runtime exception.");
    }

    @Test
    public void getPriceWithWeightReturnsPerUnitPriceMultipliedByWeightRoundedToNearestCent(){
        Item subject = new Item("almonds", getFormattedValue(6.99), true);

        assertThat(subject.getPrice(BigDecimal.valueOf(1.18329)))
                .isEqualTo(getFormattedValue(8.27));

        assertThat(subject.getPrice(BigDecimal.valueOf(4.18329)))
                .isEqualTo(getFormattedValue(29.24));
    }

    @Test
    public void getPriceSubtractsMarkdownPriceWhenMarkdownIsSet() {
        Item subject = new Item("baked beans", getFormattedValue(1.50), false);
        subject.setMarkDown(getFormattedValue(.50));

        assertThat(subject.getPrice(1))
                .isEqualTo(getFormattedValue(1.00));

        subject = new Item("almonds", getFormattedValue(6.99), true);
        subject.setMarkDown(getFormattedValue(.99));

        assertThat(subject.getPrice(getFormattedValue(1.17)))
                .isEqualTo(getFormattedValue(7.02));
    }

    @Test
    public void getPriceReturnsCorrectPriceWhenABogoSpecialIsAdded() {
        Item subject = new Item("baked beans", getFormattedValue(1.50), false);

        subject.setPriceSpecial(new BuyOneGetOne(subject, 1, getFormattedValue(0), null));
        assertThat(subject.getPrice(4)).isEqualTo(getFormattedValue(3.00));

        subject.setPriceSpecial(new BuyOneGetOne(subject,3, getFormattedValue(0), null));
        assertThat(subject.getPrice(4)).isEqualTo(getFormattedValue(4.50));
        assertThat(subject.getPrice(5)).isEqualTo(getFormattedValue(6));

        subject.setPriceSpecial(new BuyOneGetOne(subject, 3, getFormattedValue(.50), null));
        assertThat(subject.getPrice(8)).isEqualTo(getFormattedValue(10.5));

        subject.setMarkDown(getFormattedValue(.50));
        assertThat(subject.getPrice(5)).isEqualTo(getFormattedValue(4.50));
    }

    @Test
    public void getPriceReturnsCorrectPriceWhenABulkPriceSpecialIsAdded() {
        Item subject = new Item("baked beans", getFormattedValue(1.50), false);

        subject.setPriceSpecial(new BulkPrice(subject, 5, getFormattedValue(1), null));
        assertThat(subject.getPrice(5)).isEqualTo(getFormattedValue(5));
        assertThat(subject.getPrice(6)).isEqualTo(getFormattedValue(6.50));
        assertThat(subject.getPrice(10)).isEqualTo(getFormattedValue(10));
        assertThat(subject.getPrice(17)).isEqualTo(getFormattedValue(18));

        subject.setMarkDown(getFormattedValue(.25));
        assertThat(subject.getPrice(6)).isEqualTo(getFormattedValue(6.25));
    }

    @Test
    public void getPriceReturnsCorrectPriceWithBogoSpecialAndLimit() {
        Item subject = new Item("baked beans", getFormattedValue(1.50), false);

        subject.setPriceSpecial(new BuyOneGetOne(subject, 5, getFormattedValue(0), 12));
        assertThat(subject.getPrice(18)).isEqualTo(getFormattedValue(24));

        subject.setPriceSpecial(new BuyOneGetOne(subject, 2, getFormattedValue(.50), 6));
        assertThat(subject.getPrice(9)).isEqualTo(getFormattedValue(12));

        subject.setPriceSpecial(new BuyOneGetOne(subject, 1, getFormattedValue(0), 6));
        assertThat(subject.getPrice(8)).isEqualTo(getFormattedValue(7.5));

        subject.setPriceSpecial(new BuyOneGetOne(subject, 1, getFormattedValue(0), 7));
        assertThat(subject.getPrice(8)).isEqualTo(getFormattedValue(7.5));
    }

    @Test
    public void getPriceReturnsCorrectPriceWithBulkSpecialAndLimit() {
        Item subject = new Item("baked beans", getFormattedValue(1.50), false);

        subject.setPriceSpecial(new BulkPrice(subject, 5, getFormattedValue(1), 10));


        assertThat(subject.getPrice(15)).isEqualTo(getFormattedValue(17.5));
        assertThat(subject.getPrice(8)).isEqualTo(getFormattedValue(9.5));
        assertThat(subject.getPrice(13)).isEqualTo(getFormattedValue(14.5));

        subject.setPriceSpecial(new BulkPrice(subject, 3, getFormattedValue(1), 8));

        assertThat(subject.getPrice(6)).isEqualTo(getFormattedValue(6));
        assertThat(subject.getPrice(7)).isEqualTo(getFormattedValue(7.5));
        assertThat(subject.getPrice(9)).isEqualTo(getFormattedValue(10.5));
        assertThat(subject.getPrice(10)).isEqualTo(getFormattedValue(12));
    }
}
