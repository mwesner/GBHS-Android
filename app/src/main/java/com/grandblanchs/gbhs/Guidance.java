package com.grandblanchs.gbhs;


import android.app.Fragment;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;



/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Admin.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class Guidance extends Fragment {

    public interface OnFragmentInteractionListener{}



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

        lst_guide = (ListView) view.findViewById(R.id.lst_guide);

        String[] guides = {"Mrs. Gardner (O-Z)", "Mr. Hentes (CSS)", "Mrs. Hall (9th Grade)",
                "Mrs. Kernen (Ge-N)", "Mrs. Mol (A-Ga)", "Mrs. McCleary (Secretary East)"};


        ListAdapter guidanceAdapter = new GuidanceAdapter(getActivity().getApplicationContext(), guides);



        lst_guide.setAdapter(guidanceAdapter);


    }

    @Override
    public void onStart() {
        super.onStart();

        //Emails:

        //mgardner@grandblancschools.org
        //jhentes@grandblancschools.org
        //phall@grandblancschools.org
        //nkernen@grandblancschools.org
        //pmol@grandblancschools.org
        //lmcclear@grandblancschools.org
    }
}