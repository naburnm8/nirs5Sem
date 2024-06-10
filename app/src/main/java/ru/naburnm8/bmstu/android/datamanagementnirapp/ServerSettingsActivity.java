package ru.naburnm8.bmstu.android.datamanagementnirapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import ru.naburnm8.bmstu.android.datamanagementnirapp.asyncDatabase.AsyncReceiver;
import ru.naburnm8.bmstu.android.datamanagementnirapp.asyncDatabase.databaseData.DatabaseQuery;
import ru.naburnm8.bmstu.android.datamanagementnirapp.asyncDatabase.dialects.MicrosoftSQLOperator;
import ru.naburnm8.bmstu.android.datamanagementnirapp.asyncDatabase.dialects.PostgreSQLOperator;
import ru.naburnm8.bmstu.android.datamanagementnirapp.database.AsyncDBOutput;

import java.sql.ResultSet;
import java.util.ArrayList;

public class ServerSettingsActivity extends AppCompatActivity implements AsyncReceiver {
    Button saveButton, testButton;
    TextInputEditText serverSocket;
    TextView testLog;
    String serverSocketString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.server_setup_activity);
        saveButton = findViewById(R.id.saveServerSettings);
        saveButton.setEnabled(false);
        saveButton.setOnClickListener(view -> {
            SharedPreferences settings = getSharedPreferences("settings", MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("serverSocket", serverSocketString);
            editor.putBoolean("serverSetUp", true);
            editor.apply();
            finish();
        });
        testButton = findViewById(R.id.serverTestConnectionButton);
        testButton.setOnClickListener(view -> {
            Editable socketEditable = serverSocket.getText();
            String socket = "";
            if (socketEditable != null) {
                socket = socketEditable.toString();
            }
            testLog.setText(R.string.connecting);
            saveButton.setEnabled(false);
            testServerConnection(socket);
        });
        serverSocket = findViewById(R.id.serverSocketInput);
        testLog = findViewById(R.id.testLog);

    }
    void testServerConnection(String socket){
        serverSocketString = socket;
        DatabaseQuery query = new DatabaseQuery(socket, "connectionTest", "con", "SELECT \n" +
                "    SERVERPROPERTY('ProductVersion') AS ProductVersion,\n" +
                "    SERVERPROPERTY('ProductLevel') AS ProductLevel,\n" +
                "    SERVERPROPERTY('Edition') AS Edition,\n" +
                "    SERVERPROPERTY('EngineEdition') AS EngineEdition,\n" +
                "    SERVERPROPERTY('MachineName') AS MachineName", this);
        new MicrosoftSQLOperator().execute(query);
    }

    @Override
    public void setLogged(String logged) {
        if (logged == null) {
            return;
        }
        if (logged.contains("Login failed") || logged.contains("Successful")){
            testLog.setText("Server reachable");
            saveButton.setEnabled(true);
            return;
        }
        testLog.setText(logged);
    }

    @Override
    public void setData(ArrayList<ArrayList<String>> results) {
        if(results == null){return;}
        for (ArrayList<String> row : results) {
            if (row == null) {return;}
        }
        Toast.makeText(this, results.toString(), Toast.LENGTH_SHORT).show();
    }
}
