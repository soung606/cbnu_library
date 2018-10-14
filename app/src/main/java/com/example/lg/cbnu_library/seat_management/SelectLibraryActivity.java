package com.example.lg.cbnu_library.seat_management;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.lg.cbnu_library.R;
import com.example.lg.cbnu_library.databinding.ActivitySelectLibraryBinding;

public class SelectLibraryActivity extends AppCompatActivity {
    private ActivitySelectLibraryBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_select_library);
        new SelectLibraryThread(this, binding).execute();
    }


}
