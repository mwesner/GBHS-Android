package com.grandblanchs.gbhs;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
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

    private OnFragmentInteractionListener mListener;

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
    public void onStart() {
        super.onStart();
        if (getActivity() != null) {
            scrNotification = (ScrollView) getView().findViewById(R.id.scrNotification);
            txtNotification = (TextView) getView().findViewById(R.id.txtNotification);
            txtNotification.setCompoundDrawablesWithIntrinsicBounds(android.R.drawable.ic_dialog_alert, 0, 0, 0);
            btnAnnounce = (Button) getView().findViewById(R.id.btnAnnounce);
            btnFacebook = (Button) getView().findViewById(R.id.btnFacebook);
            btnTwitter = (Button) getView().findViewById(R.id.btnTwitter);
            btnGrades = (Button) getView().findViewById(R.id.btnGrades);
            btnCalendar = (Button) getView().findViewById(R.id.btnCalendar);
            btnTimes = (Button) getView().findViewById(R.id.btnTimes);
            btnWeb = (Button) getView().findViewById(R.id.btnWeb);

            /*TODO: Enable this before release.
            Disabled in testing so the website isn't constantly scraped*/
            //new CheckNotifications().execute();

            btnAnnounce.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((GBHS) getActivity()).setActionBarTitle(getString(R.string.Announce));
                    Announce announceFrag = new Announce();
                    getFragmentManager().beginTransaction().replace(R.id.FragmentContainer, announceFrag)
                            .addToBackStack(null).commit();
                }
            });
            btnFacebook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((GBHS) getActivity()).setActionBarTitle(getString(R.string.Facebook));
                    Facebook facebookFrag = new Facebook();
                    getFragmentManager().beginTransaction().replace(R.id.FragmentContainer, facebookFrag)
                            .addToBackStack(null).commit();
                }
            });
            btnTwitter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((GBHS) getActivity()).setActionBarTitle(getString(R.string.Twitter));
                    TwitterFeed twitterFrag = new TwitterFeed();
                    getFragmentManager().beginTransaction().replace(R.id.FragmentContainer, twitterFrag)
                            .addToBackStack(null).commit();
                }
            });
            btnGrades.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((GBHS) getActivity()).setActionBarTitle(getString(R.string.Grades));
                    getActivity().setTitle(getString(R.string.Grades));
                    new Grades(getActivity()).show();
                }
            });
            btnCalendar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((GBHS) getActivity()).setActionBarTitle(getString(R.string.Calendar));
                    Calendar calFrag = new Calendar();
                    getFragmentManager().beginTransaction().replace(R.id.FragmentContainer, calFrag)
                            .addToBackStack(null).commit();
                }
            });
            btnTimes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((GBHS) getActivity()).setActionBarTitle(getString(R.string.schedule));
                    Times timeFrag = new Times();
                    getFragmentManager().beginTransaction().replace(R.id.FragmentContainer, timeFrag)
                            .addToBackStack(null).commit();
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
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public class CheckNotifications extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            //Check for emergency notifications on the website.
            Document emergNotif;
            try {
                emergNotif = Jsoup.connect("http://grandblanc.high.schoolfusion.us").get();
                final Elements emergNotifBox = emergNotif.getElementsByClass("emergNotifBox");
                if (getActivity() != null && !emergNotifBox.text().equals("")) {
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {}

}
