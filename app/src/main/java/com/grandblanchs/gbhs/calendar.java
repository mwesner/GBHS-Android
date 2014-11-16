package com.grandblanchs.gbhs;

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import java.util.Calendar;


public class calendar extends Fragment {

    private OnFragmentInteractionListener mListener;

    CalendarView gridCal;
    TextView txtInfo;

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
        System.out.println("onStart");

        gridCal = (CalendarView) getView().findViewById(R.id.gridCal);
        txtInfo = (TextView) getView().findViewById(R.id.txtInfo);
        //This will display events for a given date
        txtInfo.setText("Today");
        gridCal.setShowWeekNumber(false);
        //new calGet().execute();
        gridCal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int day) {
                //Add one month because month starts at 0
                month ++;
                String newDate = day + "" + month + "" + year;
                Calendar calendar = Calendar.getInstance();
                int newweek = calendar.get(Calendar.DAY_OF_MONTH);
                int newmonth = calendar.get(Calendar.MONTH) + 1;
                int newyear = calendar.get(Calendar.YEAR);
                String currentDate = newweek + "" + newmonth + "" + newyear;
                /*System.out.println("New Date: " + newDate);
                System.out.println("Current Date: " + currentDate);*/
                if (newDate.equals(currentDate)) {
                    txtInfo.setText("Today");
                }else{
                    txtInfo.setText("");
                }
            }

        });
    }

private class calGet extends AsyncTask<Void, Void, Void> {

    @Override
    protected Void doInBackground(Void... voids) {

        getActivity().runOnUiThread(new Runnable() {
            public void run() {

            }

        });
        return null;
    }
}
}

