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
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.RESTDBOutput;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.databaseAPI.catalogue.CatalogueAPI_GET;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Recordable;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class AccountSettingsActivity extends AppCompatActivity implements RESTDBOutput {
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
            sharedPreferencesEncrypted.edit().putString("username", "").apply();
            sharedPreferencesEncrypted.edit().putString("role", "").apply();
            sharedPreferencesEncrypted.edit().putString("token", "").apply();
            finish();
        });

        settingsButton.setOnClickListener(view -> {
            String baseUrl = sharedPreferences.getString("serverSocket", "");
            String token = sharedPreferencesEncrypted.getString("token", "");
            CatalogueAPI_GET catalogueAPI_get = new CatalogueAPI_GET(this, baseUrl, token);
            catalogueAPI_get.execute();
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        usernameText.setText(sharedPreferencesEncrypted.getString("username", "ERROR"));
        roleText.setText(sharedPreferencesEncrypted.getString("role", "ERROR"));
    }

    @Override
    public void setLogged(String logged) {
        if (logged == null){
            return;
        }
        Toast.makeText(getApplicationContext(), logged, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setData(Recordable data) {
        if (data != null){
            Toast.makeText(getApplicationContext(), data.parseToString(), Toast.LENGTH_LONG).show();
            System.out.println(data.parseToRecord());
        }
    }
}
