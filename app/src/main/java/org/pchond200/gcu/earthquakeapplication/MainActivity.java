//
// Name                 Panagiotis Chondrodimos
// Student ID           S1517035
// Programme of Study   BSc Computing
//

package org.pchond200.gcu.earthquakeapplication;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.LinkedList;


import org.pchond200.gcu.earthquakeapplication.R;

public class MainActivity extends AppCompatActivity
{


    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;

    ArrayList<String> arrayOne;
    ArrayList<String> arrayTwo;

    ArrayList<String> date1;
    ArrayList<String> location1;
    ArrayList<String> lat1;
    ArrayList<String> lon1;
    ArrayList<String> depth1;
    ArrayList<String> mag1;
    ArrayList<String> latLong;

    ArrayList<EarthquakeClass> articles = new ArrayList<EarthquakeClass>();

    private String urlSource="http://quakes.bgs.ac.uk/feeds/MhSeismology.xml";

   // InputStream is;

   // ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.parse2);

        arrayOne = new ArrayList<String>();
        arrayTwo = new ArrayList<String>();

        date1 = new ArrayList<String>();
        location1 = new ArrayList<String>();
        lon1 = new ArrayList<String>();
        depth1 = new ArrayList<String>();
        mag1 = new ArrayList<String>();
        latLong = new ArrayList<String>();

        BottomNavigationView navBottom = findViewById(R.id.navBottom);

        navBottom.setOnNavigationItemSelectedListener(navListener);

            new ProcessInBackground().execute();
        }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public  boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.bottomNavHome:
                            selectedFragment = new MainFragment();
                            Bundle myBundle3 = new Bundle();
                            myBundle3.putStringArrayList("key3", arrayOne);
                            selectedFragment.setArguments(myBundle3);
                            break;
                        case R.id.bottomNavMap:
                            selectedFragment = new MapFragment();
                            Bundle myBundle = new Bundle();
                            myBundle.putStringArrayList("key", arrayOne);
                            selectedFragment.setArguments(myBundle);
                            break;
                        case R.id.bottomNavSettings:
                            selectedFragment = new SettingsFragment();
                            Bundle myBundle2 = new Bundle();
                            myBundle2.putStringArrayList("key2", arrayOne );
                            selectedFragment.setArguments(myBundle2);
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment, selectedFragment).commit();

                    return true;
                }
            };

        public InputStream getInputStream(URL url)
        {
            try
            {
                return url.openConnection().getInputStream();
            }
            catch (IOException e)
            {
                return null;
            }
        }

        public class ProcessInBackground extends AsyncTask<Integer, Void, Exception>
        {
            ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);

            Exception exception = null;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog.setMessage("Loading RSS Feed...");
                progressDialog.show();
            }

            @Override
            protected Exception doInBackground(Integer... params) {

                try
                {
                    URL url = new URL(urlSource);
                    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                    factory.setNamespaceAware(false);
                    XmlPullParser xpp = factory.newPullParser();
                    xpp.setInput(getInputStream(url), "UTF_8");

                    // Look for the <title> tag inside the <item> tag

                    boolean insideItem = false;

                    // Returns the type of current event type

                    int eventType = xpp.getEventType();

                    while (eventType != XmlPullParser.END_DOCUMENT)
                    {
                        if (eventType == XmlPullParser.START_TAG)
                        {
                            if (xpp.getName().equalsIgnoreCase("item"))
                            {
                                insideItem = true;
                            }
                            else if (xpp.getName().equalsIgnoreCase("title"))
                            {
                                if (insideItem)
                                {
                                    arrayTwo.add(xpp.nextText());
                                }
                            }

                            //if the tag is called "link"

                            else if (xpp.getName().equalsIgnoreCase("description"))
                            {
                                if (insideItem)
                                {
                                    arrayOne.add(xpp.nextText());
                                }
                            }

                            else if (xpp.getName().equalsIgnoreCase("pubDate"))
                            {
                                if (insideItem) {
                                    date1.add(xpp.nextText());
                                }
                            }
                        }

                        else if (eventType == XmlPullParser.END_TAG) {
                            if (xpp.getName().equalsIgnoreCase("item")) {
                                insideItem = false;
                        }
                    }
                        eventType = xpp.next();
                    }


                }
                catch (MalformedURLException e)
                {
                    exception = e;
                }
                catch (XmlPullParserException e)
                {
                    exception = e;
                }
                catch (IOException e)
                {
                    exception = e;
                }

                return exception;
            }

            @Override
            protected void onPostExecute(Exception s) {
                super.onPostExecute(s);

                String getDesc = "";
                for (int i = 0; i < arrayOne.size(); i++) {
                    getDesc = arrayOne.get(i);
                    String[] seperatedDesc = getDesc.split(";");
                    String[] seperateLocation = seperatedDesc[1].split(":");
                    location1.add(seperateLocation[1]);
                    latLong.add(seperatedDesc[2]);
                    depth1.add(seperatedDesc[3]);
                    mag1.add(seperatedDesc[4]);

                }

                     if (arrayOne != null) {

                     Fragment selectedFragment = new MainFragment();
                     Bundle myBundle3 = new Bundle();
                     myBundle3.putStringArrayList("key3", arrayOne);
                     selectedFragment.setArguments(myBundle3);
                     getSupportFragmentManager().beginTransaction().replace(R.id.fragment, selectedFragment).commit();

                     } else {
                         Log.e("null", "NULL");
                     }

                progressDialog.dismiss();
            }

        }
    }

