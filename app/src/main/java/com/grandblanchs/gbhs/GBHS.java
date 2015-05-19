package com.grandblanchs.gbhs;

import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.crashlytics.android.Crashlytics;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import java.util.Random;

import io.fabric.sdk.android.Fabric;

public class GBHS extends AppCompatActivity implements
        TwitterFeed.OnFragmentInteractionListener, Calendar.OnFragmentInteractionListener,
        Map.OnFragmentInteractionListener, Announce.OnFragmentInteractionListener,
        Admin.OnFragmentInteractionListener, Times.OnFragmentInteractionListener,
        Staff.OnFragmentInteractionListener, External.OnFragmentInteractionListener,
        College.OnFragmentInteractionListener, Guidance.OnFragmentInteractionListener,
        Athletics.OnFragmentInteractionListener, NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    static Toolbar toolbar;

    CharSequence prevTitle;
    FragmentManager fm;

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

    //Your consumer key and secret should be obfuscated in your source code before shipping.
    //TODO: Recreate these keys in the release version.
    private static final String TWITTER_KEY = "ZJnydLJQ8PPjT8hxt5znyscnj";
    private static final String TWITTER_SECRET = "gzSfM0fG4fFaHuQUb46SvWEM30v9XkJih0RVJiB3nEisDZfICV";

    //Create FragmentManager for switching fragments
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gbhs);

        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Crashlytics(), new Twitter(authConfig));

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.fragment_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer), toolbar);
        // populate the navigation drawer
        mNavigationDrawerFragment.setUserData(
                getRandomHeaderBackground(),
                ResourcesCompat.getDrawable(getResources(), R.drawable.drawer_header, null));
    }

    public int getRandomHeaderBackground() {
        //Randomize the header image.
        Random rand = new Random();

        switch (rand.nextInt(4)) {
            default: //Required
            case 0:
                return R.drawable.flag;
            case 1:
                return R.drawable.hswest;
            case 2:
                return R.drawable.hsfinal1;
            case 3:
                return R.drawable.gbgym;
        }
    }

    public void onNavigationDrawerItemSelected(int position) {
        fm = getSupportFragmentManager();
        prevTitle = getTitle();

        switch (position + 1) {
            case 1:
                setTitle(R.string.Announce);
                fm.beginTransaction()
                        .setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out)
                        .replace(R.id.FragmentContainer, announceFrag)
                        .addToBackStack(null)
                        .commit();
                break;
            case 2:
                setTitle(R.string.Map);
                fm.beginTransaction()
                        .setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out,
                                R.anim.abc_fade_in, R.anim.abc_fade_out)
                        .replace(R.id.FragmentContainer, mapFrag)
                        .addToBackStack(null)
                        .commit();
                break;
            case 3:
                setTitle(R.string.Twitter);
                fm.beginTransaction()
                        .setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out,
                                R.anim.abc_fade_in, R.anim.abc_fade_out)
                        .replace(R.id.FragmentContainer, twitterFrag)
                        .addToBackStack(null)
                        .commit();
                break;
            case 4:
                setTitle(R.string.Calendar);
                fm.beginTransaction()
                        .setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out,
                                R.anim.abc_fade_in, R.anim.abc_fade_out)
                        .replace(R.id.FragmentContainer, calFrag)
                        .addToBackStack(null)
                        .commit();
                break;
            case 5:
                setTitle(R.string.Athletics);
                fm.beginTransaction()
                        .setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out,
                                R.anim.abc_fade_in, R.anim.abc_fade_out)
                        .replace(R.id.FragmentContainer, athleticsFrag)
                        .addToBackStack(null)
                        .commit();
                break;
            case 6:
                setTitle(R.string.College);
                fm.beginTransaction()
                        .setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out,
                                R.anim.abc_fade_in, R.anim.abc_fade_out)
                        .replace(R.id.FragmentContainer, collegeFrag)
                        .addToBackStack(null)
                        .commit();
                break;
            case 7:
                setTitle(R.string.Staff);
                fm.beginTransaction()
                        .setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out,
                                R.anim.abc_fade_in, R.anim.abc_fade_out)
                        .replace(R.id.FragmentContainer, staffFrag)
                        .addToBackStack(null)
                        .commit();
                break;
            case 8:
                setTitle(R.string.Guidance);
                fm.beginTransaction()
                        .setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out,
                                R.anim.abc_fade_in, R.anim.abc_fade_out)
                        .replace(R.id.FragmentContainer, guidanceFrag)
                        .addToBackStack(null)
                        .commit();
                break;
            case 9:
                setTitle(R.string.Admin);
                fm.beginTransaction()
                        .setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out,
                                R.anim.abc_fade_in, R.anim.abc_fade_out)
                        .replace(R.id.FragmentContainer, adminFrag)
                        .addToBackStack(null)
                        .commit();
                break;
            case 10:
                setTitle(R.string.External);
                fm.beginTransaction()
                        .setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out,
                                R.anim.abc_fade_in, R.anim.abc_fade_out)
                        .replace(R.id.FragmentContainer, externalFrag)
                        .addToBackStack(null)
                        .commit();
                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.gbhs, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        fm = getSupportFragmentManager();

        switch (item.getItemId()) {
            case R.id.Times:
                if (Build.VERSION.SDK_INT >= 21) {
                    toolbar.setElevation(0);
                }
                setTitle(R.string.Schedule);
                fm.beginTransaction()
                        .setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out,
                                R.anim.abc_fade_in, R.anim.abc_fade_out)
                        .replace(R.id.FragmentContainer, timesFrag)
                        .addToBackStack(null)
                        .commit();
                return true;
            case R.id.Grades:
                new Grades(this).show();
                return true;
            case R.id.Facebook:
                Uri uri;
                String url = "https://www.facebook.com/GrandBlancHighSchool";
                try {
                    getPackageManager().getPackageInfo("com.facebook.katana", 0);
                    uri = Uri.parse("fb://page/" + getString(R.string.FacebookID));
                } catch (PackageManager.NameNotFoundException e) {
                    uri = Uri.parse(url);
                }
                Intent i = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(i);
                return true;
            case R.id.GBHSWeb:
                Intent w = new Intent(Intent.ACTION_VIEW, Uri.parse("http://grandblanc.high.schoolfusion.us"));
                startActivity(w);
                return true;
            case R.id.About:
                new About(this).show();
                return true;
        }

        prevTitle = getTitle();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen()) {
            mNavigationDrawerFragment.closeDrawer();
        }else if (fm.getBackStackEntryCount() > 1){
            // Catch back action and pops from backstack
            // (if you called previously to addToBackStack() in your transaction)
            fm.popBackStack();
            setTitle(prevTitle);
        } else {
            // Default action on back pressed
            super.onBackPressed();
        }
    }
}
