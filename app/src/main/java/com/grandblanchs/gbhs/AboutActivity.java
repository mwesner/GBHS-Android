package com.grandblanchs.gbhs;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    Resources res;

    String[] SYD; //Second year developer names
    String[] FYD; //First year developer names

    TextView secondyear1;
    TextView secondyear2;

    TextView firstyear1;
    TextView firstyear2;
    TextView firstyear3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }

        PackageInfo versionInfo = getPackageInfo();
        TextView txtVersion = (TextView) findViewById(R.id.txtVersion);
        txtVersion.setText(getString(R.string.Version) + versionInfo.versionName);

        //Make CTE link clickable
        TextView dev = (TextView) findViewById(R.id.txtDev);
        dev.setMovementMethod(LinkMovementMethod.getInstance());

        res = getResources();

        SYD = res.getStringArray(R.array.SecondYearDevs);
        FYD = res.getStringArray(R.array.FirstYearDevs);

        secondyear1 = (TextView) findViewById(R.id.txtSYD1);
        secondyear2 = (TextView) findViewById(R.id.txtSYD2);

        firstyear1 = (TextView) findViewById(R.id.txtFYD1);
        firstyear2 = (TextView) findViewById(R.id.txtFYD2);
        firstyear3 = (TextView) findViewById(R.id.txtFYD3);

        secondyear1.setText(SYD[0]);
        secondyear2.setText(SYD[1]);

        firstyear1.setText(FYD[0]);
        firstyear2.setText(FYD[1]);
        firstyear3.setText(FYD[2]);
    }

    private PackageInfo getPackageInfo() {
        PackageInfo pi = null;
        try {
            pi = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            //Leave txtVersion blank if the version cannot be retrieved.
        }
        return pi;
    }
}
