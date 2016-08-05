package com.example.pyojihye.airpollution;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignInActivity extends AppCompatActivity {

    private static final int REQUEST_ENABLE_BT = 1;
    private BluetoothAdapter myBluetoothAdapter;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Button buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            EditText editTextEmail = (EditText) findViewById(R.id.editTextEmail);
            EditText editTextPassword = (EditText) findViewById(R.id.editTextPassword);

            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                if (email.equals("") || password.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please fill in all information", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    HttpConnection httpConnection = new HttpConnection(getApplicationContext());
                    httpConnection.execute(email,password);
                    //blueToothOn();
                    //gpsOn();
                }
            }
        });
    }

    public void textViewSignInClick(View v) {
        if (v.isClickable()) {
            Intent signUpIntent = new Intent(SignInActivity.this, SignUpActivity.class);
            startActivity(signUpIntent);
        }
    }

    public void blueToothOn() {
        Intent deviceSettingIntent = new Intent(SignInActivity.this, SettingDeviceActivity.class);
        startActivity(deviceSettingIntent);
        if (!myBluetoothAdapter.isEnabled()) {
            Intent turnOnIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnOnIntent, REQUEST_ENABLE_BT);
            Toast.makeText(getApplicationContext(), "Bluetooth turned on", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Bluetooth is already on", Toast.LENGTH_LONG).show();
        }
    }

    public void gpsOn() {
        if(android.os.Build.VERSION.SDK_INT < 23){
            if(locationManager.isProviderEnabled (LocationManager.GPS_PROVIDER)) {
                Toast.makeText(SignInActivity.this, "GPS is already on", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(SignInActivity.this, "Please check the GPS use.", Toast.LENGTH_LONG).show();
                startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), 0);
            }
        }
        else{
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);

                } else {
                    Toast.makeText(SignInActivity.this, "GPS is already on", Toast.LENGTH_LONG).show();
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    }
}
