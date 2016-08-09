package com.example.pyojihye.airpollution.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.pyojihye.airpollution.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CircleOptions;

import com.example.pyojihye.airpollution.P_Adapter.Pager_Adapter;
import com.example.pyojihye.airpollution.P_Data.Air_Data;
import com.example.pyojihye.airpollution.P_Fragment.Fr_DeviceManagement;
import com.example.pyojihye.airpollution.P_Fragment.Fr_GMap;
import com.example.pyojihye.airpollution.P_Fragment.Fr_View_pager;
import com.example.pyojihye.airpollution.P_Manager.Gmap_Manager;
import com.example.pyojihye.airpollution.P_Service.Air_Fake_Service;

/**
 * Created by PYOJIHYE on 2016-08-05.
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    GoogleMap gmap;

    //class
    public Gmap_Manager gmap_manager;
    //Fragment 관련
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    public static Pager_Adapter pa; //STATIC PAGER ADAPTER USING Viewpager add
    Air_Fake_Service air_fake_service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        init();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }


    public void init() {
        //gmap_manager = new Gmap_Manager(getApplicationContext());
        air_fake_service=new Air_Fake_Service(sHandler);

    }
    private final Handler sHandler=new Handler(){

        @Override
        public void handleMessage(Message msg) {


            Air_Data ar=(Air_Data)msg.getData().getSerializable("data");
            if(0<ar.co&&ar.co<100)
            {
                Fr_GMap.gMap.addCircle(new CircleOptions().center(ar.latLng).radius(10000).strokeColor(Color.GREEN).fillColor(Color.GREEN));

            }
            else if(101<ar.co&&ar.co<200)
            {
                Fr_GMap.gMap.addCircle(new CircleOptions().center(ar.latLng).radius(10000).strokeColor(Color.BLUE).fillColor(Color.BLUE));
            }
            else if(201<ar.co&&ar.co<300)
            {
                Fr_GMap.gMap.addCircle(new CircleOptions().center(ar.latLng).radius(10000).strokeColor(Color.YELLOW).fillColor(Color.YELLOW));
            }
            else if(301<ar.co&&ar.co<400)
            {
                Fr_GMap.gMap.addCircle(new CircleOptions().center(ar.latLng).radius(10000).strokeColor(Color.CYAN).fillColor(Color.CYAN));
            }
            else
            {
                Fr_GMap.gMap.addCircle(new CircleOptions().center(ar.latLng).radius(10000).strokeColor(Color.BLACK).fillColor(Color.BLACK));
            }


            //Fr_GMap.gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ar.latLng,15));
            //fragmentTransaction.notify();
            //Fr_GMap.gMap

            //googleMap.addCircle(new CircleOptions().center(latLng).radius(100).strokeColor(Color.RED).fillColor(Color.RED
            //Fr_GMap. gMap.addCircle(new CircleOptions().center(ar.latLng).radius(1000000));
            //String vv=String.valueOf(ar.co)+","+String.valueOf(ar.co2)+","+String.valueOf(ar.no2)+","
             //       +String.valueOf(ar.o3)+","+String.valueOf(ar.so2);
            //Air_Data ar=(Air_Data)msg.getData().getSerializable("data");


        }
    };

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_main) {

        } else if (id == R.id.nav_realtime_data) {
            pa = new Pager_Adapter(getLayoutInflater(), getApplicationContext());
            fragmentManager = getFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            Fragment fr = new Fr_View_pager();


            fragmentTransaction.replace(R.id.list_container, fr);

            fragmentTransaction.commit();
        } else if (id == R.id.nav_chart) {

        } else if (id == R.id.nav_realtime_map) {
           // MapFragment map=(MapFragment)getFragmentManager().findFragmentById(R.id.map);
            Fragment fr = new Fr_GMap(gmap_manager.get_LatLng());

            fragmentManager = getFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.list_container, fr);
            //fragmentTransaction.add(R.id.list_container,fr);
            fragmentTransaction.commit();
            Air_Fake_Service.RECEIVE_DATA_STATUS=true;
        } else if (id == R.id.nav_history_map) {
            //getMapFragment();
            //MapFragment map=(MapFragment)getFragmentManager().findFragmentById(R.id.map);

            //fragmentManager.findFragmentById(R.id.map);
        } else if (id == R.id.nav_management) {
            Fragment fragmentDeviceManagement = new Fr_DeviceManagement();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
