package apps.stisser.karissa.feelgood;

/**
 * Created by yfadila on 3/4/2017.
 */

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MessagesBroker {
    private static int lastId = 0;
    private static final String BASE_URL = "http://fadila.net/feelgood/index.php/messages?filter=id,gt,";
    private static Gson gson = new Gson();

    public static JsonArray test() {
        JsonArray records = null;

        try {
            URL url = new URL(BASE_URL + lastId);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output = br.readLine();
            JsonElement jelem = gson.fromJson(output, JsonElement.class);
            records = jelem.getAsJsonObject().getAsJsonObject("messages").getAsJsonArray("records");
//            System.out.println("string: " + records);
//            System.out.println("string.size " + records.size());

            if (records.size() > 0)
            {
             lastId = Integer.parseInt(records.get(records.size() - 1).getAsJsonArray().get(0).toString());
            }

            conn.disconnect();
        } catch (MalformedURLException e) {

            e.printStackTrace();
            

        } catch (IOException e) {

            e.printStackTrace();
        }
        return records;
    }

    public static void main(String[] args) {
        test();
        System.out.println(lastId);
    }
}
