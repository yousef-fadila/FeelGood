package apps.stisser.karissa.feelgood;

import android.support.annotation.NonNull;

import com.google.gson.JsonArray;

/**
 * Created by yfadila on 3/4/2017.
 */

public class Message implements Comparable<Message>{
    public int id;
    public String time;
    public String from;
    public String content;
    public String topic;

    public Message(int id, String time, String from, String content, String topic) {
        this.id = id;
        this.time = time;
        this.from = from;
        this.content = content;
        this.topic = topic;
        System.out.println(this.content);
    }

    public Message(String from, String content, String topic){
        this.from = from;
        this.content = content;
        this.topic = topic;
    }

    public Message(JsonArray jsonArray) {
        this(Integer.parseInt(jsonArray.get(0).toString()), jsonArray.get(1).toString().replace("\"",""),  jsonArray.get(3).toString().replace("\"",""),
                jsonArray.get(4).toString().replace("\"",""),    jsonArray.get(2).toString().replace("\"",""));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        return id == message.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public int compareTo(@NonNull Message message) {
        return this.id - message.id;
    }
}
