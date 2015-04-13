package net.bouzuya.torzder.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import net.bouzuya.torzder.R;
import net.bouzuya.torzder.models.Caption;

public class CaptionListAdapter extends ArrayAdapter<Caption> {
    private static int RESOURCE = R.layout.caption_list_item;

    public CaptionListAdapter(Context context) {
        super(context, CaptionListAdapter.RESOURCE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(CaptionListAdapter.RESOURCE, parent, false);
        }

        TextView noTextView = (TextView) convertView.findViewById(R.id.noTextView);
        TextView startTimeTextView = (TextView) convertView.findViewById(R.id.startTimeTextView);

        Caption caption = getItem(position);
        noTextView.setText(Integer.toString(position + 1));
        startTimeTextView.setText(caption.getStartTimeString());

        return convertView;
    }
}
