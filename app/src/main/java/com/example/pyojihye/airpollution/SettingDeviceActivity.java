package com.example.pyojihye.airpollution;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Set;

/**
 * Created by PYOJIHYE on 2016-08-03.
 */
public class SettingDeviceActivity extends AppCompatActivity {
    private static final int REQUEST_ENABLE_BT=1;

    private BluetoothAdapter myBluetoothAdapter;
    private Set<BluetoothDevice> pairedDevices;
    private ArrayAdapter<String> BTArrayAdapter;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Setting Device");
        setContentView(R.layout.activity_setting_device);

        ActionBar bar = getSupportActionBar();


        myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if(myBluetoothAdapter==null){
            Toast.makeText(this,"Bluetooth is not available",Toast.LENGTH_LONG).show();
            finish();
        }

        Button buttonNext = (Button) findViewById(R.id.buttonNext);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(SettingDeviceActivity.this, MainActivity.class);
                startActivity(mainIntent);
            }
        });

        ImageView ImageViewUdoo = (ImageView) findViewById(R.id.imageViewUdoo);
        ImageViewUdoo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //xxxx
            }
        });

        ImageView ImageViewHeart = (ImageView) findViewById(R.id.imageViewHeart);
        ImageViewHeart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //xxxxx
            }
        });
    }

    public void blueToothOn() {
        if (!myBluetoothAdapter.isEnabled()) {
            Intent IntentEnable = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(IntentEnable, REQUEST_ENABLE_BT);
            Toast.makeText(getApplicationContext(), "Bluetooth turned on", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Bluetooth is already on", Toast.LENGTH_LONG).show();
        }
    }

    public void gpsOn() {
        if(android.os.Build.VERSION.SDK_INT < 23){
            if(locationManager.isProviderEnabled (LocationManager.GPS_PROVIDER)) {
                Toast.makeText(this, "GPS is already on", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(this, "Please check the GPS use.", Toast.LENGTH_LONG).show();
                startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), 0);
            }
        }
        else{
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1);

                } else {
                    Toast.makeText(this, "GPS is already on", Toast.LENGTH_LONG).show();
                    ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                }
            }
        }
    }


}