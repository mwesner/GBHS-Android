package com.grandblanchs.gbhs;


import android.app.Fragment;
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

        String[] guides = {"Mrs. Gardner (O-Z)", "Mr. Hentes (CSS)", "Mrs. Hall (9th Grade)",
                "Mrs. Kernen (Ge-N)", "Mrs. Mol (A-Ga)", "Mrs. McCleary (Secretary East)"};

        //Had to get rid of the getActivity.getApplicationContext(), because then startActivity calls don't work...
        ListAdapter guidanceAdapter = new GuidanceAdapter(getActivity(), guides);



        lst_guide.setAdapter(guidanceAdapter);


    }

    @Override
    public void onStart() {
        super.onStart();
        prog.setVisibility(View.GONE);
        //Emails:

        //mgardner@grandblancschools.org
        //591-6652

        //jhentes@grandblancschools.org
        //phall@grandblancschools.org
        //nkernen@grandblancschools.org
        //pmol@grandblancschools.org
        //lmcclear@grandblancschools.org
    }
}