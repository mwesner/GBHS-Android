package com.grandblanchs.gbhs;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
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
import android.widget.Toast;

import org.joda.time.DateTime;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Calendar extends Fragment {

    //Scrape iCal feed
    //Add events into a list that shows on date change

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

    private CalendarAdapter mAdapter;

    public Calendar() {
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
        return inflater.inflate(R.layout.calendar, container, false);
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
            new CalGet().execute();
        }
    }

    private class CalGet extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            //Retrieve iCalendar with Jsoup
            Document cal = null;


            try {
                cal = Jsoup.connect("http://grandblanc.high.schoolfusion.us/modules/calendar/exportICal.php").get();
                //Split by event
                calArray = cal.toString().split("BEGIN:VEVENT");
                eventDescription = cal.toString().split("SUMMARY:");
                eventTime = cal.toString().split("DTSTART:");

                //CALENDAR PARSER

                eventList.clear();
                eventCount = 0;

                eventList.add(0, "Today");
                eventCount++;

                for (int i = 1; i < calArray.length; i++) {
                    /*Example string format: 20150101 (substring(9, 17))
                    The leading zero in the day and month must be removed for comparison to work.
                    Strip the unnecessary portion of the string to make modification simpler.*/
                    calArray[i] = calArray[i].substring(9, 17);
                    /*Now 20150101 can be modified using (substring(0, 8))
                    Day zero is (substring(4, 5)
                    Month zero is (substring(6, 7)*/

                    if (calArray[i].substring(4, 5).equals("0") && calArray[i].substring(6, 7).equals("0")) {
                        //Remove zeroes in month and day
                        calArray[i] = calArray[i].substring(0, 4) + calArray[i].substring(5, 6) + calArray[i].substring(7, 8);
                    } else if (calArray[i].substring(4, 5).equals("0")) {
                        //Remove zero in month
                        calArray[i] = calArray[i].substring(0, 4) + calArray[i].substring(5, 8);
                    } else if (calArray[i].substring(6, 7).equals("0")) {
                        //Remove zero in day
                        calArray[i] = calArray[i].substring(0, 6) + calArray[i].substring(7, 8);
                    }
                }

                for (int i = 1; i < eventDescription.length; i++) {
                    //Retrieve the event description from the iCal feed.
                    eventDescription[i] = StringUtils.substringBefore(eventDescription[i], " PRIORITY");
                    //Replace "&amp;" with "&"
                    eventDescription[i] = eventDescription[i].replace("&amp;", "&");
                }

                for (int i = 1; i < eventTime.length; i++) {
                    //Retrieve the event start times from the iCal feed.
                    eventTime[i] = eventTime[i].substring(9, 15);

                    int time = 0;

                    //Remove strings from array of times
                    if (!eventTime[i].contains("TRANS")) {
                        time = Integer.parseInt(eventTime[i]);
                    }

                    if (time < 050000) {
                        //Time is before 0500 GMT. Roll back one day.

                        String date = calArray[i];
                        int dateChange;

                        //Subtract one from the current day.
                        if (date.length() == 7) {
                            //Double-digit day.
                            dateChange = Integer.parseInt(date.substring(5, 7)) - 1;
                        } else {
                            //Single-digit day.
                            dateChange = Integer.parseInt(date.substring(5, 6)) - 1;
                        }

                        calArray[i] = calArray[i].substring(0, 5) + String.valueOf(dateChange);
                    }
                }


                //Set the current date
                DateTime dt = new DateTime();
                int currentday = dt.getDayOfMonth();

                int currentmonth = dt.getMonthOfYear();

                int currentyear = dt.getYear();

                currentDate = currentyear + "" + currentmonth + "" + currentday;


                //Search for events that occur on the current date
                for (int i = 1; i < calArray.length; i++) {
                    if (calArray[i].equals(currentDate)) {
                        eventList.add(eventCount, eventDescription[i]);
                        eventCount++;
                    }
                }

                //Set the content of the ListView
                mAdapter = new CalendarAdapter();
                for (int i = 0; i < eventList.size(); i++) {
                    mAdapter.addItem(eventList.get(i));
                }

                if (getActivity() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            lstInfo.setAdapter(mAdapter);

                        }
                    });
                }
            } catch (final IOException e) {
                if (getActivity() != null) {
                    final Context context = getActivity().getApplicationContext();
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(context, getString(R.string.NoConnection), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            prog.setVisibility(View.GONE);
            lstInfo.setVisibility(View.VISIBLE);
            gridCal.setVisibility(View.VISIBLE);

            //Change the events displayed when the user selects a new date.
            gridCal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                @Override
                public void onSelectedDayChange(CalendarView calendarView, int year, int month, int day) {

                    //Add one month because month starts at 0
                    month++;

                    selectedDate = year + "" + month + "" + day;

                    //Clear the events from the previously selected date
                    eventList.clear();
                    eventCount = 0;

                    //Add "Today" if the selected date is the current date
                    if (selectedDate.equals(currentDate)) {
                        eventList.add(eventCount, "Today");
                        eventCount++;
                    }

                    //Search for events that occur on the selected date
                    for (int i = 1; i < calArray.length; i++) {
                        if (calArray[i].equals(selectedDate)) {
                            eventList.add(eventCount, eventDescription[i]);
                            eventCount++;
                        }
                    }

                    //Set the content of the ListView
                    mAdapter = new CalendarAdapter();
                    for (int i = 0; i < eventList.size(); i++) {
                        mAdapter.addItem(eventList.get(i));
                    }

                    lstInfo.setAdapter(mAdapter);
                }

            });
        }
    }

    //Adapter class
    private class CalendarAdapter extends BaseAdapter {
        private static final int TYPE_ITEM = 0;

        private ArrayList<String> mData = new ArrayList<String>();
        private LayoutInflater mInflater;

        public CalendarAdapter() {
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

