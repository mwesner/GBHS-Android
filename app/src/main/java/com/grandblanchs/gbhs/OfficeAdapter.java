package com.grandblanchs.gbhs;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
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


class OfficeAdapter extends ArrayAdapter<String> {


    int type; //0 is administration, 1 is guidance

    Bitmap image;

    String[] name;
    String[] subtitle;
    String[] email;
    String[] phone;
    String[] bio;

    Resources res;

    OfficeAdapter(Context context, int t, String[] names) {
        super(context, R.layout.item_office, names);
        type = t;
        name = names;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        res = getContext().getResources();

        getInformation(type, position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_office, parent, false);
        }

        ImageView img = (ImageView) convertView.findViewById(R.id.img);

        TextView txt_name = (TextView) convertView.findViewById(R.id.txt_name);
        TextView txt_subtitle = (TextView) convertView.findViewById(R.id.txt_subtitle);
        ImageButton btn_call = (ImageButton) convertView.findViewById(R.id.btn_call);
        ImageButton btn_email = (ImageButton) convertView.findViewById(R.id.btn_email);

        img.setImageBitmap(image);
        txt_name.setText(name[position]);
        txt_subtitle.setText(subtitle[position]);

        btn_call.setOnClickListener(
                new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        phoneCall(phone[position]);
                    }
                }
        );

        btn_email.setOnClickListener(
                new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        emailer(email[position]);
                    }
                }
        );

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Properly adjust image sizes.
                new OfficeDialog(getContext(), getImage(8, type, position), name[position],
                        subtitle[position], email[position], phone[position],
                        bio[position]).show();
            }
        });

        return convertView;
    }

    public void getInformation(int i, int pos) {
        image = getImage(6, i, pos);
        if (i == 0) { //Administration
            subtitle = res.getStringArray(R.array.admin_subtitles);
            email = res.getStringArray(R.array.admin_emails);
            phone = res.getStringArray(R.array.admin_phones);
            bio = res.getStringArray(R.array.admin_bios);
        }else if (i == 1) { //Guidance
            subtitle = res.getStringArray(R.array.guidance_subtitles);
            email = res.getStringArray(R.array.guidance_emails);
            phone = res.getStringArray(R.array.guidance_phones);
            bio = res.getStringArray(R.array.guidance_bios);
        }
    }
    
    public Bitmap getImage(int sampleSize, int type, int position) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = sampleSize;

        if (type == 0) {
            switch (position) {
                default:
                    return null; //Required
                case 0:
                    return BitmapFactory.decodeResource(res,
                            R.drawable.hammond, opts);
                case 1:
                    return BitmapFactory.decodeResource(res,
                            R.drawable.belcher, opts);
                case 2:
                    return BitmapFactory.decodeResource(res,
                            R.drawable.gz, opts);
                case 3:
                    return BitmapFactory.decodeResource(res,
                            R.drawable.poelke, opts);
                case 4:
                    return BitmapFactory.decodeResource(res,
                            R.drawable.knight, opts);
                case 5:
                    return BitmapFactory.decodeResource(res,
                            R.drawable.dohm, opts);
            }
        }else if (type == 1) {
            switch (position) {
                default:
                    return null; //Required
                case 0:
                    return BitmapFactory.decodeResource(res,
                            R.drawable.gardner, opts);
                case 1:
                    return BitmapFactory.decodeResource(res,
                            R.drawable.hentes, opts);
                case 2:
                    return BitmapFactory.decodeResource(res,
                            R.drawable.hall, opts);
                case 3:
                    return BitmapFactory.decodeResource(res,
                            R.drawable.kernen, opts);
                case 4:
                    return BitmapFactory.decodeResource(res,
                            R.drawable.mol, opts);
                case 5:
                    return BitmapFactory.decodeResource(res,
                            R.drawable.mccleary, opts);
            }
        }else{
            return null;
        }
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