package com.grandblanchs.gbhs;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    CharSequence Titles[];
    int tabcount;

    public ViewPagerAdapter(FragmentManager fm,CharSequence mTitles[], int tabs) {
        super(fm);

        this.Titles = mTitles;
        this.tabcount = tabs;

    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            default: //Required
            case 0:
                return new TimesFull();
            case 1:
                return new TimesHalf();
            case 2:
                return new TimesLate();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    @Override
    public int getCount() {
        return tabcount;
    }
}
