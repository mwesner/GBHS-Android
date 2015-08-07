package com.grandblanchs.gbhs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class StaffFragment extends Fragment {

    ProgressBar prog;
    ListView lst_staff;
    static TextView staffSearch;

    StaffAdapter adapter;

    List<String> staffNames;
    List<String> staffEmails;
    List<String> staffPhones;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_staff, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prog = (ProgressBar) view.findViewById(R.id.prog);
        lst_staff = (ListView) view.findViewById(R.id.lst_staff);
        staffSearch = (TextView) view.findViewById(R.id.staffSearch);

        staffNames = Arrays.asList(getResources().getStringArray(R.array.staff_names));
        staffEmails = Arrays.asList(getResources().getStringArray(R.array.staff_emails));
        staffPhones = Arrays.asList(getResources().getStringArray(R.array.staff_phones));
        adapter = new StaffAdapter(getActivity(),
                staffNames, staffEmails, staffPhones);
        lst_staff.setAdapter(adapter);

        FadeAnimation f = new FadeAnimation();
        f.start(lst_staff, null, prog);

        staffSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        lst_staff.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                staffSearch.clearFocus();
            }
        });
    }
}