package com.grandblanchs.gbhs;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


class StaffAdapter extends ArrayAdapter<String> {
    String email[];
    StaffAdapter(Context context, String[] staff) {
        super(context, R.layout.admin_list, staff);
    }

    ImageButton btn_email;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater staffInflater = LayoutInflater.from(getContext());
        if (convertView == null) {
            convertView = staffInflater.inflate(R.layout.stafflist, parent, false);
        }
        String StaffName = getItem(position);
        btn_email = (ImageButton) convertView.findViewById(R.id.btn_email);
        TextView txt_name = (TextView) convertView.findViewById(R.id.txt_name);
        txt_name.setText(StaffName);

        btn_email.setOnClickListener(
            new Button.OnClickListener(){
                @Override
                public void onClick(View v) {
                    email = getContext().getResources().getStringArray(R.array.staff_emails);
                    emailer(email[position]);
                }
            }
        );

        return convertView;
    }

    public void emailer(String a){
        Intent emailing = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", a, null));
        getContext().startActivity(emailing);
    }
}
