package com.grandblanchs.gbhs;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;


public class GuidanceFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_guidance, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ProgressBar prog = (ProgressBar) view.findViewById(R.id.prog);
        ListView lst_guidance = (ListView) view.findViewById(R.id.lst_guidance);

        String[] names = getResources().getStringArray(R.array.guidance_names);

        ListAdapter guidanceAdapter = new OfficeAdapter
                (getActivity(), 1, names);

        lst_guidance.setAdapter(guidanceAdapter);

        FadeAnimation f = new FadeAnimation();
        f.start(lst_guidance, prog);
    }
}