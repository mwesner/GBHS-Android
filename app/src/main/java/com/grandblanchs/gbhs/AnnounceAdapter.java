package com.grandblanchs.gbhs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AnnounceAdapter extends ArrayAdapter<String> {

    List<String> announcelist = new ArrayList<>();

    public AnnounceAdapter(Context context, List<String> announce) {
        super(context, R.layout.item_announce, announce);
        announcelist = announce;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater staffInflater = LayoutInflater.from(getContext());
        if (convertView == null) {
            convertView = staffInflater.inflate(R.layout.item_announce, parent, false);
        }
        TextView txt = (TextView) convertView.findViewById(R.id.txtannounce);
        txt.setText(announcelist.get(position));
        return convertView;
    }
}
