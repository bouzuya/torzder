package net.bouzuya.torzder.views;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.bouzuya.torzder.R;
import net.bouzuya.torzder.models.Talk;

public class TalkDetailView extends LinearLayout {
    public TalkDetailView(Context context, Talk talk) {
        super(context);
        inflate(context, R.layout.talk_detail_view, this);

        TextView idTextView = (TextView) findViewById(R.id.idTextView);
        TextView speakerTextView = (TextView) findViewById(R.id.speakerTextView);
        TextView titleTextView = (TextView) findViewById(R.id.titleTextView);

        idTextView.setText(Integer.toString(talk.id));
        speakerTextView.setText(talk.speaker);
        titleTextView.setText(talk.title);
    }
}
