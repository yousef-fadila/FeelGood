package apps.stisser.karissa.feelgood;

/**
 * Created by yfadila on 3/4/2017.
 */

import android.os.Handler;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessagesBroker {

    public interface MessageCallBack {
        void onMessage(Message message);
    }

    public static Map<String, MessageCallBack> callBackMap = new HashMap<>();

    public static List<Message> getMessages(String chatRoom, MessageCallBack callBack){
        if (callBack !=null) {
            callBackMap.put(chatRoom, callBack);
        }
        List<Message> msgs = new ArrayList<>();
        for(int i=0; i< allMessages.size(); i++) {
            //(int id, String time, String from, String content, String topic)
            if (allMessages.get(i).getAsJsonArray().get(2).toString().replace("\"","").equals(chatRoom)) {
                Message e = new Message(allMessages.get(i).getAsJsonArray());
                msgs.add(e);
            }
        }
        return  msgs;
    }

    private static int lastId = 0;
    private static final String BASE_URL = "http://fadila.net/feelgood/index.php/messages";
    private static Gson gson = new Gson();
    private static JsonArray allMessages = new JsonArray();

    public static JsonArray fetchMessages() {
        JsonArray records = null;

        try {
            URL url = new URL(BASE_URL + "?filter=id,gt," + lastId);
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
    // bad practice, This is for 1st POC stage only.
    public static void addMessage(Message msg) {

        try {
            URL object = new URL(BASE_URL);
            HttpURLConnection con = (HttpURLConnection) object.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestMethod("POST");

            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
            wr.write(msg.getStringToPost());
            wr.flush();
            int HttpResult = con.getResponseCode();
            if (HttpResult != HttpURLConnection.HTTP_OK) {
                System.out.println(con.getResponseMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void pollMessages(){
        final Handler h = new Handler();
        final int delay = 2000; //milliseconds

        h.postDelayed(new Runnable(){
            public void run(){
                JsonArray newMessgaes = fetchMessages();
                if (newMessgaes.size() != 0) {
                    allMessages.addAll(newMessgaes);
                    for (int i=0; i<newMessgaes.size(); i++) {
                        String chatroom = newMessgaes.get(i).getAsJsonArray().get(2).toString();
                        if (callBackMap.get(chatroom) != null)
                            callBackMap.get(chatroom).onMessage(new Message(newMessgaes.get(i).getAsJsonArray()));
                    }
                }
                h.postDelayed(this, delay);
            }
        }, delay);
    }

    public static void main(String[] args) throws InterruptedException {
        addMessage(new Message(943,"","yousef","slama","lilya"));

    }
}
