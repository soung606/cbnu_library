package com.example.lg.cbnu_library.util;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NetworkConnector {
    private final String TAG = "NetworkConnector";
    private static NetworkConnector instance;

    public static NetworkConnector getInstance() {
        if (instance == null) instance = new NetworkConnector();
        return instance;
    }

    private NetworkConnector() {
    }

    public String get(String target_url) throws Exception {
        try {
            URL url = new URL(target_url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode != 200) throw new Exception("Response Code is " + responseCode);

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }

            return builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean put(String target_url, String data) {
        try {
            Log.d(TAG, "PUT URL : " + target_url);
            Log.d(TAG, "PUT Data: " + data);
            URL url = new URL(target_url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);

            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(data);
            writer.flush();
            writer.close();

            Log.d(TAG, "PUT connection Code : " + connection.getResponseCode() + " / " + connection.getResponseMessage());
            return connection.getResponseCode() == 200;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean post(String target_url, String data) {
        try {
            URL url = new URL(target_url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);

            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(data);
            writer.flush();
            writer.close();

            Log.d(TAG, "POST connection Code : " + connection.getResponseCode() + " / " + connection.getResponseMessage());
            return connection.getResponseCode() == 201;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }


}
