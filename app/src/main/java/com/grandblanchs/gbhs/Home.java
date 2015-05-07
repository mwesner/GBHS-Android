package com.grandblanchs.gbhs;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Home.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class Home extends Fragment {

    //TODO: Improve 'home' fragment layouts for landscape and x-large devices

    public interface OnFragmentInteractionListener{}

    ScrollView scrNotification;
    TextView txtNotification;
    Button btnAnnounce;
    Button btnFacebook;
    Button btnTwitter;
    Button btnTimes;
    Button btnCalendar;
    Button btnGrades;
    Button btnWeb;

    public Home() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        scrNotification = (ScrollView) view.findViewById(R.id.scrNotification);
        txtNotification = (TextView) view.findViewById(R.id.txtNotification);
        txtNotification.setCompoundDrawablesWithIntrinsicBounds(android.R.drawable.ic_dialog_alert, 0, 0, 0);
        btnAnnounce = (Button) view.findViewById(R.id.btnAnnounce);
        btnFacebook = (Button) view.findViewById(R.id.btnFacebook);
        btnTwitter = (Button) view.findViewById(R.id.btnTwitter);
        btnGrades = (Button) view.findViewById(R.id.btnGrades);
        btnCalendar = (Button) view.findViewById(R.id.btnCalendar);
        btnTimes = (Button) view.findViewById(R.id.btnTimes);
        btnWeb = (Button) view.findViewById(R.id.btnWeb);
    }

    @Override
    public void onStart() {
        super.onStart();


        /*TODO: Enable this before release.
        Disabled in testing so the website isn't constantly scraped*/
        //new CheckNotifications().execute();


        if (btnAnnounce != null) { //btnAnnounce is not present in landscape layout variant
            btnAnnounce.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((GBHS) getActivity()).onSectionAttached(1);
                }
            });
        }
        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((GBHS) getActivity()).onSectionAttached(5);
            }
        });
        btnTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((GBHS) getActivity()).onSectionAttached(6);
            }
        });
        btnGrades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Grades(getActivity()).show();
            }
        });
        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((GBHS) getActivity()).onSectionAttached(7);
            }
        });
        btnTimes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((GBHS) getActivity()).onSectionAttached(3);
            }
        });
        btnWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://grandblanc.high.schoolfusion.us"));
                startActivity(browserIntent);
            }
        });
    }

    public class CheckNotifications extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            //Check for emergency notifications on the website.
            Document emergNotif;
            try {
                emergNotif = Jsoup.connect("http://grandblanc.high.schoolfusion.us").get();
                final Elements emergNotifBox = emergNotif.getElementsByClass("emergNotifBox");
                if (!emergNotifBox.text().equals("")) {
                    //Emergency notification is present.
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            txtNotification.setText(emergNotifBox.text());
                            scrNotification.setVisibility(View.VISIBLE);
                            txtNotification.setVisibility(View.VISIBLE);
                        }
                    });
                }
            } catch (IOException | NullPointerException e) {
                //No notifications. Don't do anything.
            }

            return null;
        }
    }
}
