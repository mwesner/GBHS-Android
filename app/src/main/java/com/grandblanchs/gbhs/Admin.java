package com.grandblanchs.gbhs;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

public class Admin extends Fragment {

    public interface OnFragmentInteractionListener {}

    //TODO: (Aaron) Code descriptions (web or hardcoded)
    //TODO: (Aaron) Show descriptions with image in separate popout fragment

    ProgressBar prog;
    ListView lst_admin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        prog = (ProgressBar) view.findViewById(R.id.prog);
        lst_admin = (ListView) view.findViewById(R.id.lst_admin);

        ListAdapter adminAdapter = new AdminAdapter(getActivity(),
                getResources().getStringArray(R.array.admin_names));

        lst_admin.setAdapter(adminAdapter);

        FadeAnimation f = new FadeAnimation();
        f.start(lst_admin, null, prog);
    }
}