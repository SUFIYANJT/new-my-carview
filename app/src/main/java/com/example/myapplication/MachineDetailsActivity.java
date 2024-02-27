package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MachineDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.machinedetailsactivity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        View backButton = findViewById(R.id.back);
        setTitle("");
        // Your activity initialization code here
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Create Intent to go back to MainActivity
                Intent intent = new Intent(MachineDetailsActivity.this, MainActivity.class);
                startActivity(intent);
                // Finish the current activity
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

}
