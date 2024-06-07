package ru.naburnm8.bmstu.android.datamanagementnirapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    Button accountSettings;
    Button setUpAServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences settings = getSharedPreferences("settings", MODE_PRIVATE);
        boolean serverSetUp = settings.getBoolean("serverSetUp", false);
        if (serverSetUp) {
            Intent intent = new Intent(this, MainMenuActivity.class);
            startActivity(intent);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        accountSettings = findViewById(R.id.accountButton);
        setUpAServer = findViewById(R.id.setUpAServerButton);
        accountSettings.setOnClickListener(view -> {
            Intent intent = new Intent(this, AccountSettingsActivity.class);
            startActivity(intent);
        });
        setUpAServer.setOnClickListener(view -> {
            Intent intent = new Intent(this, ServerSettingsActivity.class);
            startActivity(intent);
        });
    }
}