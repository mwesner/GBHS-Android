package com.grandblanchs.gbhs;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

    TextView txtNotification;
    Button btnAnnounce;
    Button btnFacebook;
    Button btnTwitter;
    Button btnTimes;
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
            txtNotification = (TextView) getView().findViewById(R.id.txtNotification);
            txtNotification.setCompoundDrawablesWithIntrinsicBounds(android.R.drawable.ic_dialog_alert, 0, 0, 0);
            btnAnnounce = (Button) getView().findViewById(R.id.btnAnnounce);
            btnFacebook = (Button) getView().findViewById(R.id.btnFacebook);
            btnTwitter = (Button) getView().findViewById(R.id.btnTwitter);
            btnGrades = (Button) getView().findViewById(R.id.btnGrades);
            btnTimes = (Button) getView().findViewById(R.id.btnTimes);
            btnWeb = (Button) getView().findViewById(R.id.btnWeb);
            new CheckNotifications().execute();

            btnAnnounce.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Announce announcefragment = new Announce();
                    getActivity().setTitle(getString(R.string.Announce));
                    FragmentManager fragmentManager = getActivity().getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.FragmentContainer, announcefragment);
                    fragmentTransaction.commit();
                }
            });
            btnTwitter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TwitterFeed twitterfragment = new TwitterFeed();
                    getActivity().setTitle(getString(R.string.Twitter));
                    FragmentManager fragmentManager = getActivity().getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.FragmentContainer, twitterfragment);
                    fragmentTransaction.commit();
                }
            });
            btnFacebook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Facebook facebookfragment = new Facebook();
                    getActivity().setTitle(getString(R.string.Facebook));
                    FragmentManager fragmentManager = getActivity().getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.FragmentContainer, facebookfragment);
                    fragmentTransaction.commit();
                }
            });
            btnGrades.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().setTitle(getString(R.string.Grades));
                    new Grades(getActivity()).show();
                }
            });
            btnTimes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().setTitle(getString(R.string.schedulemenu));
                    new Times(getActivity()).show();
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
