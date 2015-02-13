package com.grandblanchs.gbhs;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;


public class staff extends Fragment {

    //TODO: (Aaron) Hardcode names and information, sort into table/list

    private OnFragmentInteractionListener mListener;

    ProgressBar prog;
    ListView lstStaff;

    public static staff newInstance() {
        staff fragment = new staff();
        return fragment;
    }
    public staff() {
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
        return inflater.inflate(R.layout.staff, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        //getActivity().getActionBar().hide();
        new StaffScrape().execute();
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }

    String[] staffArray;
    List<String> staffList = new ArrayList<>();
    public class StaffScrape extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            Document staff;
            prog = (ProgressBar) getActivity().findViewById(R.id.prog);
            lstStaff = (ListView) getActivity().findViewById(R.id.lstStaff);
            try {
                staff = Jsoup.connect("http://grandblanc.high.schoolfusion.us/modules/cms/pages.phtml?pageid=120116").get();
                //Parse input
                staffArray = staff.toString().split("\n");
                int staffCount = 0;
                for (int i = 0; i < staffArray.length; i++) {
                    if (staffArray[i].contains("GRANDBLANCSCHOOLS.ORG")) {
                        staffList.add(staffCount, staffArray[i].substring(161, staffArray[i].length() - 10));
                        staffCount++;
                    }
                }
            }catch (IOException e) {
                final Context context = getActivity().getApplicationContext();
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(context, getString(R.string.NoConnection), Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            prog.setVisibility(View.GONE);
            //getActivity().getActionBar().show();

            //Set adapter
            StaffAdapter adapter = new StaffAdapter();

            for (int i = 0; i < staffList.size(); i++)  {
                adapter.addItem(staffList.get(i));
            }

            lstStaff.setAdapter(adapter);


        }
    }

    private class StaffAdapter extends BaseAdapter {
        private static final int TYPE_ITEM = 0;
        private static final int TYPE_SEPARATOR = 1;
        private static final int TYPE_MAX_COUNT = TYPE_SEPARATOR + 1;

        private ArrayList<String> mData = new ArrayList<String>();
        private LayoutInflater mInflater;

        private TreeSet<Integer> mSeparatorsSet = new TreeSet<Integer>();

        public StaffAdapter() {
            mInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
            calendar.ViewHolder holder = null;
            int type = getItemViewType(position);
            holder = new calendar.ViewHolder();
            /*No 'if (convertView == null)' statement to prevent view recycling
            (views must remain fixed)*/
            switch (type) {
                case TYPE_ITEM:
                    convertView = mInflater.inflate(R.layout.itemlist, null);
                    holder.textView = (TextView) convertView.findViewById(R.id.text);
                    break;
            }
            convertView.setTag(holder);
            holder.textView.setText(mData.get(position));
            return convertView;
        }
    }
}
