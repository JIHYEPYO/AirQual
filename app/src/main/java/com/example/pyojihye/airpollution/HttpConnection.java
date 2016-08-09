package com.example.pyojihye.airpollution;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.Toast;

import com.example.pyojihye.airpollution.activity.SettingDeviceActivity;
import com.example.pyojihye.airpollution.activity.SignInActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import P_Data.Util_STATUS;

/**
 * Created by PYOJIHYE on 2016-08-04.
 */
public class HttpConnection extends AsyncTask<String, String, String> {
    Context connectContext;
    Activity activity;
    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;
    HttpURLConnection conn;
    URL url = null;
    Handler hHnadler;
    SharedPreferences pref;

    //0 DEFAULT 1 SIGN IN& SIGN UP  2 DEVICE REGISTER // 3 CONNECTION REQUEST
    //4 RESPONSE HISTORY DATA 5 GET REAL TIME USER DATA

    public HttpConnection(Activity activity, Context connectContext) {
        this.activity=activity;
        this.connectContext=connectContext;
        //hHnadler=handler;
    }

    @Override
    protected String doInBackground(String... str) {
        try{
            // Enter URL address where your php file resides
            //0 sign in 1 SIGN up 2 DEVICE REGISTER  // 3 CONNECTION REQUEST
            //4 RESPONSE HISTORY DATA 5 GET REAL TIME USER DATA

            //set url
            switch (Util_STATUS.HTTP_CONNECT_KIND)
            {
                case 0: //sign in
                {
                    url = new URL("http://teama-iot.calit2.net/slim/recieveData.php//sign-in");
                    break;
                }
                case 1: //sign up
                {
                    url = new URL("http://teama-iot.calit2.net/slim/recieveData.php//sign-up");
                    break;
                }
                case 2: ///device register
                {
                    url = new URL("http://teama-iot.calit2.net/slim/recieveData.php/device_connect");
                    break;
                }
                case 3: //connection request
                {
                    break;
                }
                case 4: //response histroy data
                {
                    break;
                }
                case 5: //get real time user data
                {
                    break;
                }
                case 6: //input ar data
                {
                    //str[0] json data
                    break;
                }
                case 7: //input hr data
                {
                    break;
                }


            }


            if(str.length<=2){ //signIn

                //
            }else{  //signUp

            }

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "exception";
        }

        try {
            // Setup HttpURLConnection class to send and receive data from php and mysql
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(READ_TIMEOUT);
            conn.setRequestProperty("Cache-Control", "no-cache");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setConnectTimeout(CONNECTION_TIMEOUT);
            conn.setRequestMethod("POST");

            // setDoInput and setDoOutput method depict handling of both send and receive
            conn.setDoInput(true);
            conn.setDoOutput(true);

            // Append str to URL
            JSONObject json = new JSONObject();
            /*json.put("type","app");
            json.put("userID","79");
            json.put("request","0");
            json.put("deviceTYPE","0");
            json.put("deviceMAC","11A1AA1111A1");
               */
            //1.1.1. {"type":"app","userID"	:"123","request":"0","deviceTYPE":"0","deviceMAC":"11-A1-AA-11-11-A1"}

            switch (Util_STATUS.HTTP_CONNECT_KIND) {

                case 0: //sign in
                {
                    try{
                            json.put("type","app");
                            json.put("email", str[0]);
                            json.put("pwd", str[1]);


                    } catch (JSONException e) {
                        e.printStackTrace();
                        return "exception";
                    }
                    break;
                }
                case 1: //sign up
                {
                    json.put("type","app");
                    json.put("email", str[0]);
                    json.put("pwd", str[1]);
                    json.put("checkpwd", str[2]);
                    json.put("fname", str[3]);
                    json.put("lname", str[4]);
                    break;
                }
                case 2: //device register
                {
                    //USER ID 랑 ADDRESS 받음
                    json.put("type","app");
                    json.put("userID",str[0]); //userID
                    json.put("request","0");
                    if (Util_STATUS.SELECT_BLUETOOTH==0)
                    {
                        json.put("deviceTYPE","0");
                    }
                    else if(Util_STATUS.SELECT_BLUETOOTH==1)
                    {
                        json.put("deviceTYPE","1");
                    }
                    String mac="x'";
                    for(int i=0;i<str[1].split(":").length;i++)
                    {
                        mac+=str[1].split(":")[i];
                    }
                    mac+="'";
                    json.put("deviceMAC",mac);
                    //str[1] //device MAC
                    //json.put("deviceMAC",)
                    //json.put("deviceMAC",x)
                    break;
                }
                case 3: //connect request device id 들어옴
                {
                    json.put("type","app");
                    json.put("deviceID",str[0]);
                    json.put("request","0");
                    break;
                }
            }


            String body = json.toString();

            // Open connection for sending data
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(body);
            writer.flush();
            writer.close();
            os.close();
            conn.connect();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return "exception";
        } catch(Exception e){
            return "exception";
        }

        try {
            int response_code = conn.getResponseCode();
            // Check if successful connection made
            if (response_code == HttpURLConnection.HTTP_OK) {
                // Read data sent from server
                InputStream input = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                StringBuilder result = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                // Pass data to onPostExecute method
                return (result.toString());
            } else{
                return ("unsuccessful");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "exception";
        }catch(Exception e){
            return "exception";
        }finally {
            conn.disconnect();
        }
    }

    @Override
    protected void onPostExecute(String result) {

        try {
            String response;
            JSONObject jsonObject = new JSONObject(result);
            response=jsonObject.getString("response");

            switch (Util_STATUS.HTTP_CONNECT_KIND)
            {

                case 0: //sign in
                {
                    response=jsonObject.getString("response");
                    switch (response)
                    {
                        case "0":
                            //pref = activity.getSharedPreferences("MAC", 0);
                            pref=activity.getSharedPreferences("MAC",0);
                            //pref = MainActivity.getInstance().getSharedPreferences("MAC",0);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("userID",jsonObject.getString("userID"));
                            editor.commit();
                            //editor.putString("UDOOMAC", address);
                            Toast.makeText(connectContext, "Log-In Success!", Toast.LENGTH_LONG).show();
                            Intent IntentSettingDevice = new Intent(activity, SettingDeviceActivity.class);
                            activity.startActivity(IntentSettingDevice);
                            break;
                        case "1":
                            Toast.makeText(connectContext, "No exist in DB", Toast.LENGTH_LONG).show();
                            break;
                        case "2":
                            Toast.makeText(connectContext, "Password is wrong", Toast.LENGTH_LONG).show();
                            break;
                        case "3":
                            Toast.makeText(connectContext, "Lock account.\nPlease check the link in Email\nPlease Go to Web site, Change your Password!", Toast.LENGTH_LONG).show();
                            break;

                    }
                    break;
                }
                case 1: //sign up
                {
                    response=jsonObject.getString("response");
                    switch (response)
                    {
                        case "4":
                            Toast.makeText(connectContext, "Sign up in with this account", Toast.LENGTH_LONG).show();
                            break;
                        case "5":
                            Toast.makeText(connectContext, "Password must be at least 8 character long", Toast.LENGTH_LONG).show();
                            break;
                        case "6":
                            Toast.makeText(activity, "Sign Up Success!\n Please Check the link in Email.\nActivated your account", Toast.LENGTH_LONG).show();
                            Intent IntentSignIn = new Intent(activity, SignInActivity.class);
                            activity.startActivity(IntentSignIn);
                    }
                    break;
                }
                case 2: ///device register
                {

                    response=jsonObject.getString("response");
                    if(response=="0")
                    {
                        //pref 안에 디바이스 이름 디바이스 맥 유저 아이디 디바이스 아이디 저장
                        //{"type":"web","response":0,"deviceID":{"maxID":"5"}}Invalid HTTP status code211
                        pref=activity.getSharedPreferences("MAC",0);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("deviceID",jsonObject.getString("deviceID"));
                        editor.commit();
                        Toast.makeText(activity,"Device register success!",Toast.LENGTH_SHORT).show();
                    }
                    else if(response=="1")
                    {
                        Toast.makeText(activity,"Device register error!",Toast.LENGTH_SHORT).show();
                    }
                        //여기서 디바이스 아이디 저장
                    //1.1.1. {"type":"web","response":"0","deviceID":1234}


                    break;
                }
                case 3: //connection request
                {
                    //1.1.1. {"type":"web","response":"0","deviceID":1234,"connectionID":1234}//connection request

                    response=jsonObject.getString("response");
                    if(response=="0") {
                        pref=activity.getSharedPreferences("MAC",0);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("connectionID",jsonObject.getString("connectionID"));
                        editor.commit();
                            Toast.makeText(activity,"Connection request success!",Toast.LENGTH_SHORT).show();

                    }
                    else if(response=="1")
                    {
                    Toast.makeText(activity,"Connection request error!",Toast.LENGTH_SHORT).show();
                    }


                    break;
                }
                case 4: //response histroy data
                {
                    break;
                }
                case 5: //get real time user data
                {
                    break;
                }
            }
            switch (response) {
                case "0":
                    Toast.makeText(connectContext, "Log-In Success!", Toast.LENGTH_LONG).show();
                    Intent IntentSettingDevice = new Intent(activity, SettingDeviceActivity.class);
                    activity.startActivity(IntentSettingDevice);
                    break;
                case "1":
                    Toast.makeText(connectContext, "No exist in DB", Toast.LENGTH_LONG).show();
                    break;
                case "2":
                    Toast.makeText(connectContext, "Password is wrong", Toast.LENGTH_LONG).show();
                    break;
                case "3":
                    Toast.makeText(connectContext, "Lock account.\nPlease check the link in Email\nPlease Go to Web site, Change your Password!", Toast.LENGTH_LONG).show();
                    break;
                case "4":
                    Toast.makeText(connectContext, "Sign up in with this account", Toast.LENGTH_LONG).show();
                    break;
                case "5":
                    Toast.makeText(connectContext, "Password must be at least 8 character long", Toast.LENGTH_LONG).show();
                    break;
                case "6":
                    Toast.makeText(activity, "Sign Up Success!\n Please Check the link in Email.\nActivated your account", Toast.LENGTH_LONG).show();
                    Intent IntentSignIn = new Intent(activity, SignInActivity.class);
                    activity.startActivity(IntentSignIn);
                    break;
                default:
                    Toast.makeText(connectContext, "Exception!\nPlease Use Later", Toast.LENGTH_LONG).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}