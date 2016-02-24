package com.grandblanchs.gbhs;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AlertDialog;

class FacebookDialog {

    private final Activity mActivity;

    FacebookDialog(Activity context) {
        mActivity = context;
    }

    public void show() {

        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity)
                .setTitle(R.string.Facebook)
                .setIcon(R.drawable.icon_facebook)
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
