package com.grandblanchs.gbhs;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;


public class Times extends Fragment {

    private OnFragmentInteractionListener mListener;

    public Times() {
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
        return inflater.inflate(R.layout.times, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        //Create TabHost
        TabHost tabHost = (TabHost) getView().findViewById(R.id.tabHost);

        if (tabHost.getCurrentView() == null) {
            tabHost.setup();

            //Tab 1 - Full Day
            TabHost.TabSpec specs = tabHost.newTabSpec("tab1");
            specs.setContent(R.id.tab1);
            specs.setIndicator(getString(R.string.fullday));
            tabHost.addTab(specs);

            //Tab 2 - Half Day
            specs = tabHost.newTabSpec("tab2");
            specs.setContent(R.id.tab2);
            specs.setIndicator(getString(R.string.halfday));
            tabHost.addTab(specs);

            //Tab 3 - Late Start
            specs = tabHost.newTabSpec("tab3");
            specs.setContent(R.id.tab3);
            specs.setIndicator(getString(R.string.latestart));
            tabHost.addTab(specs);
        }
    }

    public interface OnFragmentInteractionListener {}
}
