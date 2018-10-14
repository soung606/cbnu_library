package com.example.lg.cbnu_library.seat_management;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.lg.cbnu_library.R;
import com.example.lg.cbnu_library.databinding.ActivitySignOnBinding;
import com.example.lg.cbnu_library.util.NetworkConnector;

public class SignOnActivity extends AppCompatActivity {
    private ActivitySignOnBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_on);

        binding.loginBtn.setOnClickListener(new SignOnListener());
        binding.loginBtn.performClick();
    }

    private class SignOnListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String email = binding.email.getText().toString();
            String password = binding.password.getText().toString();
            new LoginThread(SignOnActivity.this, email, password).execute();
        }
    }
}
