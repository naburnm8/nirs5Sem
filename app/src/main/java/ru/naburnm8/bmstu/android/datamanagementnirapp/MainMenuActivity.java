package ru.naburnm8.bmstu.android.datamanagementnirapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.RESTDBOutput;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.databaseAPI.connection.GETConnectionAPI;

public class MainMenuActivity extends AppCompatActivity implements RESTDBOutput {
    TextView serverStatus, serverSocket, usernameText;
    RecyclerView actionsList;
    Button refreshButton;
    String serverSocketString;
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
    public void setData(Object data) {

    }
}
