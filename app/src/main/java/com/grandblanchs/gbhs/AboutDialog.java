package com.grandblanchs.gbhs;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;

public class AboutDialog {

    private Activity mActivity;

    public AboutDialog(Activity context) {
        mActivity = context;
    }

    public void show() {

        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity)
                .setTitle(R.string.app_name)
                .setIcon(R.mipmap.ic_launcher)
                .setMessage(mActivity.getString(R.string.about))
                .setPositiveButton(R.string.close, new Dialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        builder.create().show();
    }
}
