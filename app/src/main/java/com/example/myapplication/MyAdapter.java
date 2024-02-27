package com.example.myapplication;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

    private static final int VIEW_TYPE_VEHICLE = 0;
    private static final int VIEW_TYPE_MACHINE = 1;

    private List<String> originalItemList; // Original list of items
    private static List<String> filteredItemList; // Filtered list of items
    private static OnVehicleClickListener onVehicleClickListener; // Listener for vehicle click events

    public MyAdapter(List<String> itemList, OnVehicleClickListener listener) {
        this.originalItemList = new ArrayList<>(itemList);
        filteredItemList = new ArrayList<>(itemList);
        onVehicleClickListener = listener; // Assign the listener instance
    }

    // Other methods...



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView;
        switch (viewType) {
            case VIEW_TYPE_VEHICLE:
                itemView = inflater.inflate(R.layout.item_vehicle, parent, false);
                return new VehicleViewHolder(itemView);
            case VIEW_TYPE_MACHINE:
                itemView = inflater.inflate(R.layout.item_machine, parent, false);
                return new MachineViewHolder(itemView);
            default:
                throw new IllegalArgumentException("Invalid view type");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        // Bind data or customize each type of card view here
        if (holder instanceof VehicleViewHolder) {
            ((VehicleViewHolder) holder).bind(filteredItemList.get(position));
        } else if (holder instanceof MachineViewHolder) {
            ((MachineViewHolder) holder).bind(filteredItemList.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        // Return the view type based on position or any other criteria
        return position % 2 == 0 ? VIEW_TYPE_VEHICLE:VIEW_TYPE_MACHINE;
    }

    @Override
    public int getItemCount() {
        return filteredItemList.size();
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String query = constraint.toString().toLowerCase().trim();

                List<String> filteredList = new ArrayList<>();
                if (query.isEmpty()) {
                    filteredList.addAll(originalItemList);
                } else {
                    for (String item : originalItemList) {
                        if (item.toLowerCase().contains(query)) {
                            filteredList.add(item);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filteredList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredItemList.clear();
                filteredItemList.addAll((List<String>) results.values);
                notifyDataSetChanged();
            }
        };
    }

    public interface OnVehicleClickListener {

        void onVehicleClick(String vehicleName);


    }

    private static class VehicleViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public VehicleViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.Vehicle_text);
            textView.setText("Vehicle Card");

            // Set click listener for the item
            itemView.setOnClickListener(v -> {
                if (onVehicleClickListener != null)
                    onVehicleClickListener.onVehicleClick(filteredItemList.get(getAdapterPosition()));
            });
        }

        public void bind(String item) {
            textView.setText(item);
        }
    }



    private static class MachineViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public MachineViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize the textView variable using the itemView
            textView = itemView.findViewById(R.id.Machine_text);
            textView.setText("Machine Card");

            // Set click listener for the item
            itemView.setOnClickListener(v -> {
                // Get the clicked machine item
                String machineItem = filteredItemList.get(getAdapterPosition());

                // Create Intent to open new activity
                Intent intent = new Intent(itemView.getContext(), MachineDetailsActivity.class);
                // Pass data to the new activity if needed
                intent.putExtra("machineName", machineItem);
                itemView.getContext().startActivity(intent);
            });
        }

        public void bind(String item) {
            textView.setText(item);
        }
    }
}
