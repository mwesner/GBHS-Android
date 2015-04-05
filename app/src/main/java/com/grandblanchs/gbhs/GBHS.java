package com.grandblanchs.gbhs;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


public class GBHS extends ActionBarActivity implements Home.OnFragmentInteractionListener,
        TwitterFeed.OnFragmentInteractionListener, Calendar.OnFragmentInteractionListener,
        Announce.OnFragmentInteractionListener, Facebook.OnFragmentInteractionListener,
        Admin.OnFragmentInteractionListener, Times.OnFragmentInteractionListener,
        Map.OnFragmentInteractionListener, Staff.OnFragmentInteractionListener,
        External.OnFragmentInteractionListener, College.OnFragmentInteractionListener,
        Guidance.OnFragmentInteractionListener, Athletics.OnFragmentInteractionListener,
        NavigationDrawerFragment.NavigationDrawerCallbacks {

    //TODO: Continue managing fragment transactions

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    Home homeFrag = new Home();
    Facebook facebookFrag = new Facebook();
    TwitterFeed twitterFrag = new TwitterFeed();
    Times timesFrag = new Times();
    Map mapFrag = new Map();
    Calendar calFrag = new Calendar();
    Announce announceFrag = new Announce();
    College collegeFrag = new College();
    Admin adminFrag = new Admin();
    Staff staffFrag = new Staff();
    External externalFrag = new External();
    Guidance guidanceFrag = new Guidance();
    Athletics athleticsFrag = new Athletics();

    //Create FragmentManager for switching fragments
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gbhs);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        switch (number) {
            case 1:
                mTitle = getString(R.string.Home);
                fragmentTransaction.replace(R.id.FragmentContainer, homeFrag)
                    .addToBackStack(null)
                    .commit();
                break;
            case 2:
                mTitle = getString(R.string.Announce);
                fragmentTransaction.replace(R.id.FragmentContainer, announceFrag)
                        .addToBackStack(null)
                        .commit();
                break;
            case 3:
                new Grades(this).show();
                break;
            case 4:
                mTitle = getString(R.string.schedule);
                fragmentTransaction.replace(R.id.FragmentContainer, timesFrag)
                        .addToBackStack(null)
                        .commit();
                break;
            case 5:
                mTitle = getString(R.string.Map);
                fragmentTransaction.replace(R.id.FragmentContainer, mapFrag)
                        .addToBackStack(null)
                        .commit();
                break;
            case 6:
                mTitle = getString(R.string.Facebook);
                fragmentTransaction.replace(R.id.FragmentContainer, facebookFrag)
                        .addToBackStack(null)
                        .commit();
                break;
            case 7:
                mTitle = getString(R.string.Twitter);
                fragmentTransaction.replace(R.id.FragmentContainer, twitterFrag)
                        .addToBackStack(null)
                        .commit();
                break;
            case 8:
                mTitle = getString(R.string.Calendar);
                fragmentTransaction.replace(R.id.FragmentContainer, calFrag)
                        .addToBackStack(null)
                        .commit();
                break;
            case 9:
                mTitle = getString(R.string.Athletics);
                fragmentTransaction.replace(R.id.FragmentContainer, athleticsFrag)
                        .addToBackStack(null)
                        .commit();
                break;
            case 10:
                mTitle = getString(R.string.Early);
                fragmentTransaction.replace(R.id.FragmentContainer, collegeFrag)
                        .addToBackStack(null)
                        .commit();
                break;
            case 11:
                mTitle = getString(R.string.Staff);
                fragmentTransaction.replace(R.id.FragmentContainer, staffFrag)
                        .addToBackStack(null)
                        .commit();
                break;
            case 12:
                mTitle = getString(R.string.Guidance);
                fragmentTransaction.replace(R.id.FragmentContainer, guidanceFrag)
                        .addToBackStack(null)
                        .commit();
                break;
            case 13:
                mTitle = getString(R.string.Admin);
                fragmentTransaction.replace(R.id.FragmentContainer, adminFrag)
                        .addToBackStack(null)
                        .commit();
                break;
            case 14:
                mTitle = getString(R.string.external);
                fragmentTransaction.replace(R.id.FragmentContainer, externalFrag)
                        .addToBackStack(null)
                        .commit();
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.gbhs, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        
        switch (item.getItemId()) {
            case R.id.Times:
                setActionBarTitle(getString(R.string.schedule));
                onSectionAttached(4);
                return true;
            case R.id.StudentVUE:
                new Grades(this).show();
                return true;
            case R.id.Announce:
                setActionBarTitle(getString(R.string.Announce));
                onSectionAttached(2);
                return true;
            case R.id.Facebook:
                setActionBarTitle(getString(R.string.Facebook));
                onSectionAttached(6);
                return true;
            case R.id.Twitter:
                setActionBarTitle(getString(R.string.Twitter));
                onSectionAttached(7);
                return true;
            case R.id.Calendar:
                Intent c = new Intent(Intent.ACTION_VIEW, Uri.parse("http://grandblanc.schoolfusion.us/modules/groups/homepagefiles/cms/105549/File/District%20Calendar%202014-2015%208-19.pdf"));
                startActivity(c);
                return true;
            case R.id.GBHSWeb:
                Intent w = new Intent(Intent.ACTION_VIEW, Uri.parse("http://grandblanc.high.schoolfusion.us"));
                startActivity(w);
                return true;
            case R.id.Settings:
                Toast.makeText(getApplicationContext(), "No settings yet.", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.About:
                new About(this).show();
                return true;
        }

        restoreActionBar();
        return super.onOptionsItemSelected(item);
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    public void onBackPressed()
    {
        // Catch back action and pops from backstack
        // (if you called previously to addToBackStack() in your transaction)
        if (getFragmentManager().getBackStackEntryCount() > 1){
            getFragmentManager().popBackStack();
            restoreActionBar();
        }
        // Default action on back pressed
        else super.onBackPressed();
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_main, container, false);
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((GBHS) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }


    }
}
