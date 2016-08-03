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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

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
                    //httpConnection();
                    blueToothOn(view);
                    gpsOn();
                }
            }
        });
    }


    public void textViewClick(View v) {
        if (v.isClickable()) {
            Intent signUpIntent = new Intent(SignInActivity.this, SignUpActivity.class);
            startActivity(signUpIntent);
        }
    }

    public void httpConnection() {
        HttpURLConnection conn = null;

        try {
            URL url = new URL("requestURL"); //요청 URL을 입력
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST"); //요청 방식을 설정 (default : GET)

            conn.setDoInput(true); //input을 사용하도록 설정 (default : true)
            conn.setDoOutput(true); //output을 사용하도록 설정 (default : false)

            conn.setConnectTimeout(60); //타임아웃 시간 설정 (default : 무한대기)

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8")); //캐릭터셋 설정

            writer.write("key1=value1" + "&key2=value2" + "&key3=value3"); //요청 파라미터를 입력
            writer.flush();
            writer.close();
            os.close();

            conn.connect();

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8")); //캐릭터셋 설정

            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null) {
                if (sb.length() > 0) {
                    sb.append("\n");
                }
                sb.append(line);
            }

            System.out.println("response:" + sb.toString());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    public void blueToothOn(View view) {
        Intent mainIntent = new Intent(SignInActivity.this, MainActivity.class);
        startActivity(mainIntent);
        if (!myBluetoothAdapter.isEnabled()) {
            Intent turnOnIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnOnIntent, REQUEST_ENABLE_BT);
            Toast.makeText(getApplicationContext(), "Bluetooth turned on", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Bluetooth is already on", Toast.LENGTH_LONG).show();
        }
    }

    public void gpsOn() {
        /*
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if(locationManager.isProviderEnabled (LocationManager.GPS_PROVIDER)) {
            Toast.makeText(SignInActivity.this, "GPS is already on", Toast.LENGTH_LONG).show();
            return true;
        }
        else {
            Toast.makeText(SignInActivity.this, "Please check the GPS use.", Toast.LENGTH_LONG).show();
            startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), 0);
        }
        return false;
*/
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                // Request allowance
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            } else {
                // Do something...
                Toast.makeText(SignInActivity.this, "GPS is already on", Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            }
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    }
}
