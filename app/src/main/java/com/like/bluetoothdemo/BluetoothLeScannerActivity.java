package com.like.bluetoothdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;

public class BluetoothLeScannerActivity extends AppCompatActivity {

    private BluetoothLeScanner bluetoothLeScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_le_scanner);

        bluetoothLeScanner = BluetoothAdapter.getDefaultAdapter().getBluetoothLeScanner();

    }

    public void cancelScan(View view) {
    }

    public void scan(View view) {
        bluetoothLeScanner.startScan(new ScanCallback() {
            @Override
            public void onScanResult(int callbackType, ScanResult result) {
                super.onScanResult(callbackType, result);

                Log.d("BluetoothLeScannerActiv", "callbackType:" + callbackType);
                BluetoothDevice device = result.getDevice();
                Log.d("BluetoothLeScannerActiv", device.getName());
            }

            @Override
            public void onBatchScanResults(List<ScanResult> results) {
                super.onBatchScanResults(results);
            }

            @Override
            public void onScanFailed(int errorCode) {
                super.onScanFailed(errorCode);
            }
        });
    }
}
