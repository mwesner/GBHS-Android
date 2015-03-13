package com.grandblanchs.gbhs;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.TreeSet;


public class External extends Fragment {

    private OnFragmentInteractionListener mListener;

    ListView lstExternal;
    ProgressBar prog;

    public External() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.external, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getActivity() != null) {
            lstExternal = (ListView) getView().findViewById(R.id.lstExternal);
            prog = (ProgressBar) getView().findViewById(R.id.progExternal);

            String[] external = getActivity().getResources().getStringArray(R.array.external);

            ExternalAdapter adapter = new ExternalAdapter();
            for (int i = 0; i < external.length; i++) {
                adapter.addItem(external[i]);

            }
            lstExternal.setAdapter(adapter);
            prog.setVisibility(View.GONE);
            lstExternal.setVisibility(View.VISIBLE);
        }
    }

    public interface OnFragmentInteractionListener {
    }

    //Adapter class
    private class ExternalAdapter extends BaseAdapter {
        private static final int TYPE_ITEM = 0;
        private static final int TYPE_SEPARATOR = 1;
        private static final int TYPE_MAX_COUNT = TYPE_SEPARATOR + 1;

        private ArrayList<String> mData = new ArrayList<String>();
        private LayoutInflater mInflater;

        private TreeSet<Integer> mSeparatorsSet = new TreeSet<Integer>();

        public ExternalAdapter() {
            if (getActivity() != null) {
                mInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
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
            Calendar.ViewHolder holder = null;
            int type = getItemViewType(position);
            holder = new Calendar.ViewHolder();
            /*No 'if (convertView == null)' statement to prevent view recycling
            (views must remain fixed)*/
            switch (type) {
                case TYPE_ITEM:
                    if (position %2 == 0) {
                        convertView = mInflater.inflate(R.layout.largelist, null);
                        holder.textView = (TextView) convertView.findViewById(R.id.text);
                    }else{
                        convertView = mInflater.inflate(R.layout.linklist, null);
                        holder.textView = (TextView) convertView.findViewById(R.id.text);
                    }
                    break;
            }
            convertView.setTag(holder);
            holder.textView.setText(mData.get(position));
            return convertView;
        }
    }
}