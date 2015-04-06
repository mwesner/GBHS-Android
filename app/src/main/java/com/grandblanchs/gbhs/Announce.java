package com.grandblanchs.gbhs;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Announce extends Fragment {

    public interface OnFragmentInteractionListener {}

    //TODO: (Aaron) Properly scrape announcement text
    //TODO: (Aaron) Work on setting up RSS feed for announcements with GBTV

    ProgressBar prog;
    ListView lstAnnounce;
    ArrayAdapter adapter;

    String[] announceTextArray;
    String[] displayArray;

    Context context;

    public Announce() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.announce, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prog = (ProgressBar) view.findViewById(R.id.prog);
        lstAnnounce = (ListView) view.findViewById(R.id.lstAnnounce);
    }

    public void onStart() {
        super.onStart();
        new AnnounceScrape().execute();
    }

    public class AnnounceScrape extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            //Scrape the daily announcements into a list.
            Document announce;
            //Document rss = null;
            List<String> list;

            try {
                announce = Jsoup.connect("http://grandblanc.high.schoolfusion.us/modules/cms/pages.phtml?pageid=22922").get();
                Elements announceClass = announce.getElementsByClass("MsoNormal").tagName("li");

                //Split by class/bullet point
                announceTextArray = announceClass.toString().split("</li>");

                if (announceClass.isEmpty()) {
                    list = new ArrayList<>();

                    //Add "No Announcements."
                    list.add(0, "No announcements.");

                }else{
                    displayArray = new String[announceTextArray.length];
                    for (int i = 0; i < announceTextArray.length; i++) {
                        //Populate the array, removing the class and style tags

                        displayArray[i] = announceTextArray[i].substring(85, announceTextArray[i].length());

                        //Overwrite undesired HTML characters
                        displayArray[i] = displayArray[i].replace("&nbsp;", "");
                        displayArray[i] = displayArray[i].replace("&amp;", "&");
                        displayArray[i] = displayArray[i].replace("<br>", "");
                        displayArray[i] = displayArray[i].replace("</strong></span>", "");
                        displayArray[i] = displayArray[i].replace("ext-align: center;\">  <span style=\"font-size:16px;\"><strong>", "");
                    }


                    //Convert to ArrayList for easy item removal
                    list = new ArrayList<>(Arrays.asList(displayArray));

                    //Remove the first entry
                    list.remove(0);

                }
                    context = getActivity().getApplicationContext();
                    adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, list);
            } catch (IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(context, getString(R.string.NoConnection), Toast.LENGTH_LONG).show();
                    }
                });
            } catch (NullPointerException e) {
                list = new ArrayList<>();

                //Add "No Announcements."
                list.add(0, "No announcements.");
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
}
