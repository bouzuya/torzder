package net.bouzuya.torzder.views;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import net.bouzuya.torzder.MainActivity;
import net.bouzuya.torzder.R;
import net.bouzuya.torzder.models.Talk;

import java.util.ArrayList;
import java.util.List;

public class TalkListView extends RelativeLayout {
    private static final String LOG_TAG = "torzder";

    public TalkListView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.talk_list_view, this);

        Button button = (Button) findViewById(R.id.talkListButton);
        ListView listView = (ListView) findViewById(R.id.talkListView);
        final TalkListAdapter adapter = new TalkListAdapter(context);
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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Talk talk = adapter.getItem(position);
                Log.d(LOG_TAG, String.format("selected talk: %s", talk));
                MainActivity mainActivity = (MainActivity) getContext();
                mainActivity.showTalk(talk);
            }
        });
    }

    private List<Talk> fetchTalkList() {
        // TODO:
        List<Talk> talks = new ArrayList<>();
        Talk talk = new Talk();
        talk.id = 2041;
        talk.title = "Want to innovate? Become a \"now-ist\"";
        talk.speaker = "Joi Ito";
        talks.add(talk);
        return talks;
    }
}
