package com.grandblanchs.gbhs;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Map extends Fragment {

    public interface OnFragmentInteractionListener{}

    MapView mapView;
    GoogleMap map;
    CameraUpdate cam;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.map, container, false);

        // Gets the MapView from the XML layout and creates it
        mapView = (MapView) v.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        // Gets to GoogleMap from the MapView and does initialization
        map = mapView.getMap();
        map.getUiSettings().setMyLocationButtonEnabled(true);
        map.setMyLocationEnabled(true);

        // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
        MapsInitializer.initialize(this.getActivity());

        // Updates the location and zoom of the MapView
        cam = CameraUpdateFactory.newLatLngZoom(new LatLng(42.921275,-83.627256), 16);
        map.moveCamera(cam);

        final LatLng east = new LatLng(42.91995,-83.624859);
        final LatLng west = new LatLng(42.920577,-83.630655);

        map.addMarker(new MarkerOptions()
                .title("Grand Blanc High School")
                .snippet("East Campus")
                .position(east));
        map.addMarker(new MarkerOptions()
                .title("Grand Blanc High School")
                .snippet("West Campus")
                .position(west));

        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                switch (marker.getSnippet()) {
                    case "East Campus":
                        Log.d("EAST", "East Campus Clicked!");
                        cam = CameraUpdateFactory.newLatLngZoom(east, 18);
                        map.animateCamera(cam);
                    case "West Campus":
                        cam = CameraUpdateFactory.newLatLngZoom(west, 18);
                        map.animateCamera(cam);
                }
                return false;
            }
        });
        return v;
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }
}