package com.grandblanchs.gbhs;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;


public class CollegeFragment extends Fragment {

    public interface OnFragmentInteractionListener {}

    Button btnCollege;

    public CollegeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_college, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnCollege = (Button) view.findViewById(R.id.btnCollege);

        btnCollege.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Load the page in an external browser.
                Intent w = new Intent(Intent.ACTION_VIEW, Uri.parse("http://early-college.grandblanc.schoolfusion.us"));
                startActivity(w);
            }
        });
    }
}
