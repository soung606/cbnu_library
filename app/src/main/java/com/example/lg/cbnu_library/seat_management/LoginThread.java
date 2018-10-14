package com.example.lg.cbnu_library.seat_management;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.example.lg.cbnu_library.model.User;
import com.example.lg.cbnu_library.util.NetworkConnector;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class LoginThread extends AsyncTask<Void, Void, String> {
    private Activity activity;
    private String email;
    private String password;

    public LoginThread(Activity activity, String email, String password) {
        this.activity = activity;
        this.email = email;
        this.password = password;
    }

    @Override
    protected String doInBackground(Void... voids) {
        Log.d("TEST", "Thread On");
        String result = null;
        try {
            result = NetworkConnector.getInstance().get("http://192.168.1.251:8000/rest/users/?format=json");
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
