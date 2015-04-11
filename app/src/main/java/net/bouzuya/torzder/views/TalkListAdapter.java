package net.bouzuya.torzder.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import net.bouzuya.torzder.R;
import net.bouzuya.torzder.models.Talk;

public class TalkListAdapter extends ArrayAdapter<Talk> {
    private static int RESOURCE = R.layout.talk_list_item;

    public TalkListAdapter(Context context) {
        super(context, TalkListAdapter.RESOURCE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(TalkListAdapter.RESOURCE, parent, false);
        }

        TextView idTextView = (TextView) convertView.findViewById(R.id.idTextView);
        TextView speakerTextView = (TextView) convertView.findViewById(R.id.speakerTextView);
        TextView titleTextView = (TextView) convertView.findViewById(R.id.titleTextView);

        Talk talk = getItem(position);
        idTextView.setText(Integer.toString(talk.id));
        speakerTextView.setText(talk.speaker);
        titleTextView.setText(talk.title);

        return convertView;
    }
}
