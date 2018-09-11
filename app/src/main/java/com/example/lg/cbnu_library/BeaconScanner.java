package com.example.lg.cbnu_library;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.List;

public class BeaconScanner {
    private final String TAG = "BeaconScanner";

    private BluetoothAdapter adapter;
    private BluetoothLeScanner scanner;
    private ScanCallback callback;

    public BeaconScanner() {
        adapter = BluetoothAdapter.getDefaultAdapter();
        scanner = adapter.getBluetoothLeScanner();
        callback = new BeaconScanCallback();
    }

    // Bluetooth 사용에 필요한 권한 체크
    public void checkPermissions(Activity activity, int requestCode) {
        activity.requestPermissions(new String[]{Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN, Manifest.permission.ACCESS_COARSE_LOCATION}, requestCode);
    }

    // Bluetooth 가 사용가능한지 체크 하고 사용가능하게 요청하는 함수
    public void enableBluetooth(Activity activity, int requestCode) {
        if (adapter == null) {
            Log.d(TAG, "Device Not Support Bluetooth");
        }

        if (!adapter.isEnabled()) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            activity.startActivityForResult(intent, requestCode);
        }
    }

    public void setCallback(ScanCallback callback) {
        this.callback = callback;
    }

    public void startScan() {
        scanner.startScan(callback);
    }

    public void stopScan() {
        scanner.stopScan(callback);
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