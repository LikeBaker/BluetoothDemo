package com.like.bluetoothdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "_bluetooth";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //获取蓝牙适配器
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        /*
         * 判断蓝牙是否开启
         *
         * 从该方法开始需要Manifest.permission.BLUETOOTH权限
         */
        boolean isEnabled = bluetoothAdapter.isEnabled();
        if (isEnabled)
            Log.d(TAG, "蓝牙可用");
        else
            Log.d(TAG, "蓝牙不可用");


        //判断蓝牙状态
        int state = bluetoothAdapter.getState();
        getLocalBluetoothState(state);

        //蓝牙状态广播
        IntentFilter intentFilter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                Bundle bundle = intent.getExtras();
                if (bundle == null) return;
                int state = (int) bundle.get(BluetoothAdapter.EXTRA_STATE);

                switch (state) {
                    case BluetoothAdapter.STATE_OFF:
                        Log.d(TAG, "蓝牙状态：关闭");
                        break;
                    case BluetoothAdapter.STATE_TURNING_ON:
                        Log.d(TAG, "蓝牙状态：正在开启");
                        break;
                    case BluetoothAdapter.STATE_ON:
                        Log.d(TAG, "蓝牙状态：开启");
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        Log.d(TAG, "蓝牙状态：正在关闭");
                        break;
                    default:
                        break;
                }

            }
        }, intentFilter);

        //搜索状态广播
        IntentFilter intentFilter1 = new IntentFilter();
        intentFilter1.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        intentFilter1.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (action == null) return;

                switch (action) {
                    case BluetoothAdapter.ACTION_DISCOVERY_STARTED:
                        Log.d(TAG, "开始搜索");
                        break;
                    case BluetoothAdapter.ACTION_DISCOVERY_FINISHED:
                        Log.d(TAG, "搜索结束");
                        break;
                    case BluetoothDevice.ACTION_FOUND:
                        Bundle bundle = intent.getExtras();
                        if (bundle == null) return;

                        BluetoothDevice bluetoothDevice = (BluetoothDevice) bundle.get(BluetoothDevice.EXTRA_DEVICE);
                        BluetoothClass bluetoothClass = (BluetoothClass) bundle.get(BluetoothDevice.EXTRA_CLASS);
                        String bluetoothName = (String) bundle.get(BluetoothDevice.EXTRA_NAME);
                        Short riss = (Short) bundle.get(BluetoothDevice.EXTRA_RSSI);

                        break;
                    default:
                        break;
                }
            }
        }, intentFilter1);
    }

    private void getLocalBluetoothState(int state) {
        switch (state) {
            case BluetoothAdapter.STATE_OFF:
                Log.d(TAG, "蓝牙状态：关闭");
                break;
            case BluetoothAdapter.STATE_TURNING_ON:
                Log.d(TAG, "蓝牙状态：正在开启");
                break;
            case BluetoothAdapter.STATE_ON:
                Log.d(TAG, "蓝牙状态：开启");
                break;
            case BluetoothAdapter.STATE_TURNING_OFF:
                Log.d(TAG, "蓝牙状态：正在关闭");
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_le_scanner) {
            startActivity(new Intent(this, BluetoothLeScannerActivity.class));

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
