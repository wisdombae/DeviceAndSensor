package com.yojulab.deviceandsensor;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class BluetoothDeviceListActivity extends AppCompatActivity implements View.OnClickListener, ListView.OnItemClickListener {

    Button buttonPairedDeviceList;
    ListView listViewPairedDeviceList;

    private BluetoothAdapter bluetoothAdapter = null;
    private Set<BluetoothDevice> pairedDeviceList;
    public static String EXTRA_ADDRESS = "device_address";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_device_list);

        //if the device has bluetooth
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter == null) {
            //Show a mensag. that the device has no bluetooth adapter
            Toast.makeText(getApplicationContext(), "Bluetooth Device Not Available", Toast.LENGTH_LONG).show();

            //finish apk
            finish();
        } else if (!bluetoothAdapter.isEnabled()) {
            //Ask to the user turn the bluetooth on
            Intent turnBTon = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnBTon, 1);
        }

        buttonPairedDeviceList = (Button) findViewById(R.id.buttonPairedDeviceList);
        listViewPairedDeviceList = (ListView) findViewById(R.id.listViewPairedDeviceList);

        buttonPairedDeviceList.setOnClickListener(this);
    }

    private void pairedDeviceList() {
        pairedDeviceList = bluetoothAdapter.getBondedDevices();
        ArrayList pairedList = new ArrayList();

        if (pairedDeviceList.size() > 0) {
            for (BluetoothDevice bt : pairedDeviceList) {
                pairedList.add(bt.getName() + "\n" + bt.getAddress()); //Get the device's name and the address
            }
        } else {
            Toast.makeText(getApplicationContext(), "No Paired Bluetooth Devices Found.", Toast.LENGTH_LONG).show();
        }

        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, pairedList);
        listViewPairedDeviceList.setAdapter(adapter);
        listViewPairedDeviceList.setOnItemClickListener(this); //Method called when the device from the list is clicked

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // Get the device MAC address, the last 17 chars in the View
        String info = ((TextView) view).getText().toString();
        String address = info.substring(info.length() - 17);

        // Make an intent to start next activity.
        Intent intent = new Intent(view.getContext(), BluetoothClientActivity.class);

        //Change the activity.
        intent.putExtra(EXTRA_ADDRESS, address); //this will be received at ledControl (class) Activity
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        pairedDeviceList();
    }
}
