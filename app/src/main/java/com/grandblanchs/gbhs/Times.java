package com.grandblanchs.gbhs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Times extends Fragment {

    public interface OnFragmentInteractionListener {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.times, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewPager pager;
        ViewPagerAdapter adapter;
        SlidingTabLayout tabs;
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
}
