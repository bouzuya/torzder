package net.bouzuya.torzder;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import net.bouzuya.torzder.models.Talk;
import net.bouzuya.torzder.views.TalkListAdapter;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    private static final String LOG_TAG = "torzder";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.talkListButton);
        ListView listView = (ListView) findViewById(R.id.talkListView);
        final TalkListAdapter adapter = new TalkListAdapter(this);
        listView.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Talk> talkList = fetchTalkList();
                Log.d(LOG_TAG, String.format("talkList length: %d", talkList.size()));
                adapter.clear();
                adapter.addAll(talkList);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private List<Talk> fetchTalkList() {
        // TODO
        List<Talk> talks = new ArrayList<>();
        Talk talk = new Talk();
        talk.id = 2041;
        talk.title = "Want to innovate? Become a \"now-ist\"";
        talk.speaker = "Joi Ito";
        talks.add(talk);
        return talks;
    }
}
