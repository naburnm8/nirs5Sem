package ru.naburnm8.bmstu.android.datamanagementnirapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;
import com.google.android.material.textfield.TextInputEditText;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.RESTDBOutput;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.databaseAPI.login.LoginAPI;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Recordable;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.authmodels.LoginRequest;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity implements RESTDBOutput {
    Button loginButton;
    TextInputEditText username, password;
    String serverSocketString;
    SharedPreferences sharedPreferences;
    SharedPreferences sharedPreferencesEncrypted;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        loginButton = findViewById(R.id.loginSubmitButton);
        username = findViewById(R.id.loginInputEditText);
        password = findViewById(R.id.passwordInputEditText);
        SharedPreferences sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE);
        serverSocketString = sharedPreferences.getString("serverSocket", "");
        loginButton.setOnClickListener(view -> {
            Editable loginEditable = username.getText();
            Editable passwordEditable = password.getText();
            String loginString = "";
            String passwordString = "";
            if (loginEditable != null && passwordEditable != null) {
                loginString = loginEditable.toString();
                passwordString = passwordEditable.toString();
            }
            LoginRequest loginRequest = new LoginRequest();
            loginRequest.setUsername(loginString);
            loginRequest.setPassword(passwordString);
            LoginIntoAPI(loginRequest);

        });
    }

    public void LoginIntoAPI(LoginRequest loginRequest) {
        LoginAPI loginAPI = new LoginAPI(this, serverSocketString);
        loginAPI.execute(loginRequest);
    }


    @Override
    public void setLogged(String logged) {
        if (logged != null) {
            Toast.makeText(this, logged, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setData(Recordable data) {
        if (data == null) {
            return;
        }
        try {
            String masterKeys = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
            sharedPreferencesEncrypted = EncryptedSharedPreferences.create("account", masterKeys,
                    getApplicationContext(), EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);
            ArrayList<String> dataArray = data.parseToRecord();
            sharedPreferencesEncrypted.edit().putString("token", dataArray.get(0)).apply();
            sharedPreferencesEncrypted.edit().putString("username", dataArray.get(2)).apply();
            sharedPreferencesEncrypted.edit().putString("role", dataArray.get(3)).apply();
            sharedPreferences = getSharedPreferences("account", MODE_PRIVATE);
            sharedPreferences.edit().putBoolean("isLoggedIn", true).apply();
        } catch (GeneralSecurityException | IOException e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
        //Toast.makeText(this, sharedPreferencesEncrypted.getString("token", ""), Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, sharedPreferencesEncrypted.getString("username", ""), Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, sharedPreferencesEncrypted.getString("role", ""), Toast.LENGTH_SHORT).show();
        finish();
    }
}
