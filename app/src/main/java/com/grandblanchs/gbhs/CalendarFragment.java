package com.grandblanchs.gbhs;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class CalendarFragment extends Fragment {

    //Scrape iCal feed
    //Add events into a list that shows on date change

    private LinearLayout calendarLayout;

    private CalendarView calendarView;
    private ListView lstInfo;
    private TextView txtError;

    private ProgressBar prog;
    static final List<String> eventList = new ArrayList<>();
    private int eventCount;

    private String[] calArray;
    private String[] eventDescription;
    private String[] eventTime;

    private String selectedDate;

    private int calendarYear;
    private int calendarMonth;
    private int calendarDay;

    private Long calendarDate;

    private CalendarAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        calendarLayout = (LinearLayout) view.findViewById(R.id.calendarLayout);

        calendarView = (CalendarView) view.findViewById(R.id.calendarView);
        lstInfo = (ListView) view.findViewById(R.id.lstInfo);
        Button btnCal = (Button) view.findViewById(R.id.btnCal);
        prog = (ProgressBar) view.findViewById(R.id.progCalendar);
        txtError = (TextView) view.findViewById(R.id.txtError);

        //This will display events for a given date
        calendarView.setShowWeekNumber(false);

        btnCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent calIntent = new Intent(Intent.ACTION_VIEW, Uri.parse
                        ("http://docs.google.com/gview?embedded=true&url=http://grandblanc.schoolfusion.us/modules/groups/homepagefiles/cms/105549/File/District%20Calendar%202015-2016.pdf"));
                startActivity(calIntent);
            }
        });

        if (savedInstanceState != null) {
            calArray = savedInstanceState.getStringArray("calArray");
            eventDescription = savedInstanceState.getStringArray("eventDescription");
            eventTime = savedInstanceState.getStringArray("eventTime");
            selectedDate = savedInstanceState.getString("selectedDate");
            calendarDate = savedInstanceState.getLong("calendarDate");

            calendarYear = savedInstanceState.getInt("calendarYear");
            calendarMonth = savedInstanceState.getInt("calendarMonth");
            calendarDay = savedInstanceState.getInt("calendarDay");

            calendarView.setDate(calendarDate, true, true);

            if (calArray != null) {
                setEvents(calendarYear, calendarMonth, calendarDay, false, false);
            } else {
                FadeAnimation f = new FadeAnimation();
                f.start(txtError, prog);
                Crashlytics.log("calArray was null when restoring from savedInstanceState");

            }
        } else {
            new CalGet().execute();
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArray("calArray", calArray);
        outState.putStringArray("eventDescription", eventDescription);
        outState.putStringArray("eventTime", eventTime);
        outState.putString("selectedDate", selectedDate);

        if (calendarDate != null) {
            outState.putLong("calendarDate", calendarDate);
        }

        outState.putInt("calendarYear", calendarYear);
        outState.putInt("calendarMonth", calendarMonth);
        outState.putInt("calendarDay", calendarDay);
    }

    private void setEvents(int year, int month, int day, final boolean newCal, final boolean calVisible) {

        if (newCal) {
            //Activity is new; today is selected.
            DateTime dt = new DateTime();
            selectedDate = dt.getYear() + "" + dt.getMonthOfYear() + "" + dt.getDayOfMonth();
        }else{
            selectedDate = year + "" + month + "" + day;
        }

        String formattedDate = day + "-" + month + "-" + year;
        SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        try {
            Date d = f.parse(formattedDate);
            calendarDate = d.getTime();
        } catch (ParseException e) {
            Crashlytics.logException(e);
        }

        //Clear the events from the previously selected date
        eventList.clear();
        eventCount = 0;

        //Search for events that occur on the selected date
        for (int i = 1; i < calArray.length; i++) {
            if (calArray[i].equals(selectedDate)) {
                eventList.add(eventCount, eventDescription[i]);
                eventCount++;
            }
        }

        if (getActivity() != null) {
            //Set the content of the ListView
            mAdapter = new CalendarAdapter(getActivity());

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    lstInfo.setAdapter(mAdapter);

                    if (!calVisible) {
                        FadeAnimation f = new FadeAnimation();
                        f.start(calendarLayout, prog);
                    }

                }
            });

            //Change the events displayed when the user selects a new date.
            calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

                @Override
                public void onSelectedDayChange(CalendarView calendarView, int year, int month,
                                                int day) {

                    //Add one month because month starts at 0
                    month++;

                    calendarYear = year;
                    calendarMonth = month;
                    calendarDay = day;

                    setEvents(calendarYear, calendarMonth, calendarDay, false, true);
                }
            });
        }
    }

    private class CalGet extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            //Retrieve iCalendar with Jsoup
            Document cal;

            try {
                cal = Jsoup.connect("http://grandblanc.high.schoolfusion.us/modules/calendar/exportICal.php").get();
                //Split by event
                calArray = cal.toString().split("BEGIN:VEVENT");
                eventDescription = cal.toString().split("SUMMARY:");
                eventTime = cal.toString().split("DTSTART:");

                //CALENDAR PARSER

                eventList.clear();
                eventCount = 0;

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

                    int time = -1;

                    //Remove strings from array of times
                    if (!eventTime[i].contains("TRANS")) {
                        time = Integer.parseInt(eventTime[i]);
                    }


                    if (time < 50000 && time != -1) {
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

                //Show the events in a list
                setEvents(calendarYear, calendarMonth, calendarDay, true, false);

            } catch (final IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        FadeAnimation f = new FadeAnimation();
                        f.start(txtError, prog);
                        Crashlytics.logException(e);
                    }
                });
            }

            return null;
        }
    }
}