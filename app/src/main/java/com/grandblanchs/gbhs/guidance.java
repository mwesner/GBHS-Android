package com.grandblanchs.gbhs;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;


public class guidance extends Fragment {

    //TODO: (Corey) Add guidance and counseling information

    private OnFragmentInteractionListener mListener;

    ProgressBar prog;

    public guidance() {
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
        if (getActivity() != null && getView() != null) {
            prog = (ProgressBar) getView().findViewById(R.id.prog);
            Toast.makeText(getActivity().getApplicationContext(), "Counseling information coming soon", Toast.LENGTH_LONG).show();
        }
    }

    public interface OnFragmentInteractionListener {}
}