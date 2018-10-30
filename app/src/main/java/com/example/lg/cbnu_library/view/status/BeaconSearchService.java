package com.example.lg.cbnu_library.view.status;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class BeaconSearchService extends Service {
    private final String TAG = "BeaconService";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        // Binding 시 이쪽으로 호출이 넘어온다.
        Log.d(TAG, "Bind");
        int count = 100;
        while(count-- > 0) {
            try {
                Thread.sleep(2000);
                Log.d(TAG, "Thread running");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    public void onCreate() {
        // 서비스 생성시 호출
        Log.d(TAG, "Create");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 서비스가 호출될 때 마다 실행
        Log.d(TAG, "StartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        // 서비스 종료시 호출
        Log.d(TAG, "Destroy");
        super.onDestroy();
    }
}
