package com.grandblanchs.gbhs;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Staff extends Fragment {

    public interface OnFragmentInteractionListener{}

    //TODO: (Aaron) Scrape names, emails, and pages, sort into table/list

    ProgressBar prog;
    ListView lstStaff;

    public Staff() {
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
        return inflater.inflate(R.layout.staff, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prog = (ProgressBar) view.findViewById(R.id.prog);
        lstStaff = (ListView) view.findViewById(R.id.lstStaff);
    }

    @Override
    public void onStart() {
        super.onStart();
        new StaffScrape().execute();
    }

    String[] staffArray;
    String[] emailArray;
    List<String> staffList = new ArrayList<>();
    public class StaffScrape extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            Document staff;
            try {
                staff = Jsoup.connect("http://grandblanc.high.schoolfusion.us/modules/cms/pages.phtml?pageid=120116").get();
                //Parse input
                staffArray = staff.toString().split("\n");
                emailArray = staff.toString().split("\n");
                int staffCount = 0;
                for (int i = 0; i < staffArray.length; i++) {
                    if (staffArray[i].contains("GRANDBLANCSCHOOLS.ORG")) {
                        staffArray[i] = StringUtils.substringAfter(staffArray[i], "ORG");
                        emailArray[i] = StringUtils.substringAfter(emailArray[i], "mailto:");
                        emailArray[i] = StringUtils.substringBefore(emailArray[i], ">");

                        staffList.add(staffCount, staffArray[i].substring(2, staffArray[i].length()-10) + "\n" + emailArray[i].substring(0, emailArray[i].length()-1));
                        //staffList.add(staffCount, staffArray[i].substring(161, staffArray[i].length() - 10));
                        staffCount++;
                        
                    }
                }
            }catch (IOException e) {
                final Context context = getActivity().getApplicationContext();
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
                prog.setVisibility(View.GONE);

                //Set adapter
                StaffAdapter adapter = new StaffAdapter();

                for (int i = 0; i < staffList.size(); i++) {
                    adapter.addItem(staffList.get(i));
                }

                lstStaff.setAdapter(adapter);
            }


        }


    //Adapter class
    private class StaffAdapter extends BaseAdapter {
        private static final int TYPE_ITEM = 0;

        private ArrayList<String> mData = new ArrayList<>();
        private LayoutInflater mInflater;

        public StaffAdapter() {
            mInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public void addItem(final String item) {
            mData.add(item);
            notifyDataSetChanged();
        }

        @Override
        public int getItemViewType(int position) {
            return TYPE_ITEM;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public String getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            int type = getItemViewType(position);
            holder = new ViewHolder();
            switch (type) {
                case TYPE_ITEM:
                    convertView = mInflater.inflate(R.layout.stafflist, parent, false);
                    holder.textView = (TextView) convertView.findViewById(R.id.text);
                    break;
            }
            convertView.setTag(holder);
            holder.textView.setText(mData.get(position));
            return convertView;
        }
    }

    public static class ViewHolder {
        public TextView textView;
    }
}
