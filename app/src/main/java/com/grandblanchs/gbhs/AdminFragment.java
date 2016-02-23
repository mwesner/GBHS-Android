package com.grandblanchs.gbhs;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

public class AdminFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ProgressBar prog = (ProgressBar) view.findViewById(R.id.prog);
        ListView lst_admin = (ListView) view.findViewById(R.id.lst_admin);

        String[] names = getResources().getStringArray(R.array.admin_names);

        ListAdapter adminAdapter = new OfficeAdapter
                (getActivity(), 0, names);

        lst_admin.setAdapter(adminAdapter);

        FadeAnimation f = new FadeAnimation();
        f.start(lst_admin, prog);
    }
}