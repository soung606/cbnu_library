package com.example.lg.cbnu_library.view.select_library;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.example.lg.cbnu_library.R;

public class SelectLibraryActivity extends AppCompatActivity {
//    private ActivitySelectLibraryBinding binding;
    private LinearLayout layout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_library);
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_select_library);
        layout = (LinearLayout) findViewById(R.id.select_library_container);
        new SelectLibraryThread(this, layout).execute();
    }
}
