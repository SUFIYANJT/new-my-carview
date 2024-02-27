// MainActivity.java
package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MyAdapter.OnVehicleClickListener, SearchView.OnQueryTextListener {

    private RecyclerView recyclerView;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Example data for the RecyclerView
        List<String> itemList = new ArrayList<>();
        itemList.add("Vehicle 1");
        itemList.add("Machine 2");

        adapter = new MyAdapter(itemList, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onVehicleClick(String vehicleName) {
        // Show bottom sheet dialog to update vehicle information
        showBottomSheetDialog(vehicleName);
    }

    private void showBottomSheetDialog(String vehicleName) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        // Inflate your bottom sheet layout here
        View bottomSheetView = getLayoutInflater().inflate(R.layout.bottom_sheet_layout, null);
        bottomSheetDialog.setContentView(bottomSheetView);

        // Initialize EditText fields and button in the bottom sheet layout
        // ...

        bottomSheetDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView(); // Use androidx.appcompat.widget.SearchView
        assert searchView != null;
        searchView.setOnQueryTextListener(this); // Set the listener to MainActivity
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        // Your logout logic here
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        // Handle search query submission
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        // Filter RecyclerView data based on search query
        adapter.getFilter().filter(newText);
        return true;
    }
}
