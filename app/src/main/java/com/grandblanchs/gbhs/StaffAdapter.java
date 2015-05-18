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
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


class StaffAdapter extends ArrayAdapter<String> implements Filterable {
    String email[];
    String call[];

    public StaffAdapter(Context context, List<String> staff) {
        super(context, R.layout.item_staff, staff);
        this.filteredData = staff;
        this.originalData = staff;
    }

    public int getCount() {
        return filteredData.size();
    }

    public String getItem(int position) {
        return filteredData.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    ImageButton btn_email;
    ImageButton btn_call;

    List<String> originalData;
    List<String> filteredData;
    StaffFilter filter = new StaffFilter();

    @SuppressLint("ViewHolder")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater staffInflater = LayoutInflater.from(getContext());

        //convertView cannot be recycled - position is used to hide call button for staff without phones.
        convertView = staffInflater.inflate(R.layout.item_staff, parent, false);

        btn_email = (ImageButton) convertView.findViewById(R.id.btn_email);
        btn_call = (ImageButton) convertView.findViewById(R.id.btn_call);
        TextView txt_name = (TextView) convertView.findViewById(R.id.txt_name);
        txt_name.setText(getItem(position));

        email = getContext().getResources().getStringArray(R.array.staff_emails);
        call = getContext().getResources().getStringArray(R.array.staff_phones);

        btn_email.setOnClickListener(
                new Button.OnClickListener() {
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

    @Override
    public Filter getFilter() {
        if (filter == null)
            filter = new StaffFilter();
        return filter;
    }

    public void emailer(String a) {
        Intent emailing = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", a, null));
        getContext().startActivity(emailing);
    }

    public void caller(String a) {
        Intent calling = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", a, null));
        getContext().startActivity(calling);
    }

    private class StaffFilter extends Filter {

        List<String> staffList = new ArrayList<>();

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            // We implement here the filter logic
            if (constraint == null || constraint.length() == 0) {
                staffList = Arrays.asList(new Staff().getResources().getStringArray
                        (R.array.staff_names));
                // No filter implemented: we return all the list
                results.values = staffList;
                results.count = staffList.size();
            } else {

                for (String s : staffList) {
                    if (s.toUpperCase().startsWith(constraint.toString().toUpperCase())) {
                        staffList.add(s);
                    }
                }

                results.values = staffList;
                results.count = staffList.size();

            }
            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
                staffList = (ArrayList<String>) results.values;
                notifyDataSetChanged();
            }
        }
    }
