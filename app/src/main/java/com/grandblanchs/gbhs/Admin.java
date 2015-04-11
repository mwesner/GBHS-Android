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


public class Admin extends Fragment {

    public interface OnFragmentInteractionListener {}

    //TODO: (Aaron) Code descriptions (web or hardcoded)
    //TODO: (Aaron) Show descriptions with image in separate popout fragment

    private OnFragmentInteractionListener mListener;
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
        return inflater.inflate(R.layout.admin, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        prog = (ProgressBar) view.findViewById(R.id.prog);
        lst_admin = (ListView) view.findViewById(R.id.lst_admin);

        String[] admins = {"Dr. Hammond", "Chris Belcher", "Mr. Goetzinger", "Patricia Poelke", "Christy Knight", "Jerrod Dohm"};



        //Had to get rid of the getActivity.getApplicationContext(), because then startActivity calls don't work...
        ListAdapter adminAdapter = new AdminAdapter(getActivity(), admins);

        lst_admin.setAdapter(adminAdapter);



    }

    @Override
    public void onStart() {
        super.onStart();
        prog.setVisibility(View.GONE);
    }
}