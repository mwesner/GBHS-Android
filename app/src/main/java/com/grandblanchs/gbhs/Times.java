package com.grandblanchs.gbhs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;

public class Times {

    private Activity mActivity;

    public Times(Activity context) {
        mActivity = context;
    }

    public void show() {

        // Show the schedule
        String title = "Daily Schedule of Times";

        //Includes the updates as well so users know what changed.
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity)
                .setTitle(title)
                .setMessage(R.string.schedule)
                .setIcon(R.drawable.ic_launcher)
                .setNeutralButton("Close", new Dialog.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Close the dialog
                        dialogInterface.dismiss();
                    }
                });
        builder.create().show();
    }
}
