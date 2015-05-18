package com.grandblanchs.gbhs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CalendarAdapter extends ArrayAdapter<String> {

    List<String> calendarlist;

    public CalendarAdapter(Context context, List<String> calendar) {
        super(context, R.layout.item_calendar, calendar);
        this.calendarlist = calendar;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater calInflater = LayoutInflater.from(getContext());

        if (convertView == null) {
            convertView = calInflater.inflate(R.layout.item_calendar, parent, false);
        }
        TextView txt = (TextView) convertView.findViewById(R.id.caltext);
        txt.setText(calendarlist.get(position));
        return convertView;
    }

}
