package com.grandblanchs.gbhs;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


class AdminAdapter extends ArrayAdapter<String> {

    Bitmap BitmapImage;

    AdminAdapter(Context context, String[] admins) {
        super(context, R.layout.admin_list, admins);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater adminInflater = LayoutInflater.from(getContext());
        View customView = adminInflater.inflate(R.layout.admin_list, parent, false);

        String SingleAdmin = getItem(position);
        ImageView img_admin = (ImageView) customView.findViewById(R.id.img_admin);
        Button btn_call = (Button) customView.findViewById(R.id.btn_call);
        Button btn_email = (Button) customView.findViewById(R.id.btn_email);
        TextView txt_name = (TextView) customView.findViewById(R.id.txt_name);


        btn_email.setOnClickListener(
                new Button.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        if (position == 0) {
                            emailer();
                        }else if (position == 1){
                            emailer();
                        }else if (position == 2){
                            emailer();
                        }else if (position == 3){
                            emailer();
                        }else if (position == 4){
                            emailer();
                        }else if (position == 5){
                            emailer();
                        }else{

                        }
                    }
                }

        );










        txt_name.setText(SingleAdmin);

        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = 8;


        if (position == 0) {
            Bitmap icon = BitmapFactory.decodeResource(getContext().getResources(),
                    R.drawable.hammond, opts);
            img_admin.setImageBitmap(icon);
        } else if (position == 1) {
            Bitmap icon = BitmapFactory.decodeResource(getContext().getResources(),
                    R.drawable.belcher, opts);
            img_admin.setImageBitmap(icon);
        } else if (position == 2) {
            Bitmap icon = BitmapFactory.decodeResource(getContext().getResources(),
                    R.drawable.gz, opts);
            img_admin.setImageBitmap(icon);
        } else if (position == 3) {
            Bitmap icon = BitmapFactory.decodeResource(getContext().getResources(),
                    R.drawable.poelke, opts);
            img_admin.setImageBitmap(icon);
        } else if (position == 4) {
            Bitmap icon = BitmapFactory.decodeResource(getContext().getResources(),
                    R.drawable.knight, opts);
            img_admin.setImageBitmap(icon);
        } else if (position == 5) {
            Bitmap icon = BitmapFactory.decodeResource(getContext().getResources(),
                    R.drawable.dohm, opts);
            img_admin.setImageBitmap(icon);
        } else {

        }


        return customView;
    }

    public void emailer(){
        Intent emailing = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto:", "aamigo89@gmail.com", null));
        emailing.setType("message/rfc822");
        getContext().startActivity(Intent.createChooser(emailing, "Send an email..."));
    }
}