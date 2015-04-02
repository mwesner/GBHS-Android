package com.grandblanchs.gbhs;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;


public class Athletics extends Fragment {

    //TODO: (Corey) Add athletic events into a list that shows on date change

    private OnFragmentInteractionListener mListener;

    ProgressBar prog;
    Spinner lstSport;
    Spinner lstLevel;
    Spinner lstGender;
    Button btnSport;

    boolean sportPicked;
    boolean levelPicked;
    boolean genderPicked;

    public Athletics() {
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
            prog = (ProgressBar) getView().findViewById(R.id.progCalendar);
            lstSport = (Spinner) getView().findViewById(R.id.lstSport);
            lstLevel = (Spinner) getView().findViewById(R.id.lstLevel);
            lstGender = (Spinner) getView().findViewById(R.id.lstGender);
            btnSport = (Button) getView().findViewById(R.id.btnSport);

            lstSport.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position == 0) {
                        sportPicked = false;
                        toggleSportButton();
                    } else {
                        btnSport.setEnabled(true);
                        sportPicked = true;
                        toggleSportButton();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    sportPicked = false;
                    toggleSportButton();
                }
            });

            lstLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position == 0) {
                        levelPicked = false;
                        toggleSportButton();
                    } else {
                        levelPicked = true;
                        toggleSportButton();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    levelPicked = false;
                    toggleSportButton();
                }
            });

            lstGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position == 0) {
                        genderPicked = false;
                        toggleSportButton();
                    } else {
                        genderPicked = true;
                        toggleSportButton();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    genderPicked = false;
                    toggleSportButton();
                }
            });

            btnSport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(
                            getActivity().getApplicationContext(),
                            getResources().getStringArray(R.array.sportsgender)[lstGender.getSelectedItemPosition()]
                            +" " + getResources().getStringArray(R.array.sportslevel)[lstLevel.getSelectedItemPosition()]
                            + " " +getResources().getStringArray(R.array.sports)[lstSport.getSelectedItemPosition()],
                            Toast.LENGTH_LONG)
                            .show();
                }
            });
        }
    }

        public void toggleSportButton() {
            if (sportPicked && levelPicked && genderPicked) {
                btnSport.setEnabled(true);
            }else{
                btnSport.setEnabled(false);
            }
    }
}