package ru.naburnm8.bmstu.android.datamanagementnirapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    Button setUpAServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpAServer = findViewById(R.id.setUpAServerButton);
        setUpAServer.setOnClickListener(view -> {
            Intent intent = new Intent(this, ServerSettingsActivity.class);
            startActivity(intent);
        });
        SharedPreferences settings = getSharedPreferences("settings", MODE_PRIVATE);
        boolean serverSetUp = settings.getBoolean("serverSetUp", false);
        String serverSocket = settings.getString("serverSocket", "null");
        if (serverSetUp) {
            Toast.makeText(this, serverSocket, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainMenuActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences settings = getSharedPreferences("settings", MODE_PRIVATE);
        boolean serverSetUp = settings.getBoolean("serverSetUp", false);
        if (serverSetUp){
            Intent intent = new Intent(this, MainMenuActivity.class);
            startActivity(intent);
        }
    }
}