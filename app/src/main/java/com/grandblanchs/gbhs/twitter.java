package com.grandblanchs.gbhs;

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ArrayAdapter;

import java.io.IOException;
import java.util.ArrayList;

import twitter4j.ResponseList;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;



public class twitter extends Fragment {

    //This is authentication for the first step of logging into Twitter
    private final static String CONSUMER_KEY = "0S62lfz7hGX39oZo2jJmrhZ96";
    private final static String CONSUMER_KEY_SECRET ="Pr1YnBtFU5OErrxhpLNet2S6KolhRm43cfwZuFPCQLOasEPXm7";

    private OnFragmentInteractionListener mListener;

    ListView lst_feed;
    GridView grid_feed;

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
        new twitterPost().execute();

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


    private class twitterPost extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            Twitter twitter = new TwitterFactory().getInstance();
            twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_KEY_SECRET);

            String accessToken = "2832408273-pvUljzIaVPHm9SgWAqwQXXbBQgA9AYQ8gKI9XEQ";
            String accessTokenSecret = "0NI1CVcbKCZDWjIqqipN7LZuWuCDAqVaL37Wf6XgAa9Ww";

            AccessToken oathAccessToken = new AccessToken(accessToken, accessTokenSecret);
            twitter.setOAuthAccessToken(oathAccessToken);





            lst_feed = (ListView) getView().findViewById(R.id.list);
            grid_feed = (GridView) getView().findViewById(R.id.grid);
            final String [] tweets = new String[20];


            try {
                ResponseList<twitter4j.Status> statuses = twitter.getUserTimeline("GrandBlancPride");
                for(int i = 0; i < statuses.size(); i++) {
                    tweets[i] = statuses.get(i).getText();
                }

            } catch (TwitterException e) {
                e.printStackTrace();
            }





            ArrayList<String> list = new ArrayList<String>();
                for (int i = 0; i < tweets.length; ++i) {
                    list.add(tweets[i]);
                }
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                            android.R.layout.simple_list_item_1, tweets);
                    try {
                        lst_feed.setAdapter(adapter);
                    }catch (NullPointerException e) {
                        grid_feed.setAdapter(adapter);
                    }

                }
            });




            return null;
        }
    }

}
