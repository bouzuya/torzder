package net.bouzuya.torzder.views;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import net.bouzuya.torzder.MainActivity;
import net.bouzuya.torzder.R;
import net.bouzuya.torzder.models.Caption;
import net.bouzuya.torzder.models.CaptionListRequest;
import net.bouzuya.torzder.models.Talk;
import net.bouzuya.torzder.models.TalkDetailRequest;

import java.util.List;

public class TalkDetailView extends LinearLayout {
    private static final String LOG_TAG = "torzder";

    public TalkDetailView(Context context) {
        super(context);
        inflate(context, R.layout.talk_detail_view, this);
    }

    public void setTalk(Talk talk) {
        final CaptionListAdapter adapter = new CaptionListAdapter(getContext());

        TextView idTextView = (TextView) findViewById(R.id.idTextView);
        TextView speakerTextView = (TextView) findViewById(R.id.speakerTextView);
        TextView titleTextView = (TextView) findViewById(R.id.titleTextView);
        ListView captionListView = (ListView) findViewById(R.id.listView);

        // initialize views
        idTextView.setText(Integer.toString(talk.id));
        speakerTextView.setText(talk.speaker);
        titleTextView.setText(talk.title);
        captionListView.setAdapter(adapter);
        captionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Caption caption = adapter.getItem(position);
                MainActivity mainActivity = (MainActivity) getContext();
                mainActivity.showCaption(caption);
            }
        });

        // fetch captions
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        CaptionListRequest request = new CaptionListRequest(
                talk.id,
                new Response.Listener<List<Caption>>() {
                    @Override
                    public void onResponse(List<Caption> response) {
                        adapter.clear();
                        adapter.addAll(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(LOG_TAG, "fetch caption error", error);
                    }
                }
        );
        requestQueue.add(request);

        // fetch talk detail
        TalkDetailRequest request2 = new TalkDetailRequest(
                talk.key,
                new Response.Listener<Talk>() {
                    @Override
                    public void onResponse(Talk response) {
                        Log.d(LOG_TAG, response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(LOG_TAG, "fetch caption error", error);
                    }
                }
        );
        requestQueue.add(request2);

    }

    public void downloadVideo() {
        // TODO:
        DownloadManager downloadManager = (DownloadManager) getContext()
                .getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse("http://bouzuya.net/index.html");
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setDestinationInExternalFilesDir(
                getContext(), Environment.DIRECTORY_DOWNLOADS, "/index.html");
        request.setTitle("Download torzder video");
        request.setAllowedNetworkTypes(
                DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        request.setMimeType("text/html");
        downloadManager.enqueue(request);
    }
}
