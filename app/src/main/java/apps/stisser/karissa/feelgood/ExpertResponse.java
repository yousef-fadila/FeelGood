package apps.stisser.karissa.feelgood;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

/**
 * Created by User on 3/5/2017.
 */
public class ExpertResponse extends ActionBarActivity {
    public static List<Message> messageHistory;
    public static ListView messages;
    public static EditText text;
    public static String[] messageStrings;
    public static EditText id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expert_respond);

        messages = (ListView)findViewById(R.id.listView);
        text = (EditText)findViewById(R.id.answerTxt);
        messageHistory = MessagesBroker.getMessages(CountrySelection.getSelectedTopic(), null);
        messageStrings = new String[messageHistory.size()];
        id = (EditText)findViewById(R.id.IDEdit);
        int count = 0;
        for(Message m: messageHistory){
            messageStrings[count] = m.toString();
        }
        messages.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, messageStrings));
    }

    public void answerQuestionClicked(){
        String answer = text.getText().toString();
        String user = FeelGood.getLoginName();
        String topic = "Ask the Expert";
        String question = MessagesBroker.getQuestionByID(Integer.valueOf(id.toString()));
        Message m = new Message(user, question, topic, answer);
        MessagesBroker.submitAnswer(m);
    }
}
