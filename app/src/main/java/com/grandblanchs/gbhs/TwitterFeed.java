package com.grandblanchs.gbhs;

import android.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;

import io.fabric.sdk.android.Fabric;

public class TwitterFeed extends ListFragment {

    public interface OnFragmentInteractionListener{}

    //Your consumer key and secret should be obfuscated in your source code before shipping.
    //TODO: Recreate these keys in the release version.
    private static final String TWITTER_KEY = "ZJnydLJQ8PPjT8hxt5znyscnj";
    private static final String TWITTER_SECRET = "gzSfM0fG4fFaHuQUb46SvWEM30v9XkJih0RVJiB3nEisDZfICV";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(getActivity(), new Twitter(authConfig));

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

