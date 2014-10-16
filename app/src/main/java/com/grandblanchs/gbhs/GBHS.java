package com.grandblanchs.gbhs;

import android.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;


public class GBHS extends Activity implements twitter.OnFragmentInteractionListener {
    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    //Create FragmentManager for switching fragments
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gbhs);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        calendar calfragment = new calendar();
        fragmentTransaction.add(R.id.FragmentContainer, calfragment);
        fragmentTransaction.commit();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        System.out.println("onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.gbh, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        System.out.println("onOptionsItemSelected");
        int id = item.getItemId();
        twitter twitterfragment = new twitter();
        calendar calfragment = new calendar();
        if (id == R.id.twitter) {
            System.out.println("Switching to Twitter feed");
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.FragmentContainer, twitterfragment);
            fragmentTransaction.commit();
            return true;
        }
        if (id == R.id.calendar) {
            System.out.println("Switching to Calendar");
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.FragmentContainer, calfragment);
            fragmentTransaction.commit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
