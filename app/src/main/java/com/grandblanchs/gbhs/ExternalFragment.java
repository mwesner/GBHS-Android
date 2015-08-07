package com.grandblanchs.gbhs;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;


public class ExternalFragment extends Fragment {

    ListView lstExternal;
    ProgressBar prog;

    String[] external;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lstExternal = (ListView) view.findViewById(R.id.lstExternal);
        prog = (ProgressBar) view.findViewById(R.id.progExternal);

        external = getActivity().getResources().getStringArray(R.array.external);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_external, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        ExternalAdapter adapter = new ExternalAdapter(getActivity(), external);

        lstExternal.setAdapter(adapter);
        FadeAnimation f = new FadeAnimation();
        f.start(lstExternal, null, prog);
    }
}