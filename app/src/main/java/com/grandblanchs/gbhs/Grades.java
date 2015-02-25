package com.grandblanchs.gbhs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

public class Grades {

    private Activity mActivity;
    Context context;

    public Grades(Activity context) {
        mActivity = context;
    }

    public void show() {

        // Show the schedule
        String title = "Check Grades";

        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity)
                .setTitle(title)
                .setMessage("Dummy content")
                .setIcon(R.drawable.ic_launcher)
                .setPositiveButton(R.string.getapp, new Dialog.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("market://details?id=com.android.example"));
                        mActivity.startActivity(intent);
                    }
                })
                .setNeutralButton(R.string.synergy, new Dialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://studentvue.geneseeisd.org/GBCS/Login_Student_PXP.aspx"));
                        mActivity.startActivity(browserIntent);
                    }
                });
        builder.create().show();
    }
}
