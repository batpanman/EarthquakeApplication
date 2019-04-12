//
// Name                 Panagiotis Chondrodimos
// Student ID           S1517035
// Programme of Study   BSc Computing
//

package org.pchond200.gcu.earthquakeapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.SupportMapFragment;

import java.util.ArrayList;

public class SettingsFragment extends Fragment {

    ArrayList<String> lat = new ArrayList<String>();
    ArrayList<String> lon = new ArrayList<String>();
    ArrayList<String> depth1 = new ArrayList<>();
    ArrayList<String> mag1 = new ArrayList<>();
    ArrayList<String> date1 = new ArrayList<>();
    ArrayList<String> location1 = new ArrayList<>();
    int earthquakeNumber = 0;
    double maxLat = 0;
    double minLat = 100;
    double convertLat = 0;
    int positionMaxLat = 0;
    int positionMinLat = 0;
    double convertLong = 0;
    double maxLong = 0;
    double minLong = 100;
    int positionMaxLong = 0;
    int positionMinLong = 0;
    double convertMagn = 0;
    double getConvertDepth = 0;
    double maxMagn = 0;
    double maxDepth = 0;
    int positionMaxMagn = 0;
    int positionMaxDepth = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_settings, container,false);

        ArrayList<String> getDesc = getArguments().getStringArrayList("key2");

        earthquakeNumber = getDesc.size();
        System.out.println(earthquakeNumber);
        String trimDate = "";
        for(int i = 0; i < earthquakeNumber; i++){

            String[] seperatedDesc = getDesc.get(i).split(";");
            trimDate = seperatedDesc[0];

            String[] seperatedDate = trimDate.split(",");
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

            String[] seperateDepth = seperatedDesc[3].split(":");
            seperateDepth[0] = seperateDepth[0];
            seperateDepth[1] = seperateDepth[1];

            String[] seperateDepth2 = seperateDepth[1].split(" ");
            seperateDepth2[0] = seperateDepth2[0];
            seperateDepth2[1] = seperateDepth2[1];
            depth1.add(seperatedDesc[3]);

            String[] seperateMagn = seperatedDesc[4].split(":");
            seperateMagn[0] = seperateMagn[0];
            seperateMagn[1] = seperateMagn[1];
            mag1.add(seperatedDesc[4]);

            convertLat = (double) Double.parseDouble(String.valueOf(seperateFinal[0]));
            if (convertLat > maxLat) {
                maxLat = convertLat;
                positionMaxLat = i;
            }

            if (convertLat < minLat) {
                minLat = convertLat;
                positionMinLat = i;
            }

            convertLong = (double) Double.parseDouble(String.valueOf(seperateFinal[1]));
            if (convertLong > maxLong) {
                maxLong = convertLong;
                positionMaxLong = i;
            }

            if (convertLong < minLong) {
                minLong = convertLong;
                positionMinLong = i;
            }

            convertMagn = (double) Double.parseDouble(String.valueOf(seperateMagn[1]));
            if (convertMagn > maxMagn) {
                maxMagn = convertMagn;
                positionMaxMagn = i;
            }

            getConvertDepth = (double) Double.parseDouble(String.valueOf(seperateDepth2[1]));
            if (getConvertDepth > maxDepth) {
                maxDepth = getConvertDepth;
                positionMaxDepth = i;
            }

        }
        String minLongToString = String.valueOf(minLong);
        String maxLongToString = String.valueOf(maxLong);
        String minLatToString = String.valueOf(minLat);
        String maxLatToString = String.valueOf(maxLat);
        String totalEqToString = String.valueOf(earthquakeNumber);
        TextView furthestWest = (TextView) v.findViewById(R.id.furthestWest);
        TextView dateWest = (TextView) v.findViewById(R.id.dateWest);
        furthestWest.setText(location1.get(positionMinLong));
        dateWest.setText(date1.get(positionMinLong));


        TextView furthestEast = (TextView) v.findViewById(R.id.furthestEast);
        TextView dateEast = (TextView) v.findViewById(R.id.dateEast);
        furthestEast.setText(location1.get(positionMaxLong));
        dateEast.setText(date1.get(positionMaxLong));

        TextView furthestNorth = (TextView) v.findViewById(R.id.furthestNorth);
        TextView dateNorth = (TextView) v.findViewById(R.id.dateNorth);
        furthestNorth.setText(location1.get(positionMaxLat));
        dateNorth.setText(date1.get(positionMaxLong));

        TextView furthestSouth = (TextView) v.findViewById(R.id.furthestSouth);
        TextView dateSouth = (TextView) v.findViewById(R.id.dateSouth);
        furthestSouth.setText(location1.get(positionMinLat));
        dateSouth.setText(date1.get(positionMaxLong));

        TextView highestMagn = (TextView) v.findViewById(R.id.highMagn);
        TextView dateMagn = (TextView) v.findViewById(R.id.dateMagn);
        highestMagn.setText(mag1.get(positionMaxMagn));
        dateMagn.setText(date1.get(positionMaxMagn));

        TextView highDepth = (TextView) v.findViewById(R.id.highDepth);
        TextView dateDepth = (TextView) v.findViewById(R.id.dateDepth);
        highDepth.setText(depth1.get(positionMaxDepth));
        dateDepth.setText(date1.get(positionMaxDepth));

        TextView total = (TextView) v.findViewById(R.id.total);
        total.setText(totalEqToString);

        return v;
    }
}
