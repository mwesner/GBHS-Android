package com.grandblanchs.gbhs;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class SportsFragment extends Fragment
{
    //Show season schedule for selected athletic event

    Context context;
    Resources res;

    ListView lvSport;

    String sportPicked, levelPicked, genderPicked;

    int place = 0;
    int sportPosition;

    ArrayAdapter myAdapter;

    String baseUrl;
    Calendar cal;
    SimpleDateFormat sdf;

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        if (getView() != null)
        {
            getView().setFocusableInTouchMode(true);
            getView().requestFocus();

        }

        //Allow back button to go to start of selection.
        getView().setOnKeyListener(new View.OnKeyListener()
        {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    if (keyCode == KeyEvent.KEYCODE_BACK)
                    {
                        if (place != 0) //Go back to the sport selection
                        {
                            resetData();
                        }
                        else //Go back to the last fragment
                        {
                            getActivity().onBackPressed();
                        }
                        return true;
                    }
                }
                return false;
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment on first instance created
        return inflater.inflate(R.layout.fragment_sports, container, false);
    }


    @Override
    public void onResume()
    {
        //Start at the beginning of selection when fragment is reopened
        super.onResume();
        resetData();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        context = getActivity().getApplicationContext();
        res = getActivity().getResources();

        //Get an Array of all sports, and convert it to an ArrayList
        final List<String> sportList =
                new ArrayList<>(Arrays.asList(res.getStringArray(R.array.sports)));

        //Creating a ListView and setting it up
        lvSport = (ListView) view.findViewById(R.id.lvSport);
        myAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, sportList);
        lvSport.setAdapter(myAdapter);

        //Controls for the ListView
        lvSport.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3)
            {
                if (place == 0)
                {
                    sportPicked = (String) adapter.getItemAtPosition(position);
                    sportPosition = position;
                    place++;
                    //Display levels based on sport
                    switch (position)
                    {
                        case 0:
                            changeData(0, 0);
                            break;
                        case 1: //
                            changeData(0, 0);
                            break;
                        case 2: //Bowling only has Varsity and is Combined, go to schedule.
                            levelPicked = "Varsity";
                            genderPicked = "Boys and Girls";
                            place = 0;
                            getSeasonSchedule();
                            break;
                        case 3:
                            changeData(0, 0);
                            break;
                        case 4: //Cross Country is only Varsity, go to gender.
                            levelPicked = "Varsity";
                            changeData(1, 0);
                            place ++;
                            break;
                        case 5:
                            changeData(0, 0);
                            break;
                        case 6:
                            changeData(0, 1);
                            break;
                        case 7: //Hockey is only Varsity and is only Boys, show schedule
                            levelPicked = "Varsity";
                            genderPicked = "Boys";
                            place = 0;
                            getSeasonSchedule();
                            break;
                        case 8:
                            changeData(0, 1);
                            break;
                        case 9:
                            changeData(0, 1);
                            break;
                        case 10:
                            changeData(0, 0);
                            break;
                        case 11:
                            changeData(0, 0);
                            break;
                        case 12: //Swim and Dive is only Varsity, go to gender.
                            levelPicked = "Varsity";
                            changeData(1, 0);
                            break;
                        case 13:
                            changeData(0, 1);
                            break;
                        case 14:
                            changeData(0, 2);
                            break;
                        case 15:
                            changeData(0, 0);
                            break;
                        case 16:
                            changeData(0, 1);
                            break;
                        case 17:
                            changeData(0, 1);
                            break;
                    }
                }
                //Display gender based on sport.
                else if (place == 1)
                {
                    levelPicked = (String) adapter.getItemAtPosition(position);
                    place++;
                    //Display Correct Gender based on sport
                    switch (sportPosition)
                    {

                        case 0: //Baseball is only boys, go to schedule.
                            genderPicked = "Boys";
                            place = 0;
                            getSeasonSchedule();
                            break;
                        case 1:
                            changeData(1, 0);
                            break;
                        case 3: //Cheer is only girls, go to schedule.
                            genderPicked = "Girls";
                            place = 0;
                            getSeasonSchedule();
                            break;
                        case 4:
                            changeData(1, 0);
                            break;
                        case 5: //Football is a boys sport, go to schedule
                            genderPicked = "Boys";
                            place = 0;
                            getSeasonSchedule();
                            break;
                        case 6:
                            changeData(1, 0);
                            break;
                        case 8:
                            changeData(1, 0);
                            break;
                        case 9: //Skiing is only combined sport, go to schedule
                            genderPicked = "Boys and Girls";
                            place = 0;
                            getSeasonSchedule();
                            break;
                        case 10:
                            changeData(1, 0);
                            break;
                        case 11: //Softball is only a girls sport, go to schedule
                            genderPicked = "Girls";
                            place = 0;
                            getSeasonSchedule();
                            break;
                        case 12:
                            changeData(1, 0);
                            break;
                        case 13:
                            changeData(1, 0);
                            break;
                        case 14:
                            changeData(1, 0);
                            break;
                        case 15: //Volleyball is only a girls sport, go to schedule
                            genderPicked = "Girls";
                            place = 0;
                            getSeasonSchedule();
                            break;
                        case 16:
                            changeData(1, 0);
                            break;
                        case 17: //Wrestling is only a boys sport, go to schedule
                            genderPicked = "Boys";
                            place = 0;
                            getSeasonSchedule();
                            break;
                    }
                    myAdapter.notifyDataSetChanged();
                }
                else //Display the schedule after a gender has been clicked
                {
                    genderPicked = (String) adapter.getItemAtPosition(position);
                    place = 0;
                    getSeasonSchedule();
                }
            }
        });
    }

    //Start the ListView back at the sport selection
    public void resetData()
    {
        String[] sportList = (res.getStringArray(R.array.sports));
        place = 0;
        myAdapter.clear();
        for (int i = 0; i < sportList.length; i ++)
        {
            myAdapter.add(sportList[i]);
        }
        myAdapter.notifyDataSetChanged();
    }

    //Changes the data that is held in the Adapter for the ListView
    //Type 0 is Level, Type 1 is Gender
    //Case 0 is All, Case 1 is Varsity and Junior Varsity, Case 2 is Combined
    public void changeData(int typeCode, int caseCode)
    {
        myAdapter.clear();
        if (typeCode == 0)
        {
            if (caseCode == 0)
            {
                myAdapter.add("Varsity");
                myAdapter.add("Junior Varsity");
                myAdapter.add("Freshmen");
            }
            else if (caseCode == 1)
            {
                myAdapter.add("Varsity");
                myAdapter.add("Junior Varsity");
            }
            else if (caseCode == 2)
            {
                myAdapter.add("Varsity");
            }
        }
        else
        {
            if (caseCode == 0)
            {
                myAdapter.add("Boys");
                myAdapter.add("Girls");
            }
        }
        myAdapter.notifyDataSetChanged();
    }

    public void getSeasonSchedule()
    {
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

        //Add the user choices to the URL
        baseUrl += "/" + genderPicked;
        baseUrl += "/" + levelPicked;
        baseUrl += "/" + sportPicked;

        //Remove any spaces in the URL.
        baseUrl = baseUrl.replace(" ", "%20");

        //Load the page in an external browser.
        Intent w = new Intent(Intent.ACTION_VIEW, Uri.parse(baseUrl));
        startActivity(w);
    }
}
