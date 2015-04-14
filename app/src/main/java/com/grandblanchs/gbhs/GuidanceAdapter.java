package com.grandblanchs.gbhs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;


class GuidanceAdapter extends ArrayAdapter<String>{
    Bitmap BitmapImage;
    String number[];
    String email[];
    GuidanceAdapter(Context context, String[] guides) {
        super(context, R.layout.guidance_list ,guides);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater adminInflater = LayoutInflater.from(getContext());
        View customView = adminInflater.inflate(R.layout.guidance_list, parent, false);

        String SingleAdmin = getItem(position);
        ImageView img_guide = (ImageView) customView.findViewById(R.id.img_guide);
        Button btn_call = (Button) customView.findViewById(R.id.btn_call);
        Button btn_email = (Button) customView.findViewById(R.id.btn_email);
        TextView txt_name = (TextView) customView.findViewById(R.id.txt_name);

        btn_call.setOnClickListener(
                new Button.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        number = getContext().getResources().getStringArray(R.array.guidance_numbers);
                        if (position == 0) {
                            phoneCall(number[position]);
                        }else if (position == 1){
                            phoneCall(number[position]);
                        }else if (position == 2){
                            phoneCall(number[position]);
                        }else if (position == 3){
                            phoneCall(number[position]);
                        }else if (position == 4){
                            phoneCall(number[position]);
                        }else if (position == 5){
                            phoneCall(number[position]);
                        }else{

                        }
                    }
                }
        );


        btn_email.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        email = getContext().getResources().getStringArray(R.array.guidance_emails);
                        if (position == 0) {
                            emailer(email[position]);
                        }else if (position == 1){
                            emailer(email[position]);
                        }else if (position == 2){
                            emailer(email[position]);
                        }else if (position == 3){
                            emailer(email[position]);
                        }else if (position == 4){
                            emailer(email[position]);
                        }else if (position == 5){
                            emailer(email[position]);
                        }else{

                        }

                    }
                }
        );


        txt_name.setText(SingleAdmin);

        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = 8;


        if (position == 0 ){
            Bitmap icon = BitmapFactory.decodeResource(getContext().getResources(),
                    R.drawable.gardner, opts);
            img_guide.setImageBitmap(icon);
        }else if(position == 1){
            Bitmap icon = BitmapFactory.decodeResource(getContext().getResources(),
                    R.drawable.hentes, opts);
            img_guide.setImageBitmap(icon);
        }else if(position == 2){
            Bitmap icon = BitmapFactory.decodeResource(getContext().getResources(),
                    R.drawable.hall, opts);
            img_guide.setImageBitmap(icon);
        }else if(position == 3){
            Bitmap icon = BitmapFactory.decodeResource(getContext().getResources(),
                    R.drawable.kernen, opts);
            img_guide.setImageBitmap(icon);
        }else if(position == 4){
            Bitmap icon = BitmapFactory.decodeResource(getContext().getResources(),
                    R.drawable.mol, opts);
            img_guide.setImageBitmap(icon);
        }else if (position == 5){
            Bitmap icon = BitmapFactory.decodeResource(getContext().getResources(),
                    R.drawable.mccleary, opts);
            img_guide.setImageBitmap(icon);
        }


        return customView;
    }

    public void phoneCall(String number){
        Uri dial = Uri.parse("tel:" + number);
        Intent call = new Intent(Intent.ACTION_DIAL, dial);
        getContext().startActivity(call);
    }

    public void emailer(String a){
        Intent emailing = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto:", a, null));
        emailing.setType("message/rfc822");
        getContext().startActivity(Intent.createChooser(emailing, "Send an email..."));
    }
}