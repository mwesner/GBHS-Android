package com.grandblanchs.gbhs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;


class StaffAdapter extends ArrayAdapter<String> {
    String email[];
    StaffAdapter(Context context, String[] staff) {
        super(context, R.layout.admin_list, staff);
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater staffInflater = LayoutInflater.from(getContext());
        convertView = staffInflater.inflate(R.layout.stafflist, parent, false);

        String StaffName = getItem(position);
        Button btn_email = (Button) convertView.findViewById(R.id.btn_email);
        TextView txt_name = (TextView) convertView.findViewById(R.id.txt_name);
        txt_name.setText(StaffName);


        btn_email.setOnClickListener(
                new Button.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        email = getContext().getResources().getStringArray(R.array.guidance_numbers);

                    }
                }
        );









        return convertView;
    }


}
