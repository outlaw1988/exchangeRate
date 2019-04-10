import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

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
            assertThat(exchange.exchangeCurrency(type, currencyCode, date)).isInstanceOf(Double.class);
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
            assertThat(exchange.exchangeCurrency(type, currencyCode, date)).isInstanceOf(Double.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testResponseTypeBuyingTwoMonthsAgo() {
        ExchangeType type = ExchangeType.BUYING;
        String currencyCode = "EUR";
        int date = -31;
        try {
            assertThat(exchange.exchangeCurrency(type, currencyCode, date)).isInstanceOf(Double.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}