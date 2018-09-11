package com.example.lg.cbnu_library;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.lg.cbnu_library.databinding.ListBeaconBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeaconListAdapter extends RecyclerView.Adapter<BeaconViewHolder> {
    private List<Beacon> items;
    private Map<Beacon, Integer> duplicateChecker;

    public BeaconListAdapter() {
        items = new ArrayList<>();
        duplicateChecker = new HashMap<>();
    }

    @NonNull
    @Override
    public BeaconViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ListBeaconBinding binding = ListBeaconBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        return new BeaconViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BeaconViewHolder viewHolder, int i) {
        viewHolder.bind(items.get(i));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addBeacon(Beacon beacon) {
        if (!duplicateChecker.containsKey(beacon)) {
            duplicateChecker.put(beacon, items.size());
            items.add(beacon);
        }

        else {
            int position = duplicateChecker.get(beacon);
            items.remove(position);
            items.add(position, beacon);
        }

        notifyDataSetChanged();
    }

}

class BeaconViewHolder extends RecyclerView.ViewHolder {
    ListBeaconBinding binding;

    public BeaconViewHolder(ListBeaconBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Beacon beacon) {
        binding.setBeacon(beacon);
    }
}