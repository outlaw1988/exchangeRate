import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

public class ExchangeTest {

    private Exchange exchange;

    @Before
    public void before() {
        exchange = new Exchange();
    }

    @Test
    public void testResponseTypeMedium() {
        ExchangeType type = ExchangeType.MEDIUM;
        String currencyCode = "EUR";
        int date = 0;
        try {
            assertThat(exchange.exchangeCurrency(type, currencyCode, date)).isInstanceOf(BigDecimal.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testResponseTypeSellingMonthAgo() {
        ExchangeType type = ExchangeType.SELLING;
        String currencyCode = "EUR";
        int date = -30;
        try {
            assertThat(exchange.exchangeCurrency(type, currencyCode, date)).isInstanceOf(BigDecimal.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testResponseTypeBuyingTwoMonthsAgo() {
        ExchangeType type = ExchangeType.BUYING;
        String currencyCode = "EUR";
        int date = -60;
        try {
            assertThat(exchange.exchangeCurrency(type, currencyCode, date)).isInstanceOf(BigDecimal.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testHundredPLNtoCurrency() {
        BigDecimal rate = new BigDecimal(3.14);
        assertThat(exchange.hundredPLNtoCurrency(rate)).isCloseTo(new BigDecimal(31.85),
                within(new BigDecimal(0.01)));
    }

}