package com.grandblanchs.gbhs;


import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import java.util.ArrayList;



/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Admin.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class Admin extends Fragment {

    private OnFragmentInteractionListener mListener;
    private GridView gridView;
    private GridViewAdapter customGridAdapter;

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
    public void onStart() {
        super.onStart();
        //new AdminScrape().execute();
        gridView = (GridView) getActivity().findViewById(R.id.gridView);
        customGridAdapter = new GridViewAdapter(getActivity(), R.layout.row_grid, getData());
        gridView.setAdapter(customGridAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                System.out.println(position + "#Selected");
            }

        });
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }

    private ArrayList<ImageItem> getData() {
        final ArrayList<ImageItem> imageItems = new ArrayList<ImageItem>();
        // retrieve String drawable array
        TypedArray imgs = getResources().obtainTypedArray(R.array.admin);
        //Allows for the bitmaps to be displayed without causing an out-of-memory exception
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = 4;

        //Runs through a loop, and gets pictures from the drawable folder
        for (int i = 0; i < imgs.length(); i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),
                    imgs.getResourceId(i, -1), opts);
            if (i == 0) {
                imageItems.add(new ImageItem(bitmap, "Dr. Hammond"));
            }else if (i == 1) {
                imageItems.add(new ImageItem(bitmap, "Chris Belcher"));
            }else if (i == 2) {
                imageItems.add(new ImageItem(bitmap, "Mr. Goetzinger"));
            }else if (i == 3) {
                imageItems.add(new ImageItem(bitmap, "Patricia Poelke"));
            }else if (i == 4) {
                imageItems.add(new ImageItem(bitmap, "Christy Knight"));
            }else if (i == 5) {
                imageItems.add(new ImageItem(bitmap, "Jerrod Dohm"));
            }
        }

        return imageItems;

    }
}