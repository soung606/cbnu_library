package com.example.lg.cbnu_library.view.select_library;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.lg.cbnu_library.databinding.ActivitySelectLibraryBinding;
import com.example.lg.cbnu_library.model.Library;
import com.example.lg.cbnu_library.util.Global;
import com.example.lg.cbnu_library.util.NetworkConnector;
import com.example.lg.cbnu_library.view.select_seat.SelectSeatActivity;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

public class SelectLibraryThread extends AsyncTask<Void, Void, List<Library>> {
    private Activity activity;
    private LinearLayout container;
    private List<Library> libraries;

    public SelectLibraryThread(Activity activity, LinearLayout container) {
        this.activity = activity;
        this.container = container;
    }

    @Override
    protected List<Library> doInBackground(Void... voids) {
        try {
            String url = Global.getInstance().getIpAddr() + "rest/libraries?format=json";
            Log.d("TEST", url);
            String result = NetworkConnector.getInstance().get(url);
            Log.d("TEST", result);
            Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
            JsonParser parser = new JsonParser();
            JsonArray array = parser.parse(result).getAsJsonArray();

            List<Library> libraries = new ArrayList<>(array.size());
            for (JsonElement element : array) {
                Library library = gson.fromJson(element, Library.class);
                libraries.add(library);
            }

            return libraries;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<Library> libraries) {
        super.onPostExecute(libraries);
        this.libraries = libraries;
        Button[] buttons = new Button[libraries.size()];

        View.OnClickListener listener = new SelectLibraryListener();
        for (int i = 0; i < buttons.length; i++) {
            Log.d("TEST", libraries.get(i).getName());
            buttons[i] = new Button(activity);
            buttons[i].setId(i);
            buttons[i].setText(libraries.get(i).getName());
            buttons[i].setTextSize(30);
            buttons[i].setOnClickListener(listener);

            container.addView(buttons[i]);
        }
    }

    private class SelectLibraryListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            // 도서관 버튼 Listener
            int idx = view.getId();
            Library library = libraries.get(idx);

            Intent nextPage = new Intent(activity, SelectSeatActivity.class);
            nextPage.putExtra("LIBRARY", library);
            activity.startActivity(nextPage);
        }
    }
}