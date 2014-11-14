package com.grandblanchs.gbhs;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ArrayAdapter;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import twitter4j.ResponseList;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;



public class twitter extends Fragment {

    //Setting the consumer key and consumer secret for twitter OAuth
    private final static String CONSUMER_KEY = "0S62lfz7hGX39oZo2jJmrhZ96";
    private final static String CONSUMER_KEY_SECRET ="Pr1YnBtFU5OErrxhpLNet2S6KolhRm43cfwZuFPCQLOasEPXm7";

    private OnFragmentInteractionListener mListener;

    //Declaration of ListView lst_feed, so it can be referenced throughout twitter.java
    ListView lst_feed;

    public static twitter newInstance() {
        twitter fragment = new twitter();
        return fragment;
    }
    public twitter() {
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
        return inflater.inflate(R.layout.twitter, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        //Since internet dependant tasks cannot be performed on the main method, we execute a new one called twitterTimeline
        new twitterTimeline().execute();

    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) throws TwitterException, IOException{
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


    private class twitterTimeline extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            //Authenticates this app with my specific account codes. This will need to be changed to GBHS twitter account codes.
            Twitter twitter = new TwitterFactory().getInstance();
            twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_KEY_SECRET);

            String accessToken = "2832408273-pvUljzIaVPHm9SgWAqwQXXbBQgA9AYQ8gKI9XEQ";
            String accessTokenSecret = "0NI1CVcbKCZDWjIqqipN7LZuWuCDAqVaL37Wf6XgAa9Ww";

            AccessToken oathAccessToken = new AccessToken(accessToken, accessTokenSecret);
            twitter.setOAuthAccessToken(oathAccessToken);




            //Finds the ListView lst_feed on the GUI form
            lst_feed = (ListView) getView().findViewById(R.id.lst_feed);

            //Creates a string array of length 20 for the twenty twitter posts to be stored in.
            //This is needed because it's a lot easier to populate a ListView with a string array
            final String [] testing = new String[20];

            //A try-catch for the twitter4j method, since I can't add 'Throws twitter exception' on this method
            try {
                //Retrieves the last 20 tweets from the user 'GrandBlancPride' and puts them into a responseList
                ResponseList<twitter4j.Status> statuses = twitter.getUserTimeline("GrandBlancPride");
                //This for loop takes the text from every member of this response list and moves it to the string array
                for(int i = 0; i < statuses.size(); i++) {
                    //System.out.println(i);
                    testing[i] = statuses.get(i).getText();
                }

            } catch (TwitterException e) {
                e.printStackTrace();
            }





            ArrayList<String> list = new ArrayList<String>();
                for (int i = 0; i < testing.length; ++i) {
                    list.add(testing[i]);
                }

            //Since the UI cannot be changed without this,
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //Populates lst_feed with string array testing
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                            android.R.layout.simple_list_item_1, testing);
                    lst_feed.setAdapter(adapter);

                }
            });




            return null;
        }
    }

}
