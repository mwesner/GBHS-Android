package com.grandblanchs.gbhs;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.io.IOException;

public class announce extends Fragment {

    private OnFragmentInteractionListener mListener;

    ProgressBar prog;
    ListView lstAnnounce;
    ArrayAdapter adapter;

    String announceText;
    String[] announceTextArray;
    String[] displayArray;

    Context context;

    public static announce newInstance() {
        announce fragment = new announce();
        return fragment;
    }

    public announce() {
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
        return inflater.inflate(R.layout.announce, container, false);
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

    public void onStart() {
        super.onStart();
        prog = (ProgressBar) getView().findViewById(R.id.prog);
        lstAnnounce = (ListView) getView().findViewById(R.id.lstAnnounce);

        new AnnounceScrape().execute();
    }

    public class AnnounceScrape extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            Document announce = null;
            context = getActivity().getApplicationContext();
            try {
                announce = Jsoup.connect("http://grandblanc.high.schoolfusion.us/modules/cms/pages.phtml?pageid=22922").get();
                Elements announceClass = announce.getElementsByClass("MsoNormal").tagName("li");

                announceTextArray = announceClass.text().split("</li>");
                displayArray = new String[announceTextArray.length];
                for (int i = 0; i < announceTextArray.length; i++) {
                    displayArray[i] = announceTextArray[i].substring(22, announceTextArray[i].length());
                }
                adapter = new ArrayAdapter<String>(context,
                        android.R.layout.simple_list_item_1, displayArray);
            } catch (IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(context, getString(R.string.NoConnection), Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            lstAnnounce.setAdapter(adapter);
            prog.setVisibility(View.GONE);
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

}
