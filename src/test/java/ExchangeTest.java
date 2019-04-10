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
    public void testResponseTableA() {
        String exchangeType = "medium";
        String currencyCode = "EUR";
        String date = "2019-04-10";
        try {
            System.out.println(exchange.exchangeCurrency(exchangeType, currencyCode, date));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}