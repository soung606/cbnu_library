package com.example.lg.cbnu_library.seat_management;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.lg.cbnu_library.R;
import com.example.lg.cbnu_library.databinding.ActivityStatusBinding;


public class StatusActivity extends AppCompatActivity {

    private ActivityStatusBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_status);

        binding.exitBtn.setOnClickListener(new ExitBtnListener());
        binding.extensionBtn.setOnClickListener(new ExtensionBtnListener());


    }

    private class ExitBtnListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            // 퇴실버튼
        }
    }

    private class ExtensionBtnListener implements View.OnClickListener{

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

    private class DialogPositiveListener implements DialogInterface.OnClickListener{

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            // Yes 버튼 시간 연장
        }
    }

    private class DialogNegativeListener implements DialogInterface.OnClickListener{

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            // No 버튼 후
            binding.exitBtn.performClick();
        }
    }

}
