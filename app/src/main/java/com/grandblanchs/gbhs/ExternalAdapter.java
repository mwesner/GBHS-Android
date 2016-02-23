package com.grandblanchs.gbhs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

class ExternalAdapter extends ArrayAdapter<String> {
    private static final int TYPE_ITEM = 0;

    private final String[] externalarray;

    ExternalAdapter(Context context, String[] external) {
        super(context, 0, external);
        this.externalarray = external;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        int type = getItemViewType(position);
        holder = new ViewHolder();
        LayoutInflater externalInflater = LayoutInflater.from(getContext());
        switch (type) {
            case TYPE_ITEM:
                if (position % 2 == 0) {
                    convertView = externalInflater.inflate(R.layout.item_external_title, parent, false);
                    holder.textView = (TextView) convertView.findViewById(R.id.text);
                } else {
                    convertView = externalInflater.inflate(R.layout.item_external_link, parent, false);
                    holder.textView = (TextView) convertView.findViewById(R.id.text);
                }
                break;
        }
        convertView.setTag(holder);
        holder.textView.setText(externalarray[position]);
        return convertView;
    }

    public static class ViewHolder {
        public TextView textView;
    }
}
