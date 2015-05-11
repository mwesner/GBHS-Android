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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;


class GuidanceAdapter extends ArrayAdapter<String>{
    String number[];
    String email[];
    GuidanceAdapter(Context context, String[] guides) {
        super(context, R.layout.guidance_list ,guides);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater adminInflater = LayoutInflater.from(getContext());
        if (convertView == null) {
            convertView = adminInflater.inflate(R.layout.guidance_list, parent, false);
        }

        ImageView img_guide = (ImageView) convertView.findViewById(R.id.img_guidance);
        ImageButton btn_call = (ImageButton) convertView.findViewById(R.id.btn_call);
        ImageButton btn_email = (ImageButton) convertView.findViewById(R.id.btnmail);
        TextView txt_name = (TextView) convertView.findViewById(R.id.txt_name);

        btn_call.setOnClickListener(
            new Button.OnClickListener(){
                @Override
                public void onClick(View v) {
                    number = getContext().getResources().getStringArray(R.array.guidance_numbers);
                        phoneCall(number[position]);
                }
            }
        );

        btn_email.setOnClickListener(
            new Button.OnClickListener(){
                public void onClick(View v){
                    email = getContext().getResources().getStringArray(R.array.guidance_emails);
                        emailer(email[position]);
                }
            }
        );

        txt_name.setText(getItem(position));

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

        return convertView;
    }

    public void phoneCall(String number){
        Uri dial = Uri.parse("tel:" + number);
        Intent call = new Intent(Intent.ACTION_DIAL, dial);
        getContext().startActivity(call);
    }

    public void emailer(String a){
        Intent emailing = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", a, null));
        getContext().startActivity(emailing);
    }
}