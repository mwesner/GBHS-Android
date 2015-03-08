package com.grandblanchs.gbhs;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link home.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class home extends Fragment {

    //TODO: Improve 'home' fragment layouts for landscape and x-large devices

    private OnFragmentInteractionListener mListener;

    TextView txtNotification;

    public home() {
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
        txtNotification = (TextView) getView().findViewById(R.id.txtNotification);
        txtNotification.setCompoundDrawablesWithIntrinsicBounds(android.R.drawable.ic_dialog_alert, 0, 0, 0);
        new checkNotifications().execute();
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public class checkNotifications extends AsyncTask<Void, Void, Void> {
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
