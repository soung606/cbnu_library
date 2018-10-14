package com.example.lg.cbnu_library;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.widget.CompoundButton;

import com.example.lg.cbnu_library.databinding.ActivityBeaconBinding;

import java.util.List;

public class BeaconListActivity extends AppCompatActivity {
    private final String TAG = "BeaconListActivity";

    private ActivityBeaconBinding binding;
    private BeaconScanner scanner;
    private BeaconListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_beacon);
        adapter = new BeaconListAdapter();
        scanner = new BeaconScanner();

        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.toggleButton.setOnCheckedChangeListener(new ToggleButtonListener());

        scanner.setCallback(new BeaconScanCallback());
    }


    // 토글 버튼 리스너
    private class ToggleButtonListener implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) scanner.startScan();
            else scanner.stopScan();
        }
    }

    // 스캔한 결과를 받아오는 Listener
    private class BeaconScanCallback extends ScanCallback {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            BluetoothDevice device = result.getDevice();
            String name = device.getName();
            String address = device.getAddress();

            // RSSI 는 신호의 세기를 뜻한다!
            int rssi = result.getRssi();

            if (name == null || !name.contains("MiniBeacon")) return;

            Log.d(TAG, name + " / " + address + " : " + rssi);
            Beacon beacon = new Beacon(name, address, rssi);
            adapter.addBeacon(beacon);

            Beacon nearestBeacon = binding.nearestBeacon.getBeacon();
            if (nearestBeacon == null) nearestBeacon = beacon;
            if (Math.abs(Integer.parseInt(nearestBeacon.getRssi())) > Math.abs(Integer.parseInt(beacon.getRssi())))
                nearestBeacon = beacon;

            binding.nearestBeacon.setBeacon(nearestBeacon);

            super.onScanResult(callbackType, result);
        }

        @Override
        public void onBatchScanResults(List<ScanResult> results) {
            Log.d(TAG, "BatchScanResult");

            for (ScanResult result : results) {
                BluetoothDevice device = result.getDevice();
                String name = device.getName();
                String address = device.getAddress();
                int rssi = result.getRssi();

                Log.d(TAG, name + " / " + address + " : " + rssi);
            }

            super.onBatchScanResults(results);
        }

        @Override
        public void onScanFailed(int errorCode) {
            Log.d(TAG, "Scan Fail!");
            super.onScanFailed(errorCode);
        }
    }
}
