package com.grandblanchs.gbhs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


class GuidanceAdapter extends ArrayAdapter<String>{
    Bitmap BitmapImage;

    GuidanceAdapter(Context context, String[] guides) {
        super(context, R.layout.guidance_list ,guides);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater adminInflater = LayoutInflater.from(getContext());
        View customView = adminInflater.inflate(R.layout.guidance_list, parent, false);

        String SingleAdmin = getItem(position);
        ImageView img_guide = (ImageView) customView.findViewById(R.id.img_guide);
        Button btn_call = (Button) customView.findViewById(R.id.btn_call);
        Button btn_email = (Button) customView.findViewById(R.id.btn_email);
        TextView txt_name = (TextView) customView.findViewById(R.id.txt_name);



        txt_name.setText(SingleAdmin);

        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = 4;


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
}