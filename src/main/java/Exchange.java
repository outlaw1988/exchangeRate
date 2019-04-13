import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;

public class Exchange {

    private final String url = "http://api.nbp.pl/api/exchangerates/rates/%s/%s/%s/?format=json";

    public Exchange() {
        // empty
    }

    public BigDecimal exchangeCurrency(ExchangeType exchangeType, String currencyCode, int dateInt)
            throws IOException {
        String dateStr = DateTimeFormat.forPattern("yyyy-MM-dd").print(getDateComputeDaysFromNow(dateInt));

        String tableType = "";
        String type = "";

        if (exchangeType.equals(ExchangeType.MEDIUM)) {
            tableType = "a";
            type = "mid";
        } else if (exchangeType.equals(ExchangeType.SELLING)) {
            tableType = "c";
            type = "ask";
        } else if (exchangeType.equals(ExchangeType.BUYING)) {
            tableType = "c";
            type = "bid";
        }

        StringBuffer response = getResponse(String.format(url, tableType, currencyCode, dateStr));
        JSONObject myResponse = new JSONObject(response.toString());

        //System.out.println("Response: " + myResponse);

        return new BigDecimal(myResponse.getJSONArray("rates").getJSONObject(0).getDouble(type));
    }

    private StringBuffer getResponse(String url) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response;
    }

    public BigDecimal hundredPLNtoCurrency(BigDecimal rate) {
        return new BigDecimal(100).divide(rate, 2, BigDecimal.ROUND_HALF_UP);
    }

    private DateTime getDateComputeDaysFromNow(int days) {
        DateTime dateTime = new DateTime();
        DateTime outDateTime;
        if (days >= 0) outDateTime = dateTime.plusDays(days);
        else outDateTime = dateTime.minusDays(Math.abs(days));

        // Stock market does not work on Saturdays and Sundays
        if (outDateTime.getDayOfWeek() == 6) outDateTime = outDateTime.minusDays(1);
        if (outDateTime.getDayOfWeek() == 7) outDateTime = outDateTime.minusDays(2);

        return outDateTime;
    }

}
