package com.grandblanchs.gbhs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

public class About {

    private Activity mActivity;

    public About(Activity context) {
        mActivity = context;
    }

    public void show() {

        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity)
                .setTitle(R.string.app_name)
                .setIcon(R.drawable.ic_launcher)
                .setMessage("Sponsored by Grand Blanc Schools CTE" + "\n\n"
                                + mActivity.getString(R.string.CTEWeb
                        ))
                .setPositiveButton("Close", new Dialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        builder.create().show();
    }
}
