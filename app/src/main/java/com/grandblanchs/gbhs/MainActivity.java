package com.grandblanchs.gbhs;

import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import java.util.Random;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {

    //TODO: (App-wide) Resolve any issues with savedInstanceState.

    DrawerLayout mDrawerLayout;

    FragmentManager fm;

    TwitterFragment twitterFrag = new TwitterFragment();
    MapFragment mapFrag = new MapFragment();
    CalendarFragment calFrag = new CalendarFragment();
    AnnounceFragment announceFrag = new AnnounceFragment();
    CollegeFragment collegeFrag = new CollegeFragment();
    AdminFragment adminFrag = new AdminFragment();
    StaffFragment staffFrag = new StaffFragment();
    ExternalFragment externalFrag = new ExternalFragment();
    GuidanceFragment guidanceFrag = new GuidanceFragment();
    SportsFragment sportsFrag = new SportsFragment();

    //Your consumer key and secret should be obfuscated in your source code before shipping.
    //TODO: (Wesner) Ensure these keys are replaced with the active release keys before publishing to Google Play.
    private static final String TWITTER_KEY = "ZJnydLJQ8PPjT8hxt5znyscnj";
    private static final String TWITTER_SECRET = "gzSfM0fG4fFaHuQUb46SvWEM30v9XkJih0RVJiB3nEisDZfICV";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);

        //Disable crashlytics for debug builds.
        if (BuildConfig.USE_CRASHLYTICS) {
            Fabric.with(this, new Crashlytics(), new Twitter(authConfig));
        } else {
            Fabric.with(this, new Twitter(authConfig));
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, 0, 0){

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerSlide(View drawerView, float drawerOffset) {
                super.onDrawerSlide(drawerView, 0);
            }
        };

        mDrawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        fm = getSupportFragmentManager();

        //Show the announcements
        setTitle(R.string.Announce);
        fm.beginTransaction()
                .setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out)
                .replace(R.id.FragmentContainer, announceFrag)
                .addToBackStack(null)
                .commit();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);

            navigationView.inflateHeaderView(getRandomHeaderLayout());

            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem menuItem) {

                    if (menuItem.isChecked()) {
                        menuItem.setChecked(false);
                    }else{
                        menuItem.setChecked(true);
                    }

                    mDrawerLayout.closeDrawers();

                    switch (menuItem.getItemId()) {
                        case R.id.Announce:
                            setTitle(R.string.Announce);
                            fm.beginTransaction()
                                    .setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out)
                                    .replace(R.id.FragmentContainer, announceFrag, "Announce")
                                    .addToBackStack(null)
                                    .commit();
                            return true;
                        case R.id.Map:
                            setTitle(R.string.Map);
                            fm.beginTransaction()
                                    .setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out,
                                            R.anim.abc_fade_in, R.anim.abc_fade_out)
                                    .replace(R.id.FragmentContainer, mapFrag, "Map")
                                    .addToBackStack(null)
                                    .commit();
                            return true;
                        case R.id.Twitter:
                            setTitle(R.string.Twitter);
                            fm.beginTransaction()
                                    .setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out,
                                            R.anim.abc_fade_in, R.anim.abc_fade_out)
                                    .replace(R.id.FragmentContainer, twitterFrag, "Twitter")
                                    .addToBackStack(null)
                                    .commit();
                            return true;
                        case R.id.Calendar:
                            setTitle(R.string.Calendar);
                            fm.beginTransaction()
                                    .setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out,
                                            R.anim.abc_fade_in, R.anim.abc_fade_out)
                                    .replace(R.id.FragmentContainer, calFrag, "Calendar")
                                    .addToBackStack(null)
                                    .commit();
                            return true;
                        case R.id.Sports:
                            setTitle(R.string.Sports);
                            fm.beginTransaction()
                                    .setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out,
                                            R.anim.abc_fade_in, R.anim.abc_fade_out)
                                    .replace(R.id.FragmentContainer, sportsFrag, "Sports")
                                    .addToBackStack(null)
                                    .commit();
                            return true;
                        case R.id.College:
                            setTitle(R.string.College);
                            fm.beginTransaction()
                                    .setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out,
                                            R.anim.abc_fade_in, R.anim.abc_fade_out)
                                    .replace(R.id.FragmentContainer, collegeFrag, "College")
                                    .addToBackStack(null)
                                    .commit();
                            return true;
                        case R.id.Staff:
                            setTitle(R.string.Staff);
                            fm.beginTransaction()
                                    .setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out,
                                            R.anim.abc_fade_in, R.anim.abc_fade_out)
                                    .replace(R.id.FragmentContainer, staffFrag, "Staff")
                                    .addToBackStack(null)
                                    .commit();
                            return true;
                        case R.id.Guidance:
                            setTitle(R.string.Guidance);
                            fm.beginTransaction()
                                    .setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out,
                                            R.anim.abc_fade_in, R.anim.abc_fade_out)
                                    .replace(R.id.FragmentContainer, guidanceFrag, "Guidance")
                                    .addToBackStack(null)
                                    .commit();
                            return true;
                        case R.id.Admin:
                            setTitle(R.string.Admin);
                            fm.beginTransaction()
                                    .setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out,
                                            R.anim.abc_fade_in, R.anim.abc_fade_out)
                                    .replace(R.id.FragmentContainer, adminFrag, "Admin")
                                    .addToBackStack(null)
                                    .commit();
                            return true;
                        case R.id.External:
                            setTitle(R.string.External);
                            fm.beginTransaction()
                                    .setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out,
                                            R.anim.abc_fade_in, R.anim.abc_fade_out)
                                    .replace(R.id.FragmentContainer, externalFrag, "External")
                                    .addToBackStack(null)
                                    .commit();
                            return true;
                    }
                    return true;
                }
            });
        }
    }

    public int getRandomHeaderLayout() {
        //Randomize the header image.
        Random rand = new Random();

        switch (rand.nextInt(4)) {
            default: //Required
            case 0:
                return R.layout.nav_header_flag;
            case 1:
                return R.layout.nav_header_hswest;
            case 2:
                return R.layout.nav_header_hsfinal1;
            case 3:
                return R.layout.nav_header_gbgym;
        }
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
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
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.Times:
                Intent i = new Intent(this, TimesActivity.class);
                startActivity(i);
                return true;
            case R.id.Grades:
                new GradesDialog(this).show();
                return true;
            case R.id.Facebook:
                new FacebookDialog(this).show();
                return true;
            case R.id.GBHSWeb:
                Intent w = new Intent(Intent.ACTION_VIEW, Uri.parse("http://grandblanc.high.schoolfusion.us"));
                startActivity(w);
                return true;
            case R.id.About:
                Intent a = new Intent(this, AboutActivity.class);
                startActivity(a);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }else if (fm.getBackStackEntryCount() > 0){
            // Catch back action and pops from backstack
            // (if you called previously to addToBackStack() in your transaction)
           // restoreChecked();
            fm.popBackStack();
            restoreTitle();
        } else {
            // Default action on back pressed
            super.onBackPressed();
        }
    }

    public void restoreTitle() {
        for (int i = 0; i < fm.getFragments().size(); i++) {
            Toast.makeText(this, fm.getFragments().get(i).getId(), Toast.LENGTH_LONG).show();
        }
    }
}
