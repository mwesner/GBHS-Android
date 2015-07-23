package com.grandblanchs.gbhs;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class AnnounceFragment extends Fragment {

    //TODO: Connect to announcements XML feed

    ScrollView scrNotification;
    TextView txtNotification;

    CharSequence notification;

    ProgressBar prog;
    ListView lstAnnounce;

    AnnounceAdapter adapter;

    Elements group;

    ArrayList<String> text = new ArrayList<>();
    ArrayList<Integer> sort = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_announce, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        scrNotification = (ScrollView) view.findViewById(R.id.scrNotification);
        txtNotification = (TextView) view.findViewById(R.id.txtNotification);

        prog = (ProgressBar) view.findViewById(R.id.prog);
        lstAnnounce = (ListView) view.findViewById(R.id.lstAnnounce);
        lstAnnounce.setFastScrollEnabled(true);

        //TODO: Remove prototype content
        if (savedInstanceState == null) {
            new CheckNotifications().execute();
            //new AnnounceScrape().execute();
        }else{

            notification = savedInstanceState.getCharSequence("Notification");
            setNotification();

            //text = savedInstanceState.getStringArrayList("Testing");
            //sort = savedInstanceState.getIntegerArrayList("Sort");

            //adapter = new AnnounceAdapter(getActivity());

            /*for (int i = 0; i < text.size(); i++) {
                if (sort.get(i) == 0) {
                    adapter.addSeparatorItem(text.get(i));
                }else{
                    adapter.addItem(text.get(i));
                }
            }*/

            //setAnnouncements();
        }

        adapter = new AnnounceAdapter(getActivity());
        adapter.addSeparatorItem("Announcement date will appear here.");
        adapter.addItem("Announcements will appear here.");

        setAnnouncements();
    }

    @SuppressWarnings("unused")
    public class CheckNotifications extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            //Check for emergency notifications on the website.
            //TODO: Remove prototype content
            Document emergNotif;
            //try {
                //emergNotif = Jsoup.connect("http://grandblanc.high.schoolfusion.us").get();
                //final Elements emergNotifBox = emergNotif.getElementsByClass("emergNotifBox");
                //if (!emergNotifBox.text().equals("")) {
                    //Emergency notification is present.
                    //alert = emergNotifBox.text();
                    notification = "Emergency notifications such as school closings or event cancellations will appear here.";
                    setNotification();
                //}
            //} catch (IOException | NullPointerException e) {
                //No notifications. Don't do anything.
            //}

            return null;
        }
    }

    public void setNotification() {
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                txtNotification.setText(notification);

                txtNotification.setVisibility(View.VISIBLE);
                scrNotification.setVisibility(View.VISIBLE);
            }
        });
    }

    public class AnnounceScrape extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            //Scrape the daily announcements into a list.
            Document announce;

            adapter = new AnnounceAdapter(getActivity());
            text.clear();
            sort.clear();

            try {

                //TODO: Replace testing document with active link
                announce = Jsoup.parse(new File("mnt/sdcard/Announcements.xml"), "UTF-8");

                group = announce.select("group");

            } catch (NullPointerException | IOException e) {

                //Add "No Announcements."
                adapter.addItem(getString(R.string.NoAnnouncements));
                text.add(getString(R.string.NoAnnouncements));
                sort.add(1);

                if (getView() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            Snackbar.make(getView(), getString(R.string.AnnounceLoadError), Snackbar.LENGTH_LONG)
                                    .setAction(getString(R.string.Retry), new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            new AnnounceScrape().execute();
                                        }
                                    }).show();
                        }
                    });
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            parseAnnouncements();
            setAnnouncements();
        }
    }

    public void parseAnnouncements(){

        if (group != null) {
            for (int i = 0; i < group.size(); i++) {

                for (int j = 0; j < group.get(i).select("date").size(); j++) {
                    adapter.addSeparatorItem(group.get(i).select("date").get(j).text());
                    text.add(group.get(i).select("date").get(j).text());
                    sort.add(0);
                }
                for (int k = 0; k < group.get(i).select("announcement").size(); k++) {
                    adapter.addItem(group.get(i).select("announcement").get(k).text());
                    text.add(group.get(i).select("announcement").get(k).text());
                    sort.add(1);
                }
            }
        }
    }

    public void setAnnouncements() {
        lstAnnounce.setAdapter(adapter);

        FadeAnimation f = new FadeAnimation();
        f.start(lstAnnounce, null, prog);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //outState.putStringArrayList("Testing", text);
        //outState.putIntegerArrayList("Sort", sort);

        outState.putCharSequence("Notification", notification);
    }
}
