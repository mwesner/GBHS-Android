package com.grandblanchs.gbhs;


import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.io.IOException;


public class staff extends Fragment {

    private OnFragmentInteractionListener mListener;

    ProgressBar prog;

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
        super.onStart();
        prog = (ProgressBar) getView().findViewById(R.id.prog);
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }

    public String staffText;

    public class AnnounceScrape extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            final TextView txtStaff = (TextView) getView().findViewById(R.id.txtAnnounce);
            Document staff = null;
            try {
                staff = Jsoup.connect("http://grandblanc.high.schoolfusion.us/modules/cms/pages.phtml?pageid=120116").get();
                Elements staffClass = staff.getElementsByClass("MsoNormal");
                staffText = staffClass.text();
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        txtStaff.setText(staffText);
                    }
                });
            }catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            prog.setVisibility(View.GONE);
        }
    }
}
