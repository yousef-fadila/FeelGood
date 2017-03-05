package apps.stisser.karissa.feelgood;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;

/**
 * Created by User on 3/5/2017.
 */
public class DiscussionActivity extends ActionBarActivity {
    ListView messages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.discussion);
        messages = (ListView)findViewById(R.id.messageList);
    }
}
