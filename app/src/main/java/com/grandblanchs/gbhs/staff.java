package com.grandblanchs.gbhs;


import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.io.IOException;


public class staff extends Fragment {

    private OnFragmentInteractionListener mListener;

    public static staff newInstance() {
        staff fragment = new staff();
        return fragment;
    }
    public staff() {
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
        return inflater.inflate(R.layout.admin, container, false);
    }

    @Override
    public void onStart() {


    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }

    public String staffText;

    public class AnnounceScrape extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            final TextView txtAnnounce = (TextView) getView().findViewById(R.id.txtAnnounce);
            Document announce = null;
            try {
                announce = Jsoup.connect("http://grandblanc.high.schoolfusion.us/modules/cms/pages.phtml?pageid=120116&sessionid=97950641ed41d0c764dbc8db367cee6a&sessionid=97950641ed41d0c764dbc8db367cee6a").get();
                Elements announceClass = announce.getElementsByClass("MsoNormal");
                staffText = announceClass.text();
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        txtAnnounce.setText(staffText);
                    }
                });
            }catch (IOException e) {
                e.printStackTrace();



            }
            return null;
        }
    }










}
