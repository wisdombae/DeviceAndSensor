package com.yojulab.deviceandsensor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BluetoothActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);

        View buttonBluetoothDeviceList = findViewById(R.id.buttonBluetoothDeviceList);

        buttonBluetoothDeviceList.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        Intent intent = null;
        String text = ((Button) view).getText().toString();
        Bundle bundle = new Bundle();
        Class<?> cls = null;
        switch (id) {
            case R.id.buttonBluetoothDeviceList:
                cls = BluetoothDeviceListActivity.class;
                break;
        }
        intent = new Intent(this, cls);
        startActivity(intent);

    }
}
