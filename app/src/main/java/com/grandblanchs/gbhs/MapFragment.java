package com.grandblanchs.gbhs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private SupportMapFragment mapFragment;
    private GoogleMap map;

    private Button btnWest;
    private Button btnEast;
    private Spinner lstType;
    private CameraUpdate cam;

    private int mapType;
    private double latitude = 42.921275;
    private double longitude = -83.627256;
    private float zoom = 16;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mapType = savedInstanceState.getInt("mapType");
            latitude = savedInstanceState.getDouble("latitude");
            longitude = savedInstanceState.getDouble("longitude");
            zoom = savedInstanceState.getFloat("zoom");
        }

        View v = null;

        try {
            v = inflater.inflate(R.layout.fragment_map, container, false);

            String[] maptypes = getResources().getStringArray(R.array.maptype);

            btnWest = (Button) v.findViewById(R.id.btnWest);
            btnEast = (Button) v.findViewById(R.id.btnEast);
            lstType = (Spinner) v.findViewById(R.id.lstType);

            lstType.setAdapter(new MapAdapter(getActivity(), maptypes));

        }catch (InflateException e) {
            //Map is already present.
        }

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return v;
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        map = googleMap;

        btnEast.setVisibility(View.VISIBLE);
        btnWest.setVisibility(View.VISIBLE);
        lstType.setVisibility(View.VISIBLE);

        // Updates the location and zoom of the MapView
        cam = CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), zoom);
        map.moveCamera(cam);

        map.setMapType(mapType);

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

        lstType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setMapType(position);
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

    private void setMapType(int position) {
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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (map != null) {
            outState.putInt("mapType", map.getMapType());
            outState.putDouble("latitude", map.getCameraPosition().target.latitude);
            outState.putDouble("longitude", map.getCameraPosition().target.longitude);
            outState.putFloat("zoom", map.getCameraPosition().zoom);
        }
    }

    @Override
    public void onResume() {
        mapFragment.onResume();
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        mapFragment.onDestroyView();
        super.onDestroyView();

        Fragment fragment = getChildFragmentManager().findFragmentById(R.id.map);
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.remove(fragment);
        ft.commitAllowingStateLoss();
    }
}