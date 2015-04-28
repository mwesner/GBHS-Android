package com.grandblanchs.gbhs;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;


public class Staff extends Fragment {

    public interface OnFragmentInteractionListener{}

    ProgressBar prog;
    ListView lst_staff;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.staff, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prog = (ProgressBar) view.findViewById(R.id.prog);
        lst_staff = (ListView) view.findViewById(R.id.lst_staff);

        ListAdapter StaffAdapter = new StaffAdapter(getActivity(), getResources().getStringArray(R.array.staff_names));
        lst_staff.setAdapter(StaffAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        prog.setVisibility(View.GONE);
    }
}