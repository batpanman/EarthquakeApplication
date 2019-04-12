//
// Name                 Panagiotis Chondrodimos
// Student ID           S1517035
// Programme of Study   BSc Computing
//

package org.pchond200.gcu.earthquakeapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class MainFragment extends Fragment {

    ArrayList<String> description = new ArrayList<>();
    ArrayList<String> date1 = new ArrayList<>();
    ArrayList<String> location1 = new ArrayList<>();
    ArrayList<String> lat1 = new ArrayList<>();
    ArrayList<String> lon1 = new ArrayList<>();
    ArrayList<String> depth1 = new ArrayList<>();
    ArrayList<String> mag1 = new ArrayList<>();
    ArrayList<String> latLong = new ArrayList<>();

    private MyAdapter parse2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View v = inflater.inflate(R.layout.fragment_main, container,false);

        ArrayList<String> Al = getArguments().getStringArrayList("key3");
        String trimDate = "";
       // String latlon = "";
        for(int i = 0; i < Al.size(); i++){

            String[] seperatedDesc = Al.get(i).split(";");
            trimDate = seperatedDesc[0];
            String[] seperatedDate = trimDate.split(",");
            date1.add(seperatedDate[1]);
            String[] seperateLocation = seperatedDesc[1].split(":");
            location1.add(seperateLocation[1]);

            latLong.add(seperatedDesc[2]);

            String[] seperatedLatlon = seperatedDesc[2].split(":");

            depth1.add(seperatedDesc[3]);
            mag1.add(seperatedDesc[4]);


            RecyclerView recyclerView = v.findViewById(R.id.parse2);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            parse2 = new MyAdapter(location1, date1, depth1, mag1);  // getApplication()
            recyclerView.setAdapter(parse2);
        }
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search, menu);

        MenuItem searchItem = menu.findItem(R.id.search_location);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                parse2.getFilter().filter(newText);
                return false;
            }
        });

    }


}
