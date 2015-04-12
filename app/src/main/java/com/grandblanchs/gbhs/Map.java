package com.grandblanchs.gbhs;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

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
    Button btnWest;
    Button btnEast;
    Spinner lstType;
    GoogleMap map;
    CameraUpdate cam;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.map, container, false);

        btnWest = (Button) v.findViewById(R.id.btnWest);
        btnEast = (Button) v.findViewById(R.id.btnEast);
        lstType = (Spinner) v.findViewById(R.id.lstType);

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

        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                switch (marker.getSnippet()) {
                    case "West Campus":
                        cam = CameraUpdateFactory.newLatLngZoom(west, 18);
                        map.animateCamera(cam);
                        break;
                    case "East Campus":
                        cam = CameraUpdateFactory.newLatLngZoom(east, (float) 17.125);
                        map.animateCamera(cam);
                        break;
                }
                marker.showInfoWindow();
                return true;
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