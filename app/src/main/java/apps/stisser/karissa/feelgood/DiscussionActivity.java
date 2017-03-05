package apps.stisser.karissa.feelgood;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleExpandableListAdapter;

import java.util.List;

/**
 * Created by User on 3/5/2017.
 */
public class DiscussionActivity extends ActionBarActivity {
    ListView messages;
    EditText text;
    List<Message> messageHistory;
    String[] messageStrings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.discussion);
        messages = (ListView)findViewById(R.id.messageList);
        text = (EditText)findViewById(R.id.editText);
        messageHistory = MessagesBroker.getMessages(CountrySelection.getSelectedTopic(), null);
        messageStrings = new String[messageHistory.size()];
        int count = 0;
        for(Message m: messageHistory){
            messageStrings[count] = m.toString();
        }
        messages.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, messageStrings));
    }

    public void postClicked(View view){
        String username = FeelGood.getLoginName();
        String entry = text.getText().toString();
        String discussionTopic = CountrySelection.getSelectedTopic();
        Message m = new Message(1,"",username, entry, discussionTopic);
        MessagesBroker.addMessage(m);
        text.setText(username + " " + entry + " " + discussionTopic);
    }
}
