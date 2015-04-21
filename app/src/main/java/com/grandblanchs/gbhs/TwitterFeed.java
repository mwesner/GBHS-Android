package com.grandblanchs.gbhs;

import android.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;

public class TwitterFeed extends ListFragment {

    public interface OnFragmentInteractionListener{}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final UserTimeline userTimeline = new UserTimeline.Builder()
                .screenName("GrandBlancPride")
                .build();


        final TweetTimelineListAdapter adapter = new TweetTimelineListAdapter(getActivity(),
                userTimeline);
        setListAdapter(adapter);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.twitter, container, false);
    }
}

