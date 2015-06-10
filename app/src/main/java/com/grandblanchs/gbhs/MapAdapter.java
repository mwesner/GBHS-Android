package com.grandblanchs.gbhs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class MapAdapter extends ArrayAdapter<String> {
    String maptype[];

    public MapAdapter(Context context, String[] type) {
        super(context, R.layout.item_map, R.id.txtmaptype, type);
        this.maptype = type;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater mapInflater = LayoutInflater.from(getContext());
        if (convertView == null) {
            convertView = mapInflater.inflate(R.layout.item_map, parent, false);
        }

        TextView type = (TextView) convertView.findViewById(R.id.txtmaptype);
        type.setText(maptype[position]);

        switch (position) {
            case 0:
                type.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_map, 0, 0, 0);
                break;
            case 1:
                type.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_satellite, 0, 0, 0);
                break;
            case 3:
                type.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_terrain, 0, 0, 0);
                break;
        }

        return convertView;
    }
}
