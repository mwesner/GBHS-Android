package com.grandblanchs.gbhs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.TreeSet;

class AnnounceAdapter extends BaseAdapter {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_SEPARATOR = 1;
    private static final int TYPE_MAX_COUNT = TYPE_SEPARATOR + 1;

    private final ArrayList<String> mData = new ArrayList<>();
    private final TreeSet<Integer> mSeparatorsSet = new TreeSet<>();
    private final LayoutInflater mInflater;

    public AnnounceAdapter(Context context) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addItem(final String item) {
        mData.add(item);
        notifyDataSetChanged();
    }

    public void addSeparatorItem(final String item) {
        mData.add(item);
        //Save separator position
        mSeparatorsSet.add(mData.size() - 1);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return mSeparatorsSet.contains(position) ? TYPE_SEPARATOR : TYPE_ITEM;
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_MAX_COUNT;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public String getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        int type = getItemViewType(position);
        holder = new ViewHolder();
        switch (type) {
            case TYPE_ITEM:
                convertView = mInflater.inflate(R.layout.item_announce, parent, false);
                holder.textView = (TextView) convertView.findViewById(R.id.text);
                break;
            case TYPE_SEPARATOR:
                convertView = mInflater.inflate(R.layout.separator_announce, parent, false);
                holder.textView = (TextView) convertView.findViewById(R.id.text);
                break;
        }
        convertView.setTag(holder);
        holder.textView.setText(mData.get(position));
        return convertView;
    }

    public static class ViewHolder {
        public TextView textView;
    }
}