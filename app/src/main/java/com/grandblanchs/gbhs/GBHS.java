package com.grandblanchs.gbhs;

import android.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;


public class GBHS extends Activity implements twitter.OnFragmentInteractionListener,
        calendar.OnFragmentInteractionListener, announce.OnFragmentInteractionListener, NHS.OnFragmentInteractionListener,
        Lib.OnFragmentInteractionListener, Admin.OnFragmentInteractionListener {
    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    //Create FragmentManager for switching fragments
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gbhs);
        System.out.println("Starting");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        System.out.println("onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.gbhs, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        twitter twitterfragment = new twitter();
        calendar calfragment = new calendar();
        announce announcefragment = new announce();
        NHS nhsfragment = new NHS();
        Lib libfragment = new Lib();
        Admin adminfragment = new Admin();

        if (id == R.id.twitter) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.FragmentContainer, twitterfragment);
            fragmentTransaction.commit();
            return true;
        }
        if (id == R.id.calendar) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.FragmentContainer, calfragment);
            fragmentTransaction.commit();
            return true;
        }
        if (id == R.id.announce) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.FragmentContainer, announcefragment);
            fragmentTransaction.commit();
            return true;
        }
        if (id == R.id.NHS) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.FragmentContainer, nhsfragment);
            fragmentTransaction.commit();
            return true;
        }
        if (id == R.id.Lib) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.FragmentContainer, libfragment);
            fragmentTransaction.commit();
            return true;
        }

        if (id == R.id.Admin) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.FragmentContainer, adminfragment);
            fragmentTransaction.commit();
            return true;
        }



        return super.onOptionsItemSelected(item);
    }


}
