package com.example.pyojihye.airpollution.activity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pyojihye.airpollution.HttpConnection;
import com.example.pyojihye.airpollution.R;

import P_Data.Util_STATUS;
import P_Manager.Gps_Manager;

public class SignInActivity extends AppCompatActivity {

    public static Activity ActivitySignIn;
    private BluetoothAdapter myBluetoothAdapter;
    private static final int REQUEST_CONNECT_BLE=2;
    private static final int REQUEST_ENABLE_BT=3;
    private LocationManager locationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ActivitySignIn=SignInActivity.this;

        myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if(myBluetoothAdapter==null){
            Toast.makeText(this,"Bluetooth is not available",Toast.LENGTH_LONG).show();
        }else{
            gpsOn();
            blueToothOn();
        }

        Button buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            EditText editTextEmail = (EditText) findViewById(R.id.editTextEmail);
            EditText editTextPassword = (EditText) findViewById(R.id.editTextPassword);

            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                if (email.equals("") || password.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please fill in all information", Toast.LENGTH_SHORT).show();
                } else {

                    Util_STATUS.HTTP_CONNECT_KIND=0;
                    HttpConnection httpConnectionSignIn = new HttpConnection(SignInActivity.this, getApplicationContext());
                    SharedPreferences pref;
                    pref=getSharedPreferences("MAC",0);
                    SharedPreferences.Editor editor = pref.edit();
                    //editor.putString("UDOOdeviceID",jsonObject.getString("deviceID"));
                    editor.putString("useremail",email);

                    editor.commit();
                    httpConnectionSignIn.execute(email,password);
                    Gps_Manager gps_manager=new Gps_Manager(getApplicationContext());
                    //Intent mainIntent=new Intent(getApplicationContext(), MainActivity.class);
                    //startActivity(mainIntent);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                new AlertDialog.Builder(this)
                        .setTitle("Exit App")
                        .setMessage("Are you sure you want to exit the application?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //process kill
                                android.os.Process.killProcess(android.os.Process.myPid());
                            }
                        })
                        .setNegativeButton("NO", null)
                        .show();
                break;

            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void blueToothOn() {
        if (!myBluetoothAdapter.isEnabled()) {
            Intent IntentEnable = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(IntentEnable, REQUEST_ENABLE_BT);
//            Toast.makeText(getApplicationContext(), "Bluetooth turned on", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Bluetooth is already on", Toast.LENGTH_SHORT).show();
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

                    Toast.makeText(this, "Please check the GPS use.", Toast.LENGTH_LONG).show();
                    startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), 0);

                } else {
                    Toast.makeText(this, "GPS is already on", Toast.LENGTH_LONG).show();
                    ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                }
            }
        }
    }
}
