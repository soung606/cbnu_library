package com.example.lg.cbnu_library.seat_management;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.lg.cbnu_library.databinding.ActivitySelectLibraryBinding;
import com.example.lg.cbnu_library.model.Library;
import com.example.lg.cbnu_library.util.NetworkConnector;
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
    private ActivitySelectLibraryBinding binding;

    private List<Library> libraries;

    public SelectLibraryThread(Activity activity, ActivitySelectLibraryBinding binding) {
        this.activity = activity;
        this.binding = binding;
    }

    @Override
    protected List<Library> doInBackground(Void... voids) {
        try {
            String result = NetworkConnector.getInstance().get("http://192.168.1.251:8000/rest/libraries/");
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

        Button[] buttons = new Button[libraries.size()];

        View.OnClickListener listener = new SelectLibraryListener();
        for (int i = 0; i < 2; i++) {
            buttons[i] = new Button(activity);
            buttons[i].setId(i);
            buttons[i].setText(libraries.get(i).getName());
            buttons[i].setTextSize(30);
            buttons[i].setOnClickListener(listener);

            binding.selectLibraryContainer.addView(buttons[i]);
        }

        this.libraries = libraries;
    }

    private class SelectLibraryListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            // 도서관 버튼 Listener
            int idx = view.getId();
            Library library = libraries.get(idx);

            Intent nextPage = new Intent(activity,SelectSeatActivity.class);
            nextPage.putExtra("LIBRARY", library);
            activity.startActivity(nextPage);
        }
    }
}
