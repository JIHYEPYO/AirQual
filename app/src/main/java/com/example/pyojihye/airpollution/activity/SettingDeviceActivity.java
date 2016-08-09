package com.example.pyojihye.airpollution.activity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pyojihye.airpollution.R;
import com.example.pyojihye.airpollution.bluetooth.BluetoothChatService;
import com.example.pyojihye.airpollution.bluetooth.DeviceConnector;
import com.example.pyojihye.airpollution.bluetooth.DeviceData;
import com.example.pyojihye.airpollution.bluetooth.Utils;

import java.util.Set;

/**
 * Created by PYOJIHYE on 2016-08-03.
 */
public class SettingDeviceActivity extends AppCompatActivity {

    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;

    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";

    private static final int REQUEST_CONNECT_DEVICE_SECURE=1;
    private static final int REQUEST_ENABLE_BT=3;

    private BluetoothAdapter myBluetoothAdapter;
    private Set<BluetoothDevice> pairedDevices;
    private ArrayAdapter<String> BTArrayAdapter;
    private LocationManager locationManager;
    private BluetoothChatService mChatService = null;
    private String mConnectedDeviceName = null;
    private static DeviceConnector connector;


    public TextView textViewUDOOName;
    public TextView textViewUDOOMac;
    public ImageView imageViewUdoo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Setting Device");
        setContentView(R.layout.activity_setting_device);

        textViewUDOOName = (TextView) findViewById(R.id.TextViewUDOO);
        textViewUDOOMac = (TextView) findViewById(R.id.TextViewUDOOMac);
        imageViewUdoo=(ImageView) findViewById(R.id.imageViewUdoo);

        myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if(myBluetoothAdapter==null){
            Toast.makeText(this,"Bluetooth is not available",Toast.LENGTH_LONG).show();
        }else{
            gpsOn();
            blueToothOn();
        }

        Button buttonNext = (Button) findViewById(R.id.buttonNext);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(SettingDeviceActivity.this, MainActivity.class);
                startActivity(mainIntent);
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

    public void onImageViewUDOOClick(View view){
        if (isConnected()) {
            stopConnection();
        }
        else{
            Intent serverIntent = new Intent(this, DeviceListActivity.class);
            startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_SECURE);
        }
    }

    public void onImageViewHRClick(View view){

    }

    private final void setStatus(int resId) {
        textViewUDOOName.setText(resId);
    }

    private final void setStatus(CharSequence subTitle) {
        textViewUDOOMac.setText(subTitle);
    }


    // The Handler that gets information back from the BluetoothChatService
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case BluetoothChatService.STATE_CONNECTED:
                            setStatus(getString(R.string.title_connected_to, mConnectedDeviceName));
                            setStatus(mConnectedDeviceName);
                            BTArrayAdapter.clear();
                            break;
                        case BluetoothChatService.STATE_CONNECTING:
                            setStatus(R.string.title_connecting);
                            imageViewUdoo.setImageResource(R.drawable.udoo);
                            textViewUDOOMac.setText(DeviceListActivity.macaddress);
                            break;
                        case BluetoothChatService.STATE_LISTEN:
                        case BluetoothChatService.STATE_NONE:
                            setStatus(R.string.title_not_connected);
                            break;
                    }
                    break;
                case MESSAGE_WRITE:
                    byte[] writeBuf = (byte[]) msg.obj;
                    // construct a string from the buffer
                    String writeMessage = new String(writeBuf);
                    BTArrayAdapter.add("Me:  " + writeMessage);
                    break;
                case MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    // construct a string from the valid bytes in the buffer
                    String readMessage = new String(readBuf, 0, msg.arg1);
                    BTArrayAdapter.add(mConnectedDeviceName+":  " + readMessage);
                    break;
                case MESSAGE_DEVICE_NAME:
                    // save the connected device's name
                    mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
                    Toast.makeText(getApplicationContext(), "Connected to " + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                    break;
                case MESSAGE_TOAST:
                    Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST),Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE_SECURE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    String address = data.getStringExtra(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
                    BluetoothDevice device = myBluetoothAdapter.getRemoteDevice(address);
                    if (isAdapterReady() && (connector == null)) setupConnector(device);                }
                break;
            case REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    // Bluetooth is now enabled, so set up a chat session
                } else {
                    // User did not enable Bluetooth or an error occurred
                    Toast.makeText(this, R.string.bt_not_enabled_leaving, Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }

    boolean isAdapterReady() {
        return (myBluetoothAdapter != null) && (myBluetoothAdapter.isEnabled());
    }

    private void setupConnector(BluetoothDevice connectedDevice) {
        stopConnection();
        try {
            String emptyName = getString(R.string.empty_device_name);
            DeviceData data = new DeviceData(connectedDevice, emptyName);
            connector = new DeviceConnector(data, mHandler);
            connector.connect();
        } catch (IllegalArgumentException e) {
            Utils.log("setupConnector failed: " + e.getMessage());
        }
    }

    private void stopConnection() {
        if (connector != null) {
            connector.stop();
            connector = null;
            textViewUDOOName.setText("UDOO Board");
            DeviceListActivity.macaddress="NOT CONNECTED";
            imageViewUdoo.setImageResource(R.drawable.udoo0);
        }
    }

    private boolean isConnected() {
        return (connector != null) && (connector.getState() == DeviceConnector.STATE_CONNECTED);
    }

    private void onToggleButtonUdooClick(View view){
//        ToggleButton tog
    }
}
