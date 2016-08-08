package com.example.pyojihye.airpollution;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
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

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import P_Adapter.History_Adapter;
import P_Adapter.Pager_Adapter;
import P_Data.Air_Data;
import P_Data.DBHelper;
import P_Fragment.Fr_DeviceManagement;
import P_Fragment.Fr_Historychart_pager;
import P_Fragment.Fr_R_G_Map;
import P_Fragment.Fr_View_pager;
import P_Manager.GMap_Manager;
import P_Manager.Gps_Manager;
import P_Service.Air_Fake_Service;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    GoogleMap gmap;

    //class
    public Gps_Manager gps_manager;
    public GMap_Manager gmap_manager;
    //Fragment 관련
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    public static Pager_Adapter pa; //STATIC PAGER ADAPTER USING Viewpager add
    public static History_Adapter ha;
    Air_Fake_Service air_fake_service;
    SQLiteDatabase db;
    DBHelper helper;
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
    public void init() {

        gps_manager = new Gps_Manager(getApplicationContext());

        //
        air_fake_service=new Air_Fake_Service(sHandler);

        gmap_manager=new GMap_Manager(getApplicationContext());

        //Intent Service = new Intent(this, Air_Fake_Service.class);
        //startService(Service);

        //Intent Service = new Intent(this, MainService.class);
        //stopService(Service);
        //db_init();
        helper=new DBHelper(getApplicationContext());
        try
        {
            db=helper.getWritableDatabase();

        }catch (SQLiteException e)
        {
            db=helper.getReadableDatabase();
        }
        helper.Today_max_val();
        /*db.execSQL("INSERT INTO Air_data(regdate,CO,SO2,NO2,O3,PM) values(1234,'12','13','14','15','16');");
        String sql="SELECT * FROM Air_data";
        sql="DELETE * FROM Air_data";
        Cursor cursor=db.rawQuery(sql,null);
        String tel="";
        while(cursor.moveToNext())
        {
            tel=cursor.getString(cursor.getColumnIndex("CO"));

        }
        Toast.makeText(getApplicationContext(),tel,Toast.LENGTH_SHORT).show();
        long currentTime = System.currentTimeMillis() / 1000;
        Date date = new Date(currentTime);*/
        //String name = c.getString(c.getColumnIndex("name"));
        //유닉스타임 시작값 - 84000
    }
    public void db_init(){
        //SQLiteDatabase db = this.openOrCreateDatabase("pims", MODE_PRIVATE, null);
        SQLiteDatabase db = this.openOrCreateDatabase("Air_data", MODE_PRIVATE, null);
        //db=this.openOrCreateDatabase("location",MODE_PRIVATE,null);
        String SQL_CREATE_TABLE_CONTACT =
                "CREATE TABLE IF NOT EXISTS Air_data ( "
                        + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + "regdate INTEGER, "
                        + "CO TEXT,"
                        + "SO2 TEXT,"
                        + "NO2 TEXT,"
                        + "O3 TEXT,"
                        + "PM TEXT"
                        + " )";
        db.execSQL(SQL_CREATE_TABLE_CONTACT);
        //String sql="INSERT INTO Air_data (regdate,CO,SO2,NO2,O3,PM) values (1234,'12','13','14','15','16');";
        String sql="INSERT INTO Air_data(regdate,CO) values(1234,'12','13','14','15','16');";
        db.execSQL(sql);

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


    LatLng latLng=new LatLng(32.88179410794101,-117.23402337703594);
    int count=0;
    int count2=0;

    /* it's fake data so i can delete after next time*/
    Air_Data [] arr=new Air_Data[5];
    private final Handler sHandler=new Handler(){

        @Override
        public void handleMessage(Message msg) {


            Air_Data ar=(Air_Data)msg.getData().getSerializable("data");
            if(P_Data.Util_STATUS.REAL_MAP_STATE) {
                if (ar != null) {
                    if (count == 4) {
                        if (count2 < 60) {
                            for (int i = 0; i < 4; i++) {
                                gmap_manager.Set_Circle2(arr[i], "im." + String.valueOf(i), arr[i].latLng);
                            }
                        } else {
                            for (int i = 0; i < 3; i++) {
                                gmap_manager.Set_Circle2(arr[i], "im." + String.valueOf(i), arr[i].latLng);
                            }
                        }
                        count = 0;

                        if (count2 > 44) {

                            //gmap_manager.Set_Circle2(arr[i],"cm"+String.valueOf(i),arr[i].latLng);
                            gmap_manager.Set_Circle2(arr[1], "cm." + String.valueOf(1), new LatLng(arr[1].latLng.latitude + 0.001, arr[1].latLng.longitude - 0.001));

                        }

                        gmap_manager.check_connection();
                    }
                    arr[count] = ar;
                    count++;
                    count2++;

                }
            }

                pa.rm.Set_realtime(ar);
                pa.rcm.Set_Realchart2(ar);
                pa.rcm.Set_Data(ar);
                pa.rcm.Set_Data_Color(ar);
            String sql=
                    "INSERT INTO Air_data(regdate,CO,SO2,NO2,O3,PM) values("+String.valueOf(System.currentTimeMillis()/1000)+","+String.valueOf(ar.co)+","
                            +String.valueOf(ar.so2)+","+String.valueOf(ar.no2)+","+String.valueOf(ar.o3)+","+String.valueOf(ar.pm2_5)+","
                            +String.valueOf(gps_manager.get_LatLng())+
                            ");";
            db.execSQL(sql);

            //db.execSQL("INSERT INTO Air_data(regdate,CO,SO2,NO2,O3,PM) values(1234,'12','13','14','15','16');");




        }
    };

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        //클릭시마다 상태확인
        if (id == R.id.nav_main) {

        } else if (id == R.id.nav_realtime_data) {
            Fragment fr = new Fr_View_pager();

            pa = new Pager_Adapter(getLayoutInflater(), getApplicationContext(),((Fr_View_pager)fr).getV_viewpager());

            fragmentManager = getFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.list_container, fr);

            fragmentTransaction.commit();
            Air_Fake_Service.RECEIVE_DATA_STATUS=true;

        } else if (id == R.id.nav_chart) {
            fragmentManager = getFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            Fragment fr=new Fr_Historychart_pager();

            fragmentTransaction.replace(R.id.list_container, fr);
            fragmentTransaction.commit();
            ha=new History_Adapter(getLayoutInflater(), getApplicationContext());

        } else if (id == R.id.nav_realtime_map) {
           // MapFragment map=(MapFragment)getFragmentManager().findFragmentById(R.id.map);
            Fragment fr = new Fr_R_G_Map(gps_manager.get_LatLng());

            fragmentManager = getFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.list_container, fr);
            //fragmentTransaction.add(R.id.list_container,fr);
            fragmentTransaction.commit();
            //Air_Fake_Service.RECEIVE_DATA_STATUS=true;
            P_Data.Util_STATUS.REAL_MAP_STATE=true;
        } else if (id == R.id.nav_history_map) {
            //getMapFragment();
            //MapFragment map=(MapFragment)getFragmentManager().findFragmentById(R.id.map);

            //fragmentManager.findFragmentById(R.id.map);
        } else if (id == R.id.nav_management) {
            Fragment fragmentDeviceManagement = new Fr_DeviceManagement();
        }
        //GMap_Manager.user_hash.clear();
        GMap_Manager.user_array.clear();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }



}
