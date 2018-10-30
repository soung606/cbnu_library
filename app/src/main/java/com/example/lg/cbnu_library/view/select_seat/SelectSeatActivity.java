package com.example.lg.cbnu_library.view.select_seat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lg.cbnu_library.R;
import com.example.lg.cbnu_library.model.Library;
import com.example.lg.cbnu_library.model.Seat;
import com.example.lg.cbnu_library.util.Global;
import com.example.lg.cbnu_library.util.NetworkConnector;
import com.example.lg.cbnu_library.view.status.StatusActivity;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

public class SelectSeatActivity extends AppCompatActivity {
//    private ActivitySelectSeatBinding binding;

    private TextView title;
    private LinearLayout container;

    private Library curLibrary;
    private List<Seat> seatList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_seat);
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_select_seat);
        curLibrary = (Library) getIntent().getSerializableExtra("LIBRARY");

        title = (TextView) findViewById(R.id.library_name);
        container = (LinearLayout) findViewById(R.id.container);

        title.setText(curLibrary.getName());
        Log.d("TEST", curLibrary.toString());

        // Library 번호를 기반으로 Seat 정보를 모두 가져온다.
        new SeatInfoThread(this).execute(curLibrary.getId());
    }

    private class SeatClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            // 좌석 버튼이 눌렸을 때의 Listener
            Button selectedButton = (Button) view;
            Seat seat = seatList.get(Integer.parseInt(selectedButton.getText().toString()) - 1);

            Intent intent = new Intent(SelectSeatActivity.this, StatusActivity.class);
            intent.putExtra("SEAT", seat);
            startActivity(intent);
        }
    }


    private class SeatInfoThread extends AsyncTask<Integer, Void, List<Seat>> {
        private Activity activity;

        private SeatInfoThread(Activity activity) {
            this.activity = activity;
        }

        @Override
        protected List<Seat> doInBackground(Integer... integers) {
            try {
                String result = NetworkConnector.getInstance().get(Global.getInstance().getIpAddr() + "rest/seats?library_num=" + integers[0] + "&format=json");
                Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
                JsonParser parser = new JsonParser();
                JsonArray array = parser.parse(result).getAsJsonArray();

                List<Seat> seats = new ArrayList<>(array.size());
                for (JsonElement element : array) {
                    Seat seat = gson.fromJson(element, Seat.class);
                    seats.add(seat);
                }

                return seats;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Seat> seats) {
            super.onPostExecute(seats);
            seatList = seats;

            Button[] buttons = new Button[seats.size()];

            View.OnClickListener listener = new SeatClickListener();
            for (int i = 0; i < buttons.length; i++) {
                Seat seat = seats.get(i);

                buttons[i] = new Button(activity);
                buttons[i].setText(String.valueOf(seat.getSeatNum()));
                buttons[i].setBackgroundColor(seat.isEmpty() ? Color.BLUE : Color.RED);
                buttons[i].setTextSize(30);
                buttons[i].setOnClickListener(seat.isEmpty() ? listener : null);
            }

            for (int y = 0; y < buttons.length / 2; y++) {
                LinearLayout row = new LinearLayout(SelectSeatActivity.this);
                row.setGravity(android.view.Gravity.CENTER);
                row.setOrientation(LinearLayout.HORIZONTAL);

                for (int x = 0; x < 2; x++)
                    row.addView(buttons[x + y * 2]);

                container.addView(row);
            }
        }
    }

}
