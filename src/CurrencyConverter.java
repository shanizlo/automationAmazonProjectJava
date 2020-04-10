import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Map;

public class CurrencyConverter {
    private static final String api = "https://free.currencyconverterapi.com/api/v5/convert?q={FROM}_{TO}&compact=ultra";

    public static double convert(double amount, Currency from, Currency to) throws Exception {
        String requestUrl = api
                .replace("{FROM}", from.toString())
                .replace("{TO}", to.toString());

        String json;
        try {
            json = getJson(new URL(requestUrl));
        } catch (IOException e) {
            throw new Exception("Online conversion failed");
        }

        Gson gson = new Gson();
        Map jsonObject = gson.fromJson(json, Map.class);
        if (jsonObject.isEmpty()) {
            throw new Exception("Received invalid conversion data");
        }

        double ratio = (Double) jsonObject
                .values()
                .iterator()
                .next();

        return ratio * amount;
    }

    private static String getJson(URL url) throws IOException {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();

            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }
}
