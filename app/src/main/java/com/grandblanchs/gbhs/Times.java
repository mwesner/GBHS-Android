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
import android.widget.Toast;

public class Times extends Fragment {

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
            Toast.makeText(getActivity(), "onViewCreated", Toast.LENGTH_SHORT).show();
            CharSequence Titles[] = getResources().getStringArray(R.array.times);
            int tabcount = 3;

            adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager(), Titles, tabcount);
        Toast.makeText(getActivity(), "adapter created", Toast.LENGTH_SHORT).show();

            pager = (ViewPager) view.findViewById(R.id.pager);
            pager.setAdapter(adapter);

        Toast.makeText(getActivity(), "adapter set", Toast.LENGTH_SHORT).show();

            tabs = (SlidingTabLayout) view.findViewById(R.id.tabs);
            tabs.setDistributeEvenly(true);

            tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
                @Override
                public int getIndicatorColor(int position) {
                    return getResources().getColor(R.color.tabsScrollColor);
                }
            });

            tabs.setViewPager(pager);

        Toast.makeText(getActivity(), "pager set", Toast.LENGTH_SHORT).show();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        super.onDestroy();

        GBHS.toolbar.setElevation(4);
    }
}
