package ru.naburnm8.bmstu.android.datamanagementnirapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.RESTDBOutput;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.databaseAPI.connection.GETConnectionAPI;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Recordable;
import ru.naburnm8.bmstu.android.datamanagementnirapp.recyclerViewStuff.actionsList.ActionAdapter;
import ru.naburnm8.bmstu.android.datamanagementnirapp.recyclerViewStuff.actionsList.ActionData;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

public class MainMenuActivity extends AppCompatActivity implements RESTDBOutput {
    TextView serverStatus, serverSocket, usernameText;
    RecyclerView actionsList;
    Button refreshButton, accountsButton;
    String serverSocketString;
    ActionAdapter actionAdapter;
    SharedPreferences sharedPreferencesEncrypted;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences settings = getSharedPreferences("settings", MODE_PRIVATE);
        boolean serverSetUp = settings.getBoolean("serverSetUp", false);
        serverSocketString = settings.getString("serverSocket", "");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu_activity);
        serverStatus = findViewById(R.id.serverStatus);
        serverSocket = findViewById(R.id.serverSocketMainMenu);
        usernameText = findViewById(R.id.usernameMainMenu);
        actionsList = findViewById(R.id.actionsList);
        refreshButton = findViewById(R.id.refreshButton);
        serverSocket.setText(serverSocketString);
        serverStatus.setText(getString(R.string.connecting));
        refreshButton.setOnClickListener(view -> {
            serverStatus.setText(getString(R.string.connecting));
            testServerConnection(serverSocketString);
        });
        if (!serverSetUp) {
            Intent intent = new Intent(this, ServerSettingsActivity.class);
            startActivity(intent);
            finish();
        }
        testServerConnection(serverSocketString);
        ArrayList<ActionData> actionData = (ArrayList<ActionData>) ActionData.generateUnauthorizedActions();
        actionAdapter = new ActionAdapter(this, actionData);
        actionsList.setAdapter(actionAdapter);
        actionsList.setLayoutManager(new LinearLayoutManager(this));
        try{
        String masterKeys = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
        sharedPreferencesEncrypted = EncryptedSharedPreferences.create("account", masterKeys,
                getApplicationContext(), EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);
        } catch (GeneralSecurityException | IOException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        sharedPreferences = getSharedPreferences("account", MODE_PRIVATE);
        if (sharedPreferences.getBoolean("isLoggedIn", false)) {
            usernameText.setText(sharedPreferencesEncrypted.getString("username", ""));
            ArrayList<ActionData> actionData1 = (ArrayList<ActionData>) getActions(sharedPreferencesEncrypted.getString("role", ""));
            System.out.println(actionData1);
            actionAdapter = new ActionAdapter(this, actionData1);
            actionsList.setAdapter(actionAdapter);
        }
        accountsButton = findViewById(R.id.accountsButton);
        accountsButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, AccountSettingsActivity.class);
            startActivity(intent);
        });

    }

    protected void testServerConnection(String socket){
        try{
        GETConnectionAPI getConnectionAPI = new GETConnectionAPI(this, socket);
        getConnectionAPI.execute();}
        catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void setLogged(String logged) {
        if (logged == null){
            return;
        }
        if (logged.equals("borovik")) {
            serverStatus.setText(getString(R.string.serverStatusConnected));
            }
        else{
            serverStatus.setText(getString(R.string.serverStatusDisconnected));
            Toast.makeText(this, logged, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void setData(Recordable data) {

    }

    @Override
    public void onResume() {
        super.onResume();
        usernameText.setText(sharedPreferencesEncrypted.getString("username", ""));
        ArrayList<ActionData> actionData1 = (ArrayList<ActionData>) getActions(sharedPreferencesEncrypted.getString("role", ""));
        System.out.println(actionData1);
        actionAdapter = new ActionAdapter(this, actionData1);
        actionsList.setAdapter(actionAdapter);

    }

    private List<ActionData> getActions(String role){
        if(role.contains("ADMINISTRATOR")){
            return ActionData.generateAdminActions();
        }
        if(role.contains("CONSULTANT")){
            return ActionData.generateConsultantActions();
        }
        if (role.contains("STORAGE")){
            return ActionData.generateStorageActions();
        }
        return ActionData.generateUnauthorizedActions();
    }
}
