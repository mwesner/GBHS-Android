package com.grandblanchs.gbhs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment {

    private MapView mapView;
    private Button btnWest;
    private Button btnEast;
    private Spinner lstType;
    private GoogleMap map;
    private CameraUpdate cam;

    private String[] maptype;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);

        maptype = getResources().getStringArray(R.array.maptype);

        btnWest = (Button) v.findViewById(R.id.btnWest);
        btnEast = (Button) v.findViewById(R.id.btnEast);
        lstType = (Spinner) v.findViewById(R.id.lstType);

        // Gets the MapView from the XML layout and creates it
        mapView = (MapView) v.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        // Gets to GoogleMap from the MapView and does initialization
        map = mapView.getMap();

        // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
        if (MapsInitializer.initialize(getActivity()) == ConnectionResult.SUCCESS) {
            showMap();
        }
        return v;
    }

    private void showMap() {
        btnEast.setVisibility(View.VISIBLE);
        btnWest.setVisibility(View.VISIBLE);
        lstType.setVisibility(View.VISIBLE);

        // Updates the location and zoom of the MapView
        cam = CameraUpdateFactory.newLatLngZoom(new LatLng(42.921275, -83.627256), 16);
        map.moveCamera(cam);

        final LatLng east = new LatLng(42.91995, -83.624859);
        final LatLng west = new LatLng(42.920577, -83.630655);

        map.addMarker(new MarkerOptions()
                .title(getString(R.string.app_name))
                .snippet(getString(R.string.East))
                .position(east));
        map.addMarker(new MarkerOptions()
                .title(getString(R.string.app_name))
                .snippet(getString(R.string.West))
                .position(west));

        lstType.setAdapter(new MapAdapter(getActivity(), maptype));

        lstType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                        break;
                    case 1:
                        map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                        break;
                    case 2:
                        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                        break;
                    case 3:
                        map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        btnWest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cam = CameraUpdateFactory.newLatLngZoom(west, 18);
                map.animateCamera(cam);
            }
        });

        btnEast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cam = CameraUpdateFactory.newLatLngZoom(east, (float) 17.125);
                map.animateCamera(cam);
            }
        });
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }
}