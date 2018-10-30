package com.example.lg.cbnu_library.view.status;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lg.cbnu_library.R;
import com.example.lg.cbnu_library.databinding.ActivityStatusBinding;
import com.example.lg.cbnu_library.model.Seat;
import com.example.lg.cbnu_library.model.User;
import com.example.lg.cbnu_library.util.Global;
import com.example.lg.cbnu_library.util.NetworkConnector;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;


public class StatusActivity extends AppCompatActivity {
    //    private ActivityStatusBinding binding;
    private TextView seatNumber;
    private TextView endTimeView;

    private Button exitBtn;
    private Button extensionBtn;

    private Seat seat;
    private Date startTime;
    private Date endTime;

    private ServiceConnection serviceConnection;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        seatNumber = (TextView) findViewById(R.id.seat_number);
        endTimeView = (TextView) findViewById(R.id.end_time);
        exitBtn = (Button) findViewById(R.id.exit_btn);
        extensionBtn = (Button) findViewById(R.id.extension_btn);

//        binding = DataBindingUtil.setContentView(this, R.layout.activity_status);

        seat = (Seat) getIntent().getSerializableExtra("SEAT");
        serviceConnection = new BeaconServiceConnection();

//        binding.exitBtn.setOnClickListener(new ExitBtnListener());
//        binding.extensionBtn.setOnClickListener(new ExtensionBtnListener());
        exitBtn.setOnClickListener(new ExitBtnListener());
        extensionBtn.setOnClickListener(new ExtensionBtnListener());

        new SelectSeatThread().execute(seat);
    }

    private class ExitBtnListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            // 퇴실버튼
        }
    }

    private class ExtensionBtnListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            // 시간연장

            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(StatusActivity.this);
            dialogBuilder = dialogBuilder.
                    setTitle("시간연장").
                    setMessage("시간 연장을 하시겠습니까?").
                    setPositiveButton("YES", new DialogPositiveListener()).
                    setNegativeButton("NO", new DialogNegativeListener());
            dialogBuilder.show();
        }
    }

    private class DialogPositiveListener implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            // Yes 버튼 시간 연장
        }
    }

    private class DialogNegativeListener implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            // No 버튼 후
            exitBtn.performClick();
        }
    }

    private class BeaconServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            // 서비스 연결시 해당 부분
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            // 서비스 연결 해제시 해당 부분
        }
    }

    private class SelectSeatThread extends AsyncTask<Seat, Void, Boolean> {
        public SelectSeatThread() {
            startTime = new Date();
            endTime = new Date(System.currentTimeMillis() + 3600 * 1000);
        }

        @Override
        protected Boolean doInBackground(Seat... seats) {
            Seat seat = seats[0];
            User user = Global.getInstance().getUser();
            Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

            seat.setEmpty(false);
            String seatUrl = Global.getInstance().getIpAddr() + "rest/seats/" + seat.getId() + "/";
            String seatData = gson.toJson(seat);

            if (!NetworkConnector.getInstance().put(seatUrl, seatData)) return false;

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String userSeatUrl = Global.getInstance().getIpAddr() + "rest/userseats/";
            Log.d("TEST", userSeatUrl);
            String userSeatData = "{\"seat_id\":" + seat.getId() +
                    ", \"user_id\":" + user.getId() +
                    ", \"start_time\":" + "\"" + format.format(startTime) + "\"" +
                    ", \"end_time\":" + "\"" + format.format(endTime) + "\"" +
                    "}";

            return NetworkConnector.getInstance().post(userSeatUrl, userSeatData);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (!aBoolean) {
                Log.d("TEST", "POST EXECUTE ERROR");
                // 좌석 선택에 문제가 발생함!
//                return;
            }

            seatNumber.setText(String.valueOf(seat.getSeatNum()));
            SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");
            endTimeView.setText(format.format(endTime));

            // 서비스 시작
            Intent intent = new Intent(StatusActivity.this, BeaconSearchService.class);
            startService(intent);
            bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        }
    }
}
