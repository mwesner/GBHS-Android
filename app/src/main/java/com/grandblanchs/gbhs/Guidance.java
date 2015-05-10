package com.grandblanchs.gbhs;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;



public class Guidance extends Fragment {

    public interface OnFragmentInteractionListener{}


    ProgressBar prog;
    ListView lst_guide;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.guidance, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prog = (ProgressBar) view.findViewById(R.id.prog);
        lst_guide = (ListView) view.findViewById(R.id.lst_guide);



        //Had to get rid of the getActivity.getApplicationContext(), because then startActivity calls don't work...
        ListAdapter guidanceAdapter = new GuidanceAdapter(getActivity(), getResources().getStringArray(R.array.guidance_names));



        lst_guide.setAdapter(guidanceAdapter);


    }

    @Override
    public void onStart() {
        super.onStart();
        prog.setVisibility(View.GONE);
    }
}