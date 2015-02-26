package com.grandblanchs.gbhs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

public class Grades {

    private Activity mActivity;

    public Grades(Activity context) {
        mActivity = context;
    }

    public void show() {

        // Show the schedule
        String title = "Check Grades";

        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity)
                .setTitle(title)
                .setIcon(R.drawable.svue)
                .setPositiveButton(R.string.studentvue, new Dialog.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        PackageManager pm = mActivity.getPackageManager();
                        Intent appStartIntent = pm.getLaunchIntentForPackage("com.FreeLance.StudentVUE");
                        if (appStartIntent != null) {
                            mActivity.startActivity(appStartIntent);
                        }else{
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse("market://details?id=com.FreeLance.StudentVUE"));
                            mActivity.startActivity(intent);
                        }
                    }
                })
                .setNegativeButton(R.string.synergy, new Dialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://studentvue.geneseeisd.org/GBCS/Login_Student_PXP.aspx"));
                        mActivity.startActivity(browserIntent);
                    }
                });
        builder.create().show();
    }
}
