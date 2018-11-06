/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.pyojihye.airpollution.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.pyojihye.airpollution.HttpConnection;
import com.example.pyojihye.airpollution.activity.MainActivity;
import com.example.pyojihye.airpollution.activity.SettingDeviceActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import P_Data.Util_STATUS;
import P_Manager.Bluetooth_Manager;


public class DeviceConnector {
    private static final String TAG = "DeviceConnector";
    private static final boolean D = false;

    // Constants that indicate the current connection state
    public static final int STATE_NONE = 0;       // we're doing nothing
    public static final int STATE_CONNECTING = 1; // now initiating an outgoing connection
    public static final int STATE_CONNECTED = 2;  // now connected to a remote device

    private int mState;

    private final BluetoothAdapter btAdapter;
    private final BluetoothDevice connectedDevice;
    private ConnectThread mConnectThread;
    private ConnectedThread mConnectedThread;
    private final Handler mHandler;
    private final String deviceName;
    // ==========================================================================


    public DeviceConnector(DeviceData deviceData, Handler handler) {
        mHandler = handler;
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        connectedDevice = btAdapter.getRemoteDevice(deviceData.getAddress());
        deviceName = (deviceData.getName() == null) ? deviceData.getAddress() : deviceData.getName();
        mState = STATE_NONE;
    }

    public synchronized void connect() {
        if (D) Log.d(TAG, "connect to: " + connectedDevice);

        if (mState == STATE_CONNECTING) {
            if (mConnectThread != null) {
                if (D) Log.d(TAG, "cancel mConnectThread");
                mConnectThread.cancel();
                mConnectThread = null;
            }
        }

        if (mConnectedThread != null) {
            if (D) Log.d(TAG, "cancel mConnectedThread");
            mConnectedThread.cancel();
            mConnectedThread = null;
        }

        // Start the thread to connect with the given device
        mConnectThread = new ConnectThread(connectedDevice);
        mConnectThread.start();
        setState(STATE_CONNECTING);
    }

    public synchronized void stop() {
        if (D) Log.d(TAG, "stop");

        if (mConnectThread != null) {
            if (D) Log.d(TAG, "cancel mConnectThread");
            mConnectThread.cancel();
            mConnectThread = null;
        }

        if (mConnectedThread != null) {
            if (D) Log.d(TAG, "cancel mConnectedThread");
            mConnectedThread.cancel();
            mConnectedThread = null;
        }

        setState(STATE_NONE);
    }

    private synchronized void setState(int state) {
        if (D) Log.d(TAG, "setState() " + mState + " -> " + state);
        mState = state;
        mHandler.obtainMessage(SettingDeviceActivity.MESSAGE_STATE_CHANGE, state, -1).sendToTarget();
    }

    public synchronized int getState() {
        return mState;
    }

    public synchronized void connected(BluetoothSocket socket) {
        if (D) Log.d(TAG, "connected");

        // Cancel the thread that completed the connection
        if (mConnectThread != null) {
            if (D) Log.d(TAG, "cancel mConnectThread");
            mConnectThread.cancel();
            mConnectThread = null;
        }

        if (mConnectedThread != null) {
            if (D) Log.d(TAG, "cancel mConnectedThread");
            mConnectedThread.cancel();
            mConnectedThread = null;
        }

        setState(STATE_CONNECTED);

        // Send the name of the connected device back to the UI Activity
        Message msg = mHandler.obtainMessage(SettingDeviceActivity.MESSAGE_DEVICE_NAME, deviceName);
        mHandler.sendMessage(msg);

        // Start the thread to manage the connection and perform transmissions
        mConnectedThread = new ConnectedThread(socket);
        mConnectedThread.start();
    }

    public void write(byte[] data) {
        ConnectedThread r;
        // Synchronize a copy of the ConnectedThread
        synchronized (this) {
            if (mState != STATE_CONNECTED) return;
            r = mConnectedThread;
        }

        // Perform the write unsynchronized
        if (data.length == 1) r.write(data[0]);
        else r.writeData(data);
    }

    private void connectionFailed() {
        if (D) Log.d(TAG, "connectionFailed");

        // Send a failure message back to the Activity
        Message msg = mHandler.obtainMessage(SettingDeviceActivity.MESSAGE_TOAST);
        Bundle bundle = new Bundle();
        msg.setData(bundle);
        mHandler.sendMessage(msg);
        setState(STATE_NONE);
    }

    private void connectionLost() {
        // Send a failure message back to the Activity
        Message msg = mHandler.obtainMessage(SettingDeviceActivity.MESSAGE_TOAST);
        Bundle bundle = new Bundle();
        msg.setData(bundle);
        mHandler.sendMessage(msg);
        setState(STATE_NONE);
    }

    private class ConnectThread extends Thread {
        private static final String TAG = "ConnectThread";
        private static final boolean D = false;

        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;

        public ConnectThread(BluetoothDevice device) {
            if (D) Log.d(TAG, "create ConnectThread");
            mmDevice = device;
            mmSocket = BluetoothUtils.createRfcommSocket(mmDevice);
        }

        public void run() {
            if (D) Log.d(TAG, "ConnectThread run");
            btAdapter.cancelDiscovery();
            if (mmSocket == null) {
                if (D) Log.d(TAG, "unable to connect to device, socket isn't created");
                connectionFailed();
                return;
            }

            // Make a connection to the BluetoothSocket
            try {
                // This is a blocking call and will only return on a
                // successful connection or an exception
                mmSocket.connect();
            } catch (IOException e) {
                // Close the socket
                try {
                    mmSocket.close();
                } catch (IOException e2) {
                    if (D) Log.e(TAG, "unable to close() socket during connection failure", e2);
                }
                connectionFailed();
                return;
            }

            // Reset the ConnectThread because we're done
            synchronized (DeviceConnector.this) {
                mConnectThread = null;
            }

            // Start the connected thread
            connected(mmSocket);
        }

        public void cancel() {
            if (D) Log.d(TAG, "ConnectThread cancel");

            if (mmSocket == null) {
                if (D) Log.d(TAG, "unable to close null socket");
                return;
            }
            try {
                mmSocket.close();
            } catch (IOException e) {
                if (D) Log.e(TAG, "close() of connect socket failed", e);
            }
        }
    }

    private class ConnectedThread extends Thread {
        private static final String TAG = "ConnectedThread";
        private static final boolean D = false;

        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            if (D) Log.d(TAG, "create ConnectedThread");

            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the BluetoothSocket input and output streams
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                if (D) Log.e(TAG, "temp sockets not created", e);
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            if (D) Log.i(TAG, "ConnectedThread run");
            boolean csv_status=true;
            int count=0;
            int count2=0;
            mHandler.obtainMessage(SettingDeviceActivity.MESSAGE_CONNECT).sendToTarget();

            Bundle bundle = new Bundle();

            //mHandler.sendMessage(msg);
            byte[] buffer = new byte[1024];
            byte [] csv_buffer =null;
            int bytes;
            StringBuilder readMessage = new StringBuilder();
            JSONObject jsonObject=new JSONObject(); //들어오자마자 req start json 부터날림
            try {
                jsonObject.put("req","start");
                jsonObject.put("time",System.currentTimeMillis()/1000);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            writeData(jsonObject.toString().getBytes());
            StringBuilder CSVBuilder = new StringBuilder();
            //writeData("start".getBytes()); //send to message
            while (true) {
                try {
                    bytes = mmInStream.read(buffer);
                    CSVBuilder=new StringBuilder();
                    String readed = new String(buffer, 0, bytes);

                    readed = new String(buffer, 0, bytes);
                    JSONObject json = new JSONObject(readed);
                    if (json.has("res"))
                    {
                        if (json.getString("res").equals("CSV")) //CSV 파일 들어옴
                        {
                            Log.d("CSV_READED","CSV IN");
                            count=0;

                            while(csv_status)
                            {
                                String csv_readed;

                                    bytes = mmInStream.read(buffer);
                                    //String csv_readed=new String();
                                    //csv_readed= new String(buffer, 0, bytes-1);
                                    //csv_readed=new String(buffer,0,bytes);
                                    readed = new String(buffer, 0, bytes);

                                    if(readed.contains("*"))
                                    {
                                        readed=readed.substring(0,readed.length()-7);
                                        // CSVBuilder.substring(CSVBuilder.length()-3,CSVBuilder.length());
                                        csv_status=false;
                                        //CSVBuilder.deleteCharAt(CSVBuilder.length());
                                        //CSVBuilder.append(readed.substring(0,readed.length()-1));
                                        //csv_status=false;
                                    }
                                    CSVBuilder.append(readed.substring(0,readed.length()-1));
                                    Log.d("CSV_READED", "dd");
                                    //Log.d("CSV IN",readed);

                            }
                            Log.d("CSV_READED", "CSV OVER");
                            String temp=CSVBuilder.toString();
                            if(temp.contains("{"))
                            {
                                temp=temp.substring(12,temp.length());
                            }
                            File file = new File(Environment.getExternalStorageDirectory() + "/Download/"+count2+"ok.txt");
                            FileWriter fw = new FileWriter(file, true) ;
                            fw.write(temp);
                            fw.flush();

                            // 객체 닫기
                            fw.close();
                            csv_status=true;
                            Log.d("CSV FILE","FILE CREATE");
                            //Toast.makeText(get)
                            String tem=CSVBuilder.toString();
                            Log.d("CSV LENGTH",String.valueOf(tem.length()));
                            Log.d("CSV",CSVBuilder.toString());
                            count2++;

                        }
                        else if(json.getString("res").equals("RT")) //REAL TIME 들어옴
                        {
                            jsonObject=new JSONObject();
                            jsonObject.put("req","RT");
                            writeData(jsonObject.toString().getBytes());

                        }
                    }
                    else if(!json.has("res"))
                    {
                        Util_STATUS.HTTP_CONNECT_KIND = 6; //input real data
                        HttpConnection httpConnectionreal = new HttpConnection(MainActivity.getInstance(), MainActivity.getInstance().getApplicationContext());
                        httpConnectionreal.execute(json.toString());
                        bundle = new Bundle();
                        bundle.putString("data", readed);
                        Log.d("real data", json.toString());
                        if(Bluetooth_Manager.getInstance()!=null) {

                            Bluetooth_Manager.getInstance().Set_Data(bundle);
                        }
                    }
                    }catch(JSONException e)
                    {
                        Log.d("JSON", "JSON EXCEPTION");
                    }catch (IOException e)
                    {
                        Log.d("BLUE TOOTH","CONNECTION LOST");
                        connectionLost();
                        break;
                    }

            }
        }

        public void writeData(byte[] chunk) {

            try {
                mmOutStream.write(chunk);
                mmOutStream.flush();
                // Share the sent message back to the UI Activity
                mHandler.obtainMessage(SettingDeviceActivity.MESSAGE_WRITE, -1, -1, chunk).sendToTarget();
            } catch (IOException e) {
                if (D) Log.e(TAG, "Exception during write", e);
            }
        }

        public void write(byte command) {
            byte[] buffer = new byte[1];
            buffer[0] = command;

            try {
                mmOutStream.write(buffer);

                // Share the sent message back to the UI Activity
                mHandler.obtainMessage(SettingDeviceActivity.MESSAGE_WRITE, -1, -1, buffer).sendToTarget();
            } catch (IOException e) {
                if (D) Log.e(TAG, "Exception during write", e);
            }
        }

        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                if (D) Log.e(TAG, "close() of connect socket failed", e);
            }
        }
    }
}
