import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Exchange {

    public double exchangeCurrency(ExchangeType exchangeType, String currencyCode, int dateInt)
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

        String url = String.format("http://api.nbp.pl/api/exchangerates/rates/%s/%s/%s/?format=json",
                    tableType, currencyCode, dateStr);

        StringBuffer response = getResponse(url);

        System.out.println("Response is:" + response);
        JSONObject myResponse = new JSONObject(response.toString());
        System.out.println("Rate is: " + myResponse.getJSONArray("rates").getJSONObject(0));//.getDouble("mid"));
        return myResponse.getJSONArray("rates").getJSONObject(0).getDouble(type);
    }

    private StringBuffer getResponse(String url) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response;
    }

    private DateTime getDateComputeDaysFromNow(int days) {
        DateTime dateTime = new DateTime();
        DateTime outDateTime;
        if (days >= 0) outDateTime = dateTime.plusDays(days);
        else outDateTime = dateTime.minusDays(Math.abs(days));
        return outDateTime;
    }

}
