package net.bouzuya.torzder.views;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.bouzuya.torzder.R;
import net.bouzuya.torzder.models.Caption;

public class CaptionDetailView extends LinearLayout {
    private static final String LOG_TAG = "torzder";

    public CaptionDetailView(Context context) {
        super(context);
        inflate(context, R.layout.caption_detail_view, this);
    }

    public void setCaption(Caption caption) {
        TextView textView = (TextView) findViewById(R.id.captionTextView);
        textView.setText(caption.content);
    }
}
