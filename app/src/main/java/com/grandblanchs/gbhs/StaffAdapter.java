package com.grandblanchs.gbhs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class StaffAdapter extends BaseAdapter implements Filterable {

    Context context;
    ImageButton btn_email;
    ImageButton btn_call;
    private LayoutInflater mInflater;
    private ItemFilter mFilter = new ItemFilter();
    private List<String> originalNames;
    private List<String> filteredNames;
    private List<String> originalEmails;
    private List<String> filteredEmails;
    private List<String> originalPhones;
    private List<String> filteredPhones;


    public StaffAdapter(Context context, List<String> names, List<String> emails, List<String> phones) {

        this.context = context;
        mInflater = LayoutInflater.from(context);

        this.originalNames = names;
        this.filteredNames = names;

        this.originalEmails = emails;
        this.filteredEmails = emails;

        this.originalPhones = phones;
        this.filteredPhones = phones;
    }

    public int getCount() {
        return filteredNames.size();
    }

    public Object getItem(int position) {
        return filteredNames.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ViewHolder")
    public View getView(final int position, View convertView, ViewGroup parent) {

        convertView = mInflater.inflate(R.layout.item_staff, parent, false);

        ViewHolder holder = new ViewHolder();

        holder.text = (TextView) convertView.findViewById(R.id.txt_name);
        btn_email = (ImageButton) convertView.findViewById(R.id.btn_email);
        btn_call = (ImageButton) convertView.findViewById(R.id.btn_call);

        convertView.setTag(holder);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StaffFragment.staffSearch.clearFocus();
                InputMethodManager imm = (InputMethodManager)
                        context.getSystemService(
                                Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(StaffFragment.staffSearch.getWindowToken(),
                        0);
            }
        });

        holder.text.setText(filteredNames.get(position));

        btn_email.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailer(filteredEmails.get(position));
            }
        });

        if (filteredPhones.get(position).equals("NONE")) {
            btn_call.setVisibility(View.INVISIBLE);
        }

        btn_call.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                caller(filteredPhones.get(position));
            }
        });
        return convertView;
    }

    public Filter getFilter() {
        return mFilter;
    }

    public void emailer(String a) {
        Intent emailing = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", a, null));
        context.startActivity(emailing);
    }

    public void caller(String a) {
        Intent calling = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", a, null));
        context.startActivity(calling);
    }

    static class ViewHolder {
        TextView text;
    }

    private class ItemFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            int count = originalNames.size();
            final ArrayList<String> nlist = new ArrayList<>(count);
            final ArrayList<String> nlistemail = new ArrayList<>(count);
            final ArrayList<String> nlistphone = new ArrayList<>(count);

            String filterableString;

            for (int i = 0; i < count; i++) {
                filterableString = originalNames.get(i);
                if (filterableString.toLowerCase().contains(filterString)) {
                    nlist.add(filterableString);
                    nlistemail.add(originalEmails.get(i));
                    nlistphone.add(originalPhones.get(i));
                }
            }

            filteredEmails = nlistemail;
            filteredPhones = nlistphone;

            results.values = nlist;
            results.count = nlist.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredNames = (ArrayList<String>) results.values;
            notifyDataSetChanged();
        }

    }
}