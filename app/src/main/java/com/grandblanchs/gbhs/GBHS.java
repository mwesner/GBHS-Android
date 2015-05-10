package com.grandblanchs.gbhs;

import android.annotation.SuppressLint;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

public class GBHS extends AppCompatActivity implements
        TwitterFeed.OnFragmentInteractionListener, Calendar.OnFragmentInteractionListener,
        Map.OnFragmentInteractionListener, Announce.OnFragmentInteractionListener,
        Facebook.OnFragmentInteractionListener,Admin.OnFragmentInteractionListener,
        Times.OnFragmentInteractionListener, Staff.OnFragmentInteractionListener,
        External.OnFragmentInteractionListener, College.OnFragmentInteractionListener,
        Guidance.OnFragmentInteractionListener, Athletics.OnFragmentInteractionListener {


    CharSequence prevTitle;
    public static FragmentManager fm;
    FragmentTransaction ft;

    String TITLES[];
    int ICONS[];

    RecyclerView mRecyclerView;                           // Declaring RecyclerView
    RecyclerView.Adapter mAdapter;                        // Declaring Adapter for Recycler View
    RecyclerView.LayoutManager mLayoutManager;            // Declaring Layout Manager as a linear layout manager
    static DrawerLayout Drawer;                                  // Declaring DrawerLayout

    ActionBarDrawerToggle mDrawerToggle;                  // Declaring Action Bar Drawer Toggle

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

        TITLES = getResources().getStringArray(R.array.navdrawer);

        ICONS = new int[]{
                R.drawable.drawer_announce,
                R.drawable.drawer_svue,
                //R.drawable.drawer_jed, //TODO: Jupiter Ed
                R.drawable.ic_action_access_time,
                R.drawable.ic_action_map,
                R.drawable.drawer_facebook,
                R.drawable.drawer_twitter,
                R.drawable.ic_action_event,
                R.drawable.drawer_athletics,
                R.drawable.drawer_gbec,
                R.drawable.ic_action_quick_contacts_mail,
                R.drawable.ic_action_people,
                R.drawable.ic_action_school,
                R.drawable.ic_action_exit_to_app};

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView); // Assigning the RecyclerView Object to the xml View

        mRecyclerView.setHasFixedSize(true);                            // Letting the system know that the list objects are of fixed size

        mAdapter = new NavDrawerAdapter(
                TITLES,
                ICONS,
                this);

        mRecyclerView.setAdapter(mAdapter);                              // Setting the adapter to RecyclerView

        mLayoutManager = new LinearLayoutManager(this);                 // Creating a layout Manager

        mRecyclerView.setLayoutManager(mLayoutManager);                 // Setting the layout Manager

        Drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);        // Drawer object Assigned to the view
        mDrawerToggle = new ActionBarDrawerToggle(this, Drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, 0); // this disables the animation
            }

        }; // Drawer Toggle Object Made
        Drawer.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        onSectionAttached(1); //Show the announcements fragment

        final GestureDetector mGestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

        });

        mGestureDetector.setIsLongpressEnabled(true);


        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());


                if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {
                    Drawer.closeDrawers();
                    onSectionAttached(recyclerView.getChildAdapterPosition(child));

                    mRecyclerView.setBackgroundColor(getResources().getColor(R.color.ColorPrimary));

                    return true;

                }

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {

            }
        });
    }

    @SuppressLint("CommitTransaction")
    public void onSectionAttached(int number) {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        prevTitle = getTitle();

        switch (number) {
            case 1:
                setTitle(R.string.Announce);
                ft.replace(R.id.FragmentContainer, announceFrag)
                        .addToBackStack(null)
                        .commit();
                break;
            case 2:
                new Grades(this).show();
                break;
            case 3:
                setTitle(R.string.Schedule);
                ft.replace(R.id.FragmentContainer, timesFrag)
                        .addToBackStack(null)
                        .commit();
                break;
            case 4:
                setTitle(R.string.Map);
                ft.replace(R.id.FragmentContainer, mapFrag)
                        .addToBackStack(null)
                        .commit();
                break;
            case 5:
                setTitle(R.string.Facebook);
                ft.replace(R.id.FragmentContainer, facebookFrag)
                        .addToBackStack(null)
                        .commit();
                break;
            case 6:
                setTitle(R.string.Twitter);
                ft.replace(R.id.FragmentContainer, twitterFrag)
                        .addToBackStack(null)
                        .commit();
                break;
            case 7:
                setTitle(R.string.Calendar);
                ft.replace(R.id.FragmentContainer, calFrag)
                        .addToBackStack(null)
                        .commit();
                break;
            case 8:
                setTitle(R.string.Athletics);
                ft.replace(R.id.FragmentContainer, athleticsFrag)
                        .addToBackStack(null)
                        .commit();
                break;
            case 9:
                setTitle(R.string.College);
                ft.replace(R.id.FragmentContainer, collegeFrag)
                        .addToBackStack(null)
                        .commit();
                break;
            case 10:
                setTitle(R.string.Staff);
                ft.replace(R.id.FragmentContainer, staffFrag)
                        .addToBackStack(null)
                        .commit();
                break;
            case 11:
                setTitle(R.string.Guidance);
                ft.replace(R.id.FragmentContainer, guidanceFrag)
                        .addToBackStack(null)
                        .commit();
                break;
            case 12:
                setTitle(R.string.Admin);
                ft.replace(R.id.FragmentContainer, adminFrag)
                        .addToBackStack(null)
                        .commit();
                break;
            case 13:
                setTitle(R.string.External);
                ft.replace(R.id.FragmentContainer, externalFrag)
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
                setTitle(R.string.Schedule);
                onSectionAttached(3);
                return true;
            case R.id.Grades:
                new Grades(this).show();
                return true;
            case R.id.Announce:
                setTitle(R.string.Announce);
                onSectionAttached(1);
                return true;
            case R.id.Facebook:
                setTitle(R.string.Facebook);
                onSectionAttached(5);
                return true;
            case R.id.Twitter:
                setTitle(R.string.Twitter);
                onSectionAttached(6);
                return true;
            case R.id.Calendar:
                setTitle(R.string.Calendar);
                onSectionAttached(7);
                return true;
            case R.id.GBHSWeb:
                Intent w = new Intent(Intent.ACTION_VIEW, Uri.parse("http://grandblanc.high.schoolfusion.us"));
                startActivity(w);
                return true;
            case R.id.About:
                new About(this).show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
        // Catch back action and pops from backstack
        // (if you called previously to addToBackStack() in your transaction)
        if (fm.getBackStackEntryCount() > 1){
            fm.popBackStack();
            setTitle(prevTitle);
        }
        // Default action on back pressed
        else super.onBackPressed();
    }
}
