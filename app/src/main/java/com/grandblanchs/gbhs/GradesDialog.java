package com.grandblanchs.gbhs;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AlertDialog;

class GradesDialog {

    private final Activity mActivity;

    GradesDialog(Activity context) {
        mActivity = context;
    }

    public void show() {

        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity)
                .setTitle(R.string.Grades)
                //.setIcon(R.drawable.icon_svue) //Student Vue
                        .setIcon(R.drawable.icon_jed)
                .setPositiveButton(R.string.openapp, new Dialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        PackageManager pm = mActivity.getPackageManager();
                        //Intent appStartIntent = pm.getLaunchIntentForPackage("com.FreeLance.StudentVUE"); //Student Vues
                        Intent appStartIntent = pm.getLaunchIntentForPackage("com.jupitered.jupitered");
                        if (appStartIntent != null) {
                            mActivity.startActivity(appStartIntent);
                        } else {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            //intent.setData(Uri.parse("market://details?id=com.FreeLance.StudentVUE")); //student vue
                            intent.setData(Uri.parse("market://details?id=com.jupitered.jupitered"));
                            mActivity.startActivity(intent);
                        }
                    }
                })
                .setNegativeButton(R.string.openweb, new Dialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://studentvue.geneseeisd.org/GBCS/Login_Student_PXP.aspx")); //student vue
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://login.jupitered.com/login"));
                        mActivity.startActivity(browserIntent);
                    }
                });
        builder.create().show();

    }
}
