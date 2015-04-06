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
import android.widget.GridView;
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

    //TODO: (Corey) Add guidance and counseling information

    GridView gridView;
    GridViewAdapter customGridAdapter;
    ProgressBar prog;

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
        prog = (ProgressBar) view.findViewById(R.id.progGuidance);
        gridView = (GridView) view.findViewById(R.id.gridView);
    }

    @Override
    public void onStart() {
        super.onStart();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                System.out.println(position + "#Selected");
            }

        });
        new GuidanceScrape().execute();
    }

    class GuidanceScrape extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            customGridAdapter = new GridViewAdapter(getActivity(), R.layout.row_grid, getData());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            gridView.setAdapter(customGridAdapter);
            prog.setVisibility(View.GONE);
        }
    }

    private ArrayList<ImageItem> getData() {
        final ArrayList<ImageItem> imageItems = new ArrayList<>();
        // retrieve String drawable array
        TypedArray imgs = getResources().obtainTypedArray(R.array.guidance);
        //Allows for the bitmaps to be displayed without causing an out-of-memory exception
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = 4;

        //Runs through a loop, and gets pictures from the drawable folder
        for (int i = 0; i < imgs.length(); i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),
                    imgs.getResourceId(i, -1), opts);
            if (i == 0) {
                imageItems.add(new ImageItem(bitmap, "Mrs. Gardner (O-Z)"));
            }else if (i == 1) {
                imageItems.add(new ImageItem(bitmap, "Mr. Hentes (CSS)"));
            }else if (i == 2) {
                imageItems.add(new ImageItem(bitmap, "Mrs. Hall (9th Grade)"));
            }else if (i == 3) {
                imageItems.add(new ImageItem(bitmap, "Mrs. Kernen (Ge-N)"));
            }else if (i == 4) {
                imageItems.add(new ImageItem(bitmap, "Mrs. Mol (A-Ga)"));
            }else if (i == 5) {
                imageItems.add(new ImageItem(bitmap, "Mrs. McCleary (Secretary East)"));
            }
        }

        //Emails:

        //mgardner@grandblancschools.org
        //jhentes@grandblancschools.org
        //phall@grandblancschools.org
        //nkernen@grandblancschools.org
        //pmol@grandblancschools.org
        //lmcclear@grandblancschools.org

        imgs.recycle();

        return imageItems;

    }
}