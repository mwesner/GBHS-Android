package com.grandblanchs.gbhs;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Admin.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class Admin extends Fragment {

    private OnFragmentInteractionListener mListener;

    public Admin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.admin, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
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

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public void onStart() {
        super.onStart();
        new AdminScrape().execute();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }


    public class AdminScrape extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Document doc = Jsoup.connect("http://grandblanc.high.schoolfusion.us/modules/cms/pages.phtml?pageid=299293&sessionid=12dcf41eb0da3e6744803422860188de&sessionid=12dcf41eb0da3e6744803422860188de").get();
                Elements img = doc.getElementsByTag("img");
                for (Element el : img) {
                    //for each element get the srs url
                    String src = el.absUrl("src");
                    System.out.println("Image Found!");
                    System.out.println("src attribute is : "+src);
                    Logger.getLogger(AdminScrape.class.getName()).log(Level.SEVERE, null);

                }






            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

    }




}
