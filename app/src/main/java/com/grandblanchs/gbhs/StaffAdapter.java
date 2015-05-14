package com.grandblanchs.gbhs;

import android.annotation.SuppressLint;
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
    String call[];

    StaffAdapter(Context context, String[] staff) {
        super(context, R.layout.admin_list, staff);
    }

    ImageButton btn_email;
    ImageButton btn_call;

    @SuppressLint("ViewHolder")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater staffInflater = LayoutInflater.from(getContext());

        //convertView cannot be recycled - position is used to hide call button for staff without phones.
        convertView = staffInflater.inflate(R.layout.stafflist, parent, false);

        String StaffName = getItem(position);
        btn_email = (ImageButton) convertView.findViewById(R.id.btn_email);
        btn_call = (ImageButton) convertView.findViewById(R.id.btn_call);
        TextView txt_name = (TextView) convertView.findViewById(R.id.txt_name);
        txt_name.setText(StaffName);

        email = getContext().getResources().getStringArray(R.array.staff_emails);
        call = getContext().getResources().getStringArray(R.array.staff_phones);

        btn_email.setOnClickListener(
            new Button.OnClickListener(){
                @Override
                public void onClick(View v) {
                    emailer(email[position]);
                }
            }
        );

        btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    caller(call[position]);
            }
        });

        if (call[position].equals("NONE")) {
            btn_call.setVisibility(View.INVISIBLE);
        }


        return convertView;
    }

    public void emailer(String a){
        Intent emailing = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", a, null));
        getContext().startActivity(emailing);
    }

    public void caller(String a){
        Intent calling = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", a, null));
        getContext().startActivity(calling);
    }
}
