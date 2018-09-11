package com.example.lg.cbnu_library;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CompoundButton;

import com.example.lg.cbnu_library.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MAIN_ACTIVITY";
    private final int PERMISSION_REQUEST_CODE = 102;
    private final int ENABLE_REQUEST_CODE = 103;

    private ActivityMainBinding binding;

    private BeaconScanner scanner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.beaconSearchButton.setOnCheckedChangeListener(new ToggleButtonListener());

        scanner = new BeaconScanner();
        scanner.checkPermissions(this, PERMISSION_REQUEST_CODE);
        scanner.enableBluetooth(this, ENABLE_REQUEST_CODE);
    }

    // 토글 버튼 리스너
    private class ToggleButtonListener implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) scanner.startScan();
            else scanner.stopScan();
        }
    }


}