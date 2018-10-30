package com.example.lg.cbnu_library.view.sign_on;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.lg.cbnu_library.R;
import com.example.lg.cbnu_library.databinding.ActivitySignOnBinding;
import com.example.lg.cbnu_library.model.User;
import com.example.lg.cbnu_library.util.Global;
import com.example.lg.cbnu_library.util.NetworkConnector;
import com.example.lg.cbnu_library.view.select_library.SelectLibraryActivity;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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
            new SignOnThread(SignOnActivity.this, email, password).execute();
        }
    }

    private class SignOnThread extends AsyncTask<Void, Void, String> {
        private Activity activity;
        private String email;
        private String password;

        public SignOnThread(Activity activity, String email, String password) {
            this.activity = activity;
            this.email = email;
            this.password = password;
        }

        @Override
        protected String doInBackground(Void... voids) {
            Log.d("TEST", "Thread On");
            String result = null;
            try {
                result = NetworkConnector.getInstance().get(Global.getInstance().getIpAddr() + "rest/users/?format=json");
                Log.d("TEST", "Result : " + result);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            JsonParser parser = new JsonParser();
            Gson gson = new Gson();
            JsonArray array = parser.parse(result).getAsJsonArray();

            for (JsonElement element : array) {
                JsonObject object = element.getAsJsonObject();
                User user = gson.fromJson(object, User.class);
                if (email.equals(user.getEmail()) && password.equals(user.getPassword())) {
                    Intent intent = new Intent(activity, SelectLibraryActivity.class);
                    activity.startActivity(intent);
                    activity.finish();
                }
            }
        }
    }
}
