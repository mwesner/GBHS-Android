package com.grandblanchs.gbhs;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AlertDialog;

public class GradesDialog {

    private Activity mActivity;

    public GradesDialog(Activity context) {
        mActivity = context;
    }

    public void show() {

        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity)
                .setTitle(R.string.Grades)
                .setIcon(R.drawable.icon_svue)
                //.setIcon(R.drawable.icon_jed) //TODO: Jupiter Ed
                .setPositiveButton(R.string.openapp, new Dialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        PackageManager pm = mActivity.getPackageManager();
                        Intent appStartIntent = pm.getLaunchIntentForPackage("com.FreeLance.StudentVUE");
                        //Intent appStartIntent = pm.getLaunchIntentForPackage("com.jupitered.jupitered"); //TODO: Jupiter Ed
                        if (appStartIntent != null) {
                            mActivity.startActivity(appStartIntent);
                        } else {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse("market://details?id=com.FreeLance.StudentVUE"));
                            //intent.setData(Uri.parse("market://details?id=com.jupitered.jupitered")); //TODO: Jupiter Ed
                            mActivity.startActivity(intent);
                        }
                    }
                })
                .setNegativeButton(R.string.openweb, new Dialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://studentvue.geneseeisd.org/GBCS/Login_Student_PXP.aspx"));
                        //Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://login.jupitered.com/login")); //TODO: Jupiter Ed
                        mActivity.startActivity(browserIntent);
                    }
                });
        builder.create().show();

    }
}
