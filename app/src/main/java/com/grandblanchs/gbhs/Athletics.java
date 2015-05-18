package com.grandblanchs.gbhs;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class Athletics extends Fragment {

    //Show season schedule for selected athletic event

    public interface OnFragmentInteractionListener{}

    Context context;
    Resources res;

    ProgressBar prog;
    Spinner lstSport;
    Spinner lstLevel;
    Spinner lstGender;
    Button btnSport;

    boolean sportPicked;
    boolean levelPicked;
    boolean genderPicked;

    String baseUrl;
    Calendar cal;
    SimpleDateFormat sdf;

    String[] levelselect;
    String[] genderselect;

    String[] sports;
    String[] levelall;
    String[] varsity;
    String[] juniorvarsity;
    String[] genderall;
    String[] boys;
    String[] girls;
    String[] boysgirls;
    String[] combined;

    int levelStatus;
    /*0 = all
     *1 = V
     *2 = JV
     */

    int genderStatus;
    /*0 = all
     *1 = boys
     *2 = girls
     *3 = boysgirls
     *4 = combined
     */

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
        return inflater.inflate(R.layout.fragment_athletics, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = getActivity().getApplicationContext();
        res = getActivity().getResources();

        lstSport = (Spinner) view.findViewById(R.id.lstSport);
        lstLevel = (Spinner) view.findViewById(R.id.lstLevel);
        lstGender = (Spinner) view.findViewById(R.id.lstGender);
        btnSport = (Button) view.findViewById(R.id.btnSport);

        levelselect = res.getStringArray(R.array.sportslevelselect); //Clears level options
        genderselect = res.getStringArray(R.array.sportsgenderselect); //Clears gender options
        sports = res.getStringArray(R.array.sports); //List of sports
        levelall = res.getStringArray(R.array.sportslevelall); //Varsity, JV, and Freshman
        varsity = res.getStringArray(R.array.sportslevelvarsity); //Varsity only
        juniorvarsity = res.getStringArray(R.array.sportsleveljuniorvarsity); //Varsity and JV only
        genderall = res.getStringArray(R.array.sportsgenderall); //Boys, Girls, and "Boys and Girls"
        boysgirls = res.getStringArray(R.array.sportsgenderboysgirls); //Only Boys and Girls
        boys = res.getStringArray(R.array.sportsgenderboys); //Only Boys
        girls = res.getStringArray(R.array.sportsgendergirls);  //Only Girls
        combined = res.getStringArray(R.array.sportsgendercombined); //Only "Boys and Girls"

        lstSport.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    sportPicked = false;
                    toggleSportButton();
                    lstGender.setEnabled(false);
                    lstLevel.setEnabled(false);
                    checkSport();
                } else {
                    sportPicked = true;
                    toggleSportButton();
                    lstLevel.setEnabled(true);
                    lstGender.setEnabled(true);
                    checkSport();
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

                getSeasonSchedule();
            }
        });
    }

    public void toggleSportButton() {
        if (sportPicked && levelPicked && genderPicked) {
            btnSport.setEnabled(true);
        }else{
            btnSport.setEnabled(false);
        }
    }

    public void checkSport() {
        //Change the options available based on the selected sport.

        switch (lstSport.getSelectedItemPosition()) {
            case 0: //Reset
                clearOptions();
            case 1: //Baseball: All levels, Boys
                setLevelAll();
                setBoys();
                break;
            case 2: //Basketball: All levels, Boys, Girls
                setLevelAll();
                setBoysGirls();
                break;
            case 3: //Bowling: Varsity, Combined
                setVarsity();
                setCombined();
                break;
            case 4: //Cheerleading: All levels, Girls
                setLevelAll();
                setGirls();
                break;
            case 5: //Cross Country: Varsity, all genders
                setVarsity();
                setGenderAll();
                break;
            case 6: //Football: All levels, Boys
                setLevelAll();
                setBoys();
                break;
            case 7: //Golf: Varsity and JV, Boys, Girls
                setJuniorVarsity();
                setBoysGirls();
                break;
            case 8: //Ice Hockey: Varsity, Boys
                setVarsity();
                setBoys();
                break;
            case 9: //Lacrosse: Varsity and JV, Boys, Girls
                setJuniorVarsity();
                setBoysGirls();
                break;
            case 10: //Skiing: Varsity and JV, Combined
                setJuniorVarsity();
                setCombined();
                break;
            case 11: //Soccer: All levels, Boys, Girls
                setLevelAll();
                setBoysGirls();
                break;
            case 12: //Softball: All levels, Girls
                setLevelAll();
                setGirls();
                break;
            case 13: //Swim and Dive: Varsity, Boys, Girls
                setVarsity();
                setBoysGirls();
                break;
            case 14: //Tennis: Varsity, JV, Boys, Girls
                setJuniorVarsity();
                setBoysGirls();
                break;
            case 15: //Track: Varsity, Boys, Girls
                setVarsity();
                setBoysGirls();
                break;
            case 16: //Volleyball: All levels, Girls
                setLevelAll();
                setGirls();
                break;
            case 17: //Water Polo: Varsity, JV, Boys, Girls
                setJuniorVarsity();
                setBoysGirls();
                break;
        }

    }

    public void clearOptions() {
        ArrayAdapter levelAdapter = new ArrayAdapter<>(
                context,
                android.R.layout.simple_spinner_dropdown_item,
                levelselect);

        ArrayAdapter genderAdapter = new ArrayAdapter<>(
                context,
                android.R.layout.simple_spinner_dropdown_item,
                genderselect);

        lstLevel.setAdapter(levelAdapter);
        lstGender.setAdapter(genderAdapter);

    }


    public void setLevelAll() {

        levelStatus = 1;

        ArrayAdapter spinnerAdapter = new ArrayAdapter<>(
                context,
                android.R.layout.simple_spinner_dropdown_item,
                levelall);

        lstLevel.setAdapter(spinnerAdapter);
    }

    public void setVarsity() {

        levelStatus = 2;

        ArrayAdapter spinnerAdapter = new ArrayAdapter<>(
                context,
                android.R.layout.simple_spinner_dropdown_item,
                varsity);

        lstLevel.setAdapter(spinnerAdapter);
    }

    public void setJuniorVarsity() {

        levelStatus = 3;

        ArrayAdapter spinnerAdapter = new ArrayAdapter<>(
                context,
                android.R.layout.simple_spinner_dropdown_item,
                juniorvarsity);

        lstLevel.setAdapter(spinnerAdapter);
    }

    public void setGenderAll() {

        genderStatus = 1;

        ArrayAdapter spinnerAdapter = new ArrayAdapter<>(
                context,
                android.R.layout.simple_spinner_dropdown_item,
                genderall);

        lstGender.setAdapter(spinnerAdapter);
    }

    public void setBoys() {

        genderStatus = 2;

        ArrayAdapter spinnerAdapter = new ArrayAdapter<>(
                context,
                android.R.layout.simple_spinner_dropdown_item,
                boys);

        lstGender.setAdapter(spinnerAdapter);
    }

    public void setGirls() {

        genderStatus = 3;

        ArrayAdapter spinnerAdapter = new ArrayAdapter<>(
                context,
                android.R.layout.simple_spinner_dropdown_item,
                girls);

        lstGender.setAdapter(spinnerAdapter);
    }

    public void setBoysGirls() {

        genderStatus = 4;

        ArrayAdapter spinnerAdapter = new ArrayAdapter<>(
                context,
                android.R.layout.simple_spinner_dropdown_item,
                boysgirls);

        lstGender.setAdapter(spinnerAdapter);
    }

    public void setCombined() {

        genderStatus = 5;

        ArrayAdapter spinnerAdapter = new ArrayAdapter<>(
                context,
                android.R.layout.simple_spinner_dropdown_item,
                combined);

        lstGender.setAdapter(spinnerAdapter);
    }

    public void getSeasonSchedule() {

        /**SCHEDULE SCRAPER**/

        //Construct URL based on user selections

        /*URL structure:
        http://schedules.schedulestar.com/Grand-Blanc-High-School-Grand-Blanc-MI/season/[MM-dd-yyyy]/[Gender]/[Level]/[Sport]
        */

        //Add the current date (MM-DD-YYYY).
        cal = Calendar.getInstance();
        sdf = new SimpleDateFormat("MM-dd-yyyy", Locale.US);

        //Get the base season event page.
        baseUrl = getString(R.string.BaseURL) + "/" + sdf.format(cal.getTime());

        //Add the gender to the URL constructor.
        switch (genderStatus) {
            case 1:
                baseUrl += "/" + genderall[lstGender.getSelectedItemPosition()];
                break;
            case 2:
                baseUrl += "/" + boys[lstGender.getSelectedItemPosition()];
                break;
            case 3:
                baseUrl += "/" + girls[lstGender.getSelectedItemPosition()];
                break;
            case 4:
                baseUrl += "/" + boysgirls[lstGender.getSelectedItemPosition()];
                break;
            case 5:
                baseUrl += "/" + combined[lstGender.getSelectedItemPosition()];
                break;
        }

        //Add the level to the URL constructor.
        switch (levelStatus) {
            case 1:
                baseUrl += "/" + levelall[lstLevel.getSelectedItemPosition()];
                break;
            case 2:
                baseUrl += "/" + varsity[lstLevel.getSelectedItemPosition()];
                break;
            case 3:
                baseUrl += "/" + juniorvarsity[lstLevel.getSelectedItemPosition()];
                break;
        }

        //Add the sport to the URL constructor.
        baseUrl += "/" + sports[lstSport.getSelectedItemPosition()];

        //Remove any spaces in the URL.
        baseUrl = baseUrl.replace(" ", "%20");

        //Load the page in an external browser.
        Intent w = new Intent(Intent.ACTION_VIEW, Uri.parse(baseUrl));
        startActivity(w);

    }
}
