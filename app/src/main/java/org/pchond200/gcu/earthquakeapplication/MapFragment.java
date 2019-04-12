//
// Name                 Panagiotis Chondrodimos
// Student ID           S1517035
// Programme of Study   BSc Computing
//

package org.pchond200.gcu.earthquakeapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    ArrayList<String> latlon;
    ArrayList<String> lat = new ArrayList<String>();
    ArrayList<String> lon = new ArrayList<String>();
    ArrayList<String> depth1 = new ArrayList<>();
    ArrayList<String> mag1 = new ArrayList<>();
    ArrayList<String> date1 = new ArrayList<>();
    ArrayList<String> location1 = new ArrayList<>();

    GoogleMap map;

    private SupportMapFragment mMap;


    public MapFragment() {

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container,false);

        String trimDate = "";
        ArrayList<String> getDesc = getArguments().getStringArrayList("key");
        for(int i = 0; i < getDesc.size(); i++){

            String[] seperatedDesc = getDesc.get(i).split(";");
            trimDate = seperatedDesc[0];

            String[] seperatedDate = trimDate.split(":");
            date1.add(seperatedDate[1]);

            String[] seperateLocation = seperatedDesc[1].split(":");
            location1.add(seperateLocation[1]);

            String[] seperateLatLon = seperatedDesc[2].split(":");
            seperateLatLon[0] = seperateLatLon[0];
            seperateLatLon[1] = seperateLatLon[1];

            String[] seperateFinal = seperateLatLon[1].split(",");
            seperateFinal[0] = seperateFinal[0];
            seperateFinal[1] = seperateFinal[1];
            lat.add(seperateFinal[0]);
            lon.add(seperateFinal[1]);
            String[] seperatedLatlon = seperatedDesc[2].split(":");

            depth1.add(seperatedDesc[3]);
            mag1.add(seperatedDesc[4]);
        }

        SupportMapFragment mapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map);
        System.out.println("Map Fragment " + mapFragment);
        mapFragment.getMapAsync(this);

        return v;
    }

    @SuppressLint("ValidFragment")
    public MapFragment(ArrayList<String> arrayLat, ArrayList<String> arrayLon) {
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        ArrayList<String> here;
        map = googleMap;

        for (int i = 0; i < lat.size(); i++) {
            double d = (double) Double.parseDouble(String.valueOf(lat.get(i)));
            double g = (double) Double.parseDouble(String.valueOf(lon.get(i)));
             LatLng markers = new LatLng(d, g);
             map.addMarker(new MarkerOptions().position(markers).title(location1.get(i)).snippet(depth1.get(i) + "\n" + mag1.get(i) + "\n" + date1.get(i)));
             CameraPosition cameraPosition = new CameraPosition.Builder().target(markers).zoom(5).build();
             CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
             map.moveCamera(cameraUpdate);
        }
    }
}
