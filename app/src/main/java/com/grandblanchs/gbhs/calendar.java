package com.grandblanchs.gbhs;

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;



public class calendar extends Fragment {

    private OnFragmentInteractionListener mListener;

    TextView txtMonth;
    GridView gridCal;
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

        txtMonth = (TextView) getView().findViewById(R.id.txtMonth);
        gridCal = (GridView) getView().findViewById(R.id.gridCal);
        txtInfo = (TextView) getView().findViewById(R.id.txtInfo);
        Calendar cal = Calendar.getInstance();
        txtMonth.setText(new SimpleDateFormat("MMMM yyyy").format(cal.getTime()));
        new calGet().execute();
    }

private class calGet extends AsyncTask<Void, Void, Void> {

    @Override
    protected Void doInBackground(Void... voids) {
        //This will be corrected based on the month
        final String[] numArray = new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
        "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25",
        "26", "27", "28", "29", "30"}; //, "31"};

        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                //This will display events for a given date
                gridCal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        if (gridCal.getSelectedItemPosition() == -1) {
                            txtInfo.setText("<Display Event>");
                        }
                    }

                });
            }

        });

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_list_item_1, numArray);

                gridCal.setAdapter(adapter);
            }

        });
        return null;
    }
}
}

