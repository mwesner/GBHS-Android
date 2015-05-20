package com.grandblanchs.gbhs;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

public class AppDialog {

    private Activity mActivity;

    int type;

    public AppDialog(Activity context, int t) {
        mActivity = context;
        type = t;
    }

    public void show() {

        if (type == 0) { //Grades
            AlertDialog.Builder builder = new AlertDialog.Builder(mActivity)
                    .setTitle(R.string.Grades)
                    .setIcon(R.drawable.svue)
                            //.setIcon(R.drawable.jed) //TODO: Jupiter Ed
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
        }else if (type == 1) { //Facebook
            AlertDialog.Builder builder = new AlertDialog.Builder(mActivity)
                    .setTitle(R.string.Facebook)
                    .setIcon(R.drawable.facebook)
                    .setPositiveButton(R.string.openapp, new Dialog.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Uri uri;
                            try {
                                mActivity.getPackageManager()
                                        .getPackageInfo("com.facebook.katana", 0);
                                uri = Uri.parse("fb://page/" + mActivity.getString(R.string.FacebookID));
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                mActivity.startActivity(intent);
                            } catch (PackageManager.NameNotFoundException e) {
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse("market://details?id=com.facebook.katana"));
                                mActivity.startActivity(intent);
                            }
                        }
                    })
                    .setNegativeButton(R.string.openweb, new Dialog.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://facebook.com/GrandBlancHighSchool"));
                            mActivity.startActivity(browserIntent);
                        }
                    });
            builder.create().show();
        }

    }
}
