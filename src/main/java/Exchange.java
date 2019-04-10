import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Exchange {

    public double exchangeCurrency(String exchangeType, String currencyCode, String date)
            throws IOException {
        String tableType = "";
        String type = "";

        if (exchangeType.equals("medium")) {
            tableType = "a";
            type = "mid";
        } else if (exchangeType.equals("selling")) {
            tableType = "c";
            type = "ask";
        } else if (exchangeType.equals("buying")) {
            tableType = "c";
            type = "bid";
        }

        String url = String.format("http://api.nbp.pl/api/exchangerates/rates/%s/%s/%s/?format=json",
                    tableType, currencyCode, date);

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        // optional default is GET
        con.setRequestMethod("GET");
        //add request header
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        System.out.println("Response is:" + response);

        JSONObject myResponse = new JSONObject(response.toString());

        System.out.println("Rate is: " + myResponse.getJSONArray("rates").getJSONObject(0));//.getDouble("mid"));
       // return myResponse.getString("value");
        //return myResponse.getJSONArray("rates").getJSONObject(0)
        return myResponse.getJSONArray("rates").getJSONObject(0).getDouble(type);
    }

}
