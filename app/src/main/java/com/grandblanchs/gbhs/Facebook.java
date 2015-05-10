package com.grandblanchs.gbhs;

import android.support.v4.app.Fragment;
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

import java.util.ArrayList;

import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.Post;
import facebook4j.ResponseList;
import facebook4j.auth.AccessToken;

public class Facebook extends Fragment {

    public interface OnFragmentInteractionListener{}

    //Declaration of ListView lst_feed, so it can be referenced throughout Facebook.java
    ListView lst_feed;
    ProgressBar prog;

    //Creates a string array of length 20 for the twenty facebook posts to be stored in.
    //This is needed because it's a lot easier to populate a ListView with a string array
    String [] posts = new String[20];

    public Facebook() {
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
        return inflater.inflate(R.layout.facebook, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prog = (ProgressBar) view.findViewById(R.id.progFacebook);

        //Finds the ListView lst_feed on the GUI form
        lst_feed = (ListView) view.findViewById(R.id.list);
    }

    @Override
    public void onStart() {
        super.onStart();
        //Since internet dependant tasks cannot be performed on the main method, we execute a new one called FacebookTimeline
        new FacebookTimeline().execute();
    }

    private class FacebookTimeline extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            //A try-catch for the method
            try {
                //Authenticates this app with my specific account codes. This will need to be changed to GBHS Facebook account codes.
                // Generate facebook instance.
                facebook4j.Facebook facebook = new FacebookFactory().getInstance();
                // Use default values for oauth app id.
                facebook.setOAuthAppId(getString(R.string.facebook_app_id), getString(R.string.facebook_secret));
                // Get an access token from:
                // https://developers.facebook.com/tools/explorer
                String accessTokenString = getString(R.string.facebook_access_token);
                AccessToken at = new AccessToken(accessTokenString);
                // Set access token.
                facebook.setOAuthAccessToken(at);

                //Retrieves the last 20 posts from the user 'GrandBlancHighSchool' and puts them into a responseList
                ResponseList<Post> userposts = facebook.getPosts();
                //This for loop takes the text from every member of this response list and moves it to the string array
                for (int i = 0; i < userposts.size(); i++) {
                    posts[i] = userposts.get(i).getMessage();
                }

                final FacebookAdapter adapter = new FacebookAdapter();

                for (String post: posts) {
                    adapter.addItem(post);
                }

                //Since the UI cannot be changed without this,
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Populates lst_feed with string array testing
                        lst_feed.setAdapter(adapter);
                    }
                });

            } catch (final FacebookException e) {
                final Context context = getActivity().getApplicationContext();
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(context, getString(R.string.NoFacebook) + " " + e.getErrorCode() + ")", Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            prog.setVisibility(View.GONE);
        }
    }

    //FacebookAdapter class
    private class FacebookAdapter extends BaseAdapter {
        private static final int TYPE_ITEM = 0;

        private ArrayList<String> mData = new ArrayList<>();
        private LayoutInflater mInflater;

        public FacebookAdapter() {
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
                    convertView = mInflater.inflate(R.layout.itemlist, parent, false);
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