package ru.naburnm8.bmstu.android.datamanagementnirapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class AccountSettingsActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    SharedPreferences sharedPreferencesEncrypted;
    Button logoutButton, settingsButton;
    TextView usernameText, roleText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("account", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        if (!isLoggedIn) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        try{
            String masterKeys = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
            sharedPreferencesEncrypted = EncryptedSharedPreferences.create("account", masterKeys,
                    getApplicationContext(), EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);
        } catch (GeneralSecurityException | IOException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        setContentView(R.layout.account_settings);
        logoutButton = findViewById(R.id.logoutBtn);
        settingsButton = findViewById(R.id.settingsBtn);
        usernameText = findViewById(R.id.usernameText);
        roleText = findViewById(R.id.roleText);
        usernameText.setText(sharedPreferencesEncrypted.getString("username", "ERROR"));
        roleText.setText(sharedPreferencesEncrypted.getString("role", "ERROR"));
        logoutButton.setOnClickListener(view -> {
            sharedPreferences.edit().putBoolean("isLoggedIn", false).apply();
            finish();
        });

    }
}
