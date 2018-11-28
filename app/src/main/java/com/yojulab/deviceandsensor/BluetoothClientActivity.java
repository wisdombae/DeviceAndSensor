package com.yojulab.deviceandsensor;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

public class BluetoothClientActivity extends AppCompatActivity implements View.OnClickListener {
    String address = null;
    BluetoothAdapter bluetoothAdapter = null;
    BluetoothSocket bluetoothSocket = null;
    private boolean isBtConnected = false;
    //SPP UUID. Look for it
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    ProgressBar progressBarConnecting ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_client);

        Intent newint = getIntent();
        address = newint.getStringExtra(BluetoothDeviceListActivity.EXTRA_ADDRESS); //receive the address of the bluetooth device

        progressBarConnecting = findViewById(R.id.progressBarConnecting);

        new ConnectBluetoothTask().execute(); //Call the class to connect

        View buttonOn = (Button) findViewById(R.id.buttonOn);
        View buttonOff = (Button) findViewById(R.id.buttonOff);
        View buttonDisconnet = (Button) findViewById(R.id.buttonDisconnet);

        buttonOn.setOnClickListener(this);
        buttonOff.setOnClickListener(this);
        buttonDisconnet.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        Class<?> cls = null;
        switch (id) {
            case R.id.buttonOn:
                turnSwicth("1");
                break;
            case R.id.buttonOff:
                turnSwicth("0");
                break;
            case R.id.buttonDisconnet:
                Disconnect();
                break;
        }
    }

    private void msg(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }

    private void turnSwicth(String flag) {
        if (bluetoothSocket != null) {
            try {
                bluetoothSocket.getOutputStream().write(flag.getBytes());
            } catch (IOException e) {
                msg("Error");
            }
        }
    }

    private void Disconnect() {
        if (bluetoothSocket != null) {  //If the btSocket is busy
            try {
                bluetoothSocket.close(); //close connection
            } catch (IOException e) {
                msg("Error");
            }
        }
        finish(); //return to the first layout
    }

    private class ConnectBluetoothTask extends AsyncTask<Void, Void, Void>  // UI thread
    {
        private boolean ConnectSuccess = true; //if it's here, it's almost connected

        @Override
        protected void onPreExecute() {
            progressBarConnecting.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... devices) //while the progress dialog is shown, the connection is done in background
        {
            try {
                if (bluetoothSocket == null || !isBtConnected) {
                    bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    BluetoothDevice dispositivo = bluetoothAdapter.getRemoteDevice(address);//connects to the device's address and checks if it's available
                    bluetoothSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);//create a RFCOMM (SPP) connection
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    bluetoothSocket.connect();//start connection
                }
            } catch (IOException e) {
                ConnectSuccess = false;//if the try failed, you can check the exception here
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);

            if (!ConnectSuccess) {
                msg("Connection Failed. Is it a SPP Bluetooth? Try again.");
                finish();
            } else {
                msg("Connected.");
                isBtConnected = true;
            }
            progressBarConnecting.setVisibility(View.GONE);
        }
    }

}
