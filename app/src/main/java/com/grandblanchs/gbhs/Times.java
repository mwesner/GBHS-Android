package com.grandblanchs.gbhs;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Times extends Fragment {

    //TODO: Resolve an issue with reloading tab content.

    public interface OnFragmentInteractionListener {}

    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_times, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
            CharSequence Titles[] = getResources().getStringArray(R.array.times);
            int tabcount = 3;

            adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager(), Titles, tabcount);

            pager = (ViewPager) view.findViewById(R.id.pager);
            pager.setAdapter(adapter);

            tabs = (SlidingTabLayout) view.findViewById(R.id.tabs);
            tabs.setDistributeEvenly(true);

            tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
                @Override
                public int getIndicatorColor(int position) {
                    return getResources().getColor(R.color.tabsScrollColor);
                }
            });

            tabs.setViewPager(pager);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        super.onDestroy();
        if (Build.VERSION.SDK_INT >= 21) {
            GBHS.toolbar.setElevation(4);
        }
    }
}
