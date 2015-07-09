package com.grandblanchs.gbhs;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;

public class TwitterFragment extends ListFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_twitter, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final SwipeRefreshLayout swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_layout);
        final UserTimeline timeline = new UserTimeline.Builder().screenName("GrandBlancPride").build();
        final TweetTimelineListAdapter adapter = new TweetTimelineListAdapter(getActivity(), timeline);
        setListAdapter(adapter);

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @SuppressWarnings("unchecked")
            @Override
            public void onRefresh() {
                swipeLayout.setRefreshing(true);
                adapter.refresh(new Callback() {
                    @Override
                    public void success(Result result) {
                        swipeLayout.setRefreshing(false);
                    }

                    @Override
                    public void failure(TwitterException exception) {
                        swipeLayout.setRefreshing(false);
                        Snackbar.make(getView(), getString(R.string.NoTwitterRefresh), Snackbar.LENGTH_LONG)
                                .setAction(R.string.Retry, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        onRefresh();
                                    }
                                })
                                .show();
                    }
                });
            }
        });
    }
}