package com.example.lg.cbnu_library.seat_management;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.lg.cbnu_library.R;
import com.example.lg.cbnu_library.databinding.ActivitySelectSeatBinding;
import com.example.lg.cbnu_library.model.Library;

public class SelectSeatActivity extends AppCompatActivity {
    private ActivitySelectSeatBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_select_seat);

        Library library = (Library) getIntent().getSerializableExtra("LIBRARY");
        binding.libraryName.setText(library.getName());
        Log.d("TEST", library.toString());
        Button[] buttons = new Button[library.getSeatCount()];

        View.OnClickListener listener = new SeatClickListener();
        for (int i = 0; i < library.getSeatCount(); i++) {
            buttons[i] = new Button(this);
            buttons[i].setText((i + 1) + "번 좌석");
            buttons[i].setTextSize(30);
            buttons[i].setOnClickListener(listener);
        }

        for (int y = 0; y < library.getSeatCount() / 2; y++) {
            LinearLayout row = new LinearLayout(this);
            row.setGravity(android.view.Gravity.CENTER);
            row.setOrientation(LinearLayout.HORIZONTAL);

            for (int x = 0; x < 2; x++)
                row.addView(buttons[x + y * 2]);

            binding.container.addView(row);
        }
    }

    private class SeatClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            // 좌석 버튼이 눌렸을 때의 Listener
            String text = ((Button) view).getText().toString();
            String idxStr = text.split(" ")[0];
            int idx = Integer.parseInt(idxStr.substring(0, idxStr.length() - 1));
        }
    }

}
