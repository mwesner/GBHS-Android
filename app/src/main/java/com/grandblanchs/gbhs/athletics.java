package com.grandblanchs.gbhs;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class athletics extends Fragment {

    //TODO: (Corey) Add athletic events into a list that shows on date change

    private OnFragmentInteractionListener mListener;

    CalendarView gridCal;
    ListView lstInfo;

    ProgressBar prog;
    List<String> eventList = new ArrayList<String>();
    int eventCount;

    String[] calArray;
    String[] eventDescription;
    String[] eventTime;

    String currentDate;
    String selectedDate;

    private CustomAdapter mAdapter;

    public athletics() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.athletics, container, false);
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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {}

    @Override
    public void onStart() {
        super.onStart();
        if (getActivity() != null) {

            gridCal = (CalendarView) getView().findViewById(R.id.gridCal);
            lstInfo = (ListView) getView().findViewById(R.id.lstInfo);
            prog = (ProgressBar) getView().findViewById(R.id.progCalendar);

            //This will display events for a given date
            gridCal.setShowWeekNumber(false);
            new AthleticsScrape().execute();
        }
    }

    class AthleticsScrape extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            Document athlete = null;
            try {
                athlete = Jsoup.connect("http://schedules.schedulestar.com/Grand-Blanc-High-School-Grand-Blanc-MI/month").get();
                Elements Time = athlete.getElementsByClass("monthEventTime");
                Elements Desc = athlete.getElementsByClass("monthEventGender");
                System.out.println(Time.text());
                final String[] test = Time.toString().split("\n");

                if (getActivity() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            gridCal.setVisibility(View.VISIBLE);
                            prog.setVisibility(View.GONE);
                            lstInfo.setVisibility(View.VISIBLE);
                            ArrayAdapter<String> testing = new ArrayAdapter<String>(getActivity().getApplicationContext(),
                                    android.R.layout.simple_list_item_1, test);
                            lstInfo.setAdapter(testing);
                            Toast.makeText(getActivity().getApplicationContext(),
                                    "Content is: " + test[0], Toast.LENGTH_LONG).show();
                        }
                    });
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    //Adapter class
    private class CustomAdapter extends BaseAdapter {
        private static final int TYPE_ITEM = 0;

        private ArrayList<String> mData = new ArrayList<String>();
        private LayoutInflater mInflater;

        public CustomAdapter() {
            if (getActivity() != null) {
                mInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
        }

        public void addItem(final String item) {
            mData.add(item);
            notifyDataSetChanged();
        }

        @Override
        public int getItemViewType(int position) {
            return TYPE_ITEM;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
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
            ViewHolder holder = null;
            int type = getItemViewType(position);
            holder = new ViewHolder();
            switch (type) {
                case TYPE_ITEM:
                    convertView = mInflater.inflate(R.layout.bluelist, null);
                    holder.textView = (TextView) convertView.findViewById(R.id.text);
                    break;
            }
            convertView.setTag(holder);
            holder.textView.setText(mData.get(position));
            return convertView;
        }
    }

    public static class ViewHolder {
        public TextView textView;
}
}

