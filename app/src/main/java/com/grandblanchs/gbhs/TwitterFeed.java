package com.grandblanchs.gbhs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;

import io.fabric.sdk.android.Fabric;

public class TwitterFeed extends Fragment {

    public interface OnFragmentInteractionListener{}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.twitter, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView timeline = (ListView) view.findViewById(R.id.timelinelist);

        final UserTimeline userTimeline = new UserTimeline.Builder()
                .screenName("GrandBlancPride")
                .build();


        final TweetTimelineListAdapter adapter = new TweetTimelineListAdapter(getActivity(),
                userTimeline);
        timeline.setAdapter(adapter);
    }
}

