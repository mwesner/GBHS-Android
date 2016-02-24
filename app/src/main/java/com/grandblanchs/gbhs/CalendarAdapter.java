package com.grandblanchs.gbhs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

class CalendarAdapter extends ArrayAdapter<String> {

    private final List<String> calendarlist;

    CalendarAdapter(Context context) {
        super(context, R.layout.item_calendar, CalendarFragment.eventList);
        this.calendarlist = CalendarFragment.eventList;
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
