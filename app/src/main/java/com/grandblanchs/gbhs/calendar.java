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
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.joda.time.DateTime;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class calendar extends Fragment {

    private OnFragmentInteractionListener mListener;

    CalendarView gridCal;
    ListView lstInfo;
    ProgressBar prog;
    List<String> infoList = new ArrayList<String>();
    int infoCount;

    String[] calArray;
    List<String> eventList = new ArrayList<String>();

    public static calendar newInstance() {
        calendar fragment = new calendar();
        return fragment;
    }

    public calendar() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.calendar, container, false);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onStart() {
        super.onStart();
        gridCal = (CalendarView) getView().findViewById(R.id.gridCal);
        lstInfo = (ListView) getView().findViewById(R.id.lstInfo);
        prog = (ProgressBar) getView().findViewById(R.id.prog);
        //This will display events for a given date
        gridCal.setShowWeekNumber(false);
        new calGet().execute();
    }

    //Adapter class
    private class CustomAdapter extends BaseAdapter {
        private static final int TYPE_ITEM = 0;

        private ArrayList<String> mData = new ArrayList<String>();
        private LayoutInflater mInflater;

        public CustomAdapter() {
            mInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
                    convertView = mInflater.inflate(R.layout.itemlist, null);
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

    private class calGet extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            //Retrieve iCalendar with Jsoup
            Document cal = null;

            try {
                cal = Jsoup.connect("http://grandblanc.high.schoolfusion.us/modules/calendar/exportICal.php").get();
                //Split by event
                calArray = cal.toString().split("BEGIN:VEVENT");


            } catch (IOException e) {
                infoList.add("Connection error.");
            }

            //CALENDAR PARSER
            int test = 0;
            for (int i = 0; i < calArray.length; i++) {
                //Store the start date (YYYYMMDD)
                eventList.add(i, calArray[i].substring(8, 17));
            }
            System.out.println(eventList.toString());

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            //Populate the calendar

            infoList.add(0, "Today");
            //Set the content of the ListView
            CustomAdapter mAdapter = new CustomAdapter();
            for (int i = 0; i < infoList.size(); i++) {
                mAdapter.addItem(infoList.get(i));
            }

            lstInfo.setAdapter(mAdapter);

            gridCal.setVisibility(View.VISIBLE);
            lstInfo.setVisibility(View.VISIBLE);
            prog.setVisibility(View.GONE);
            gridCal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                @Override
                public void onSelectedDayChange(CalendarView calendarView, int year, int month, int day) {

                    //Add one month because month starts at 0
                    month++;
                    String newDate = year + "" + month + "" + day;
                    DateTime dt = new DateTime();
                    int currentday = dt.getDayOfMonth();
                    //Double-digit month
                    String currentmonthstring = "0" + String.valueOf(dt.getMonthOfYear());
                    int currentmonth = Integer.parseInt(currentmonthstring);
                    int currentyear = dt.getYear();
                    String currentDate = currentyear + "" + currentmonth + "" + currentday;
                    System.out.println("Current Date: " + currentmonth);
                    infoList.clear();
                    infoCount = 0;

                    if (newDate.equals("20150116")) {
                        infoList.add(infoCount, "End of First Semester");
                        infoCount++;
                    }else if (eventList.contains(newDate)) {
                        infoList.add(0, eventList.get(0));
                        infoList.add(1, "Event present for " + newDate);
                    }else if (newDate.equals(currentDate)) {
                        infoList.add(0, "Today");
                    }else{
                        infoList.clear();
                        infoCount = 0;
                    }

                    //Set the content of the ListView
                    CustomAdapter mAdapter = new CustomAdapter();
                    for (int i = 0; i < infoList.size(); i++) {
                        mAdapter.addItem(infoList.get(i));
                    }

                    lstInfo.setAdapter(mAdapter);
                }

            });
        }
    }
}

