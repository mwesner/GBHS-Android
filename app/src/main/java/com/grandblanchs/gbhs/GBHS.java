package com.grandblanchs.gbhs;

import android.app.FragmentManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.widget.Toast;


public class GBHS extends Activity implements twitter.OnFragmentInteractionListener,
        calendar.OnFragmentInteractionListener, announce.OnFragmentInteractionListener,
        Admin.OnFragmentInteractionListener, staff.OnFragmentInteractionListener {

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    //Create FragmentManager for switching fragments
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gbhs);
        //Toast
        Toast.makeText(getApplicationContext(), "Hello GB!", Toast.LENGTH_LONG).show();
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
        int id = item.getItemId();
        twitter twitterfragment = new twitter();
        calendar calfragment = new calendar();
        announce announcefragment = new announce();
        Admin adminfragment = new Admin();
        staff stafffragment = new staff();

        if (id == R.id.Home) {
            finish();
            Intent start = new Intent(getApplicationContext(), GBHS.class);
            startActivity(start);
        }
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
            String url = "http://gbhsnhs.com";
            Intent i = new Intent(Intent.ACTION_VIEW);
            Uri u = Uri.parse(url);
            Context context = this;

            try {
                //Start the activity
                i.setData(u);
                startActivity(i);
            }catch (ActivityNotFoundException e) {
                //Raise on activity not found
                Toast.makeText(context, "No browser found.", Toast.LENGTH_SHORT).show();
            }
        }
        if (id == R.id.Lib) {
            String url = "http://gbhslib.weebly.com";
            Intent i = new Intent(Intent.ACTION_VIEW);
            Uri u = Uri.parse(url);
            Context context = this;

            try {
                //Start the activity
                i.setData(u);
                startActivity(i);
            } catch (ActivityNotFoundException e) {
                //Raise on activity not found
                Toast.makeText(context, "No browser found.", Toast.LENGTH_SHORT).show();
            }
        }

        if (id == R.id.Admin) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.FragmentContainer, adminfragment);
            fragmentTransaction.commit();
            return true;
        }

        if (id == R.id.staff) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.FragmentContainer, stafffragment);
            fragmentTransaction.commit();
            return true;
        }



        return super.onOptionsItemSelected(item);
    }


}
