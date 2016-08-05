package com.example.pyojihye.airpollution;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
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
    private BluetoothAdapter myBluetoothAdapter;
    private Set<BluetoothDevice> pairedDevices;
    private ArrayAdapter<String> BTArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Setting Device");
        setContentView(R.layout.activity_setting_device);
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
                find(view);
            }
        });

        ImageView ImageViewHeart = (ImageView) findViewById(R.id.imageViewHeart);
        ImageViewHeart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                find(view);
            }
        });
    }

    public void find(View view) {
        if (myBluetoothAdapter.isDiscovering()) {
            myBluetoothAdapter.cancelDiscovery();
        }
        else {
            BTArrayAdapter.clear();
            myBluetoothAdapter.startDiscovery();
            registerReceiver(bReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
        }
    }

    public void list(View view){
        pairedDevices = myBluetoothAdapter.getBondedDevices();
        for(BluetoothDevice device : pairedDevices)
            BTArrayAdapter.add(device.getName()+ "\n" + device.getAddress());
        Toast.makeText(getApplicationContext(),"Show Paired Devices",Toast.LENGTH_SHORT).show();
    }
    final BroadcastReceiver bReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (BluetoothDevice.ACTION_FOUND.equals(action)) {
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            BTArrayAdapter.add(device.getName() + "\n" + device.getAddress());
            BTArrayAdapter.notifyDataSetChanged();
        }
        }
    };
}
