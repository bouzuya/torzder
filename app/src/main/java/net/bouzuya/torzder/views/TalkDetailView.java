package net.bouzuya.torzder.views;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import net.bouzuya.torzder.R;
import net.bouzuya.torzder.models.Caption;
import net.bouzuya.torzder.models.CaptionListRequest;
import net.bouzuya.torzder.models.Talk;

import java.util.List;

public class TalkDetailView extends LinearLayout {

    public TalkDetailView(Context context) {
        super(context);
        inflate(context, R.layout.talk_detail_view, this);
    }

    public void setTalk(Talk talk) {
        TextView idTextView = (TextView) findViewById(R.id.idTextView);
        TextView speakerTextView = (TextView) findViewById(R.id.speakerTextView);
        TextView titleTextView = (TextView) findViewById(R.id.titleTextView);
        final ListView captionListView = (ListView) findViewById(R.id.listView);

        idTextView.setText(Integer.toString(talk.id));
        speakerTextView.setText(talk.speaker);
        titleTextView.setText(talk.title);

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        CaptionListRequest request = new CaptionListRequest(
                talk.id,
                new Response.Listener<List<Caption>>() {
                    @Override
                    public void onResponse(List<Caption> response) {
                        CaptionListAdapter adapter = new CaptionListAdapter(getContext());
                        adapter.addAll(response);
                        captionListView.setAdapter(adapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        requestQueue.add(request);
    }
}
